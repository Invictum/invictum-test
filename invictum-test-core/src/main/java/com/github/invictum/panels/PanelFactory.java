package com.github.invictum.panels;

import com.github.invictum.pages.AbstractPage;
import com.github.invictum.panels.strategy.NoWaitStrategy;
import com.github.invictum.panels.strategy.PanelInitStrategy;
import com.github.invictum.tricks.Visibility;
import com.github.invictum.unified.data.provider.UnifiedDataProviderFactory;
import com.github.invictum.utils.ResourceProvider;
import com.github.invictum.utils.properties.PropertiesUtil;
import net.serenitybdd.core.pages.WebElementFacade;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static com.github.invictum.utils.properties.EnhancedSystemProperty.PanelInitStrategy;
import static com.github.invictum.utils.properties.EnhancedSystemProperty.PanelsPackageName;

public class PanelFactory {

    public static final String PANELS_PACKAGE = PropertiesUtil.getProperty(PanelsPackageName);
    public static final Logger LOG = LoggerFactory.getLogger(PanelFactory.class);
    final static public String FLOATING_PANEL_BASE_LOCATOR = "//body";

    private static PanelInitStrategy strategy = new NoWaitStrategy();

    private PanelFactory() {
        // disable constructor.
    }

    static {
        if (StringUtils.equals(PANELS_PACKAGE, PanelsPackageName.defaultValue())) {
            LOG.info("Project root is used as panels package. You may redefine it with '{}' property",
                    PanelsPackageName);
        } else if (!ResourceProvider.isPackagePresent(PANELS_PACKAGE)) {
            LOG.error("Configure panels package with '{}' property", PanelsPackageName);
        }
        initStrategy();
    }

    private static void initStrategy() {
        String strategyName = PropertiesUtil.getProperty(PanelInitStrategy);
        if (StringUtils.equals(strategyName, PanelInitStrategy.defaultValue())) {
            LOG.warn("Default panel init strategy is used '{}'. You may redefine it with '{}' property", strategyName,
                    PanelInitStrategy);
        }
        try {
            strategy = (PanelInitStrategy) Class.forName(strategyName).newInstance();
            LOG.debug("Used '{}' panel init strategy");
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException(String.format("Failed to init %s strategy", strategyName));
        }
    }

    /**
     * Method returns initialized panel by its class.
     *
     * @param panelClass panelClass
     * @param parentPage parentPage
     * @param <T>        template
     * @return Panel
     */
    public static <T extends AbstractPanel> T get(final Class<T> panelClass, final AbstractPage parentPage) {
        applyGlobalStrategy(panelClass, parentPage);
        T panelInstance = getPanel(panelClass);
        String locator = UnifiedDataProviderFactory.getInstance(panelInstance).getBase();
        if (FloatingPanel.class.isAssignableFrom(panelClass) && locator == null) {
            locator = FLOATING_PANEL_BASE_LOCATOR;
        }
        verifyBaseAttribute(panelClass, locator);
        WebElementFacade panel = parentPage.isXpath(locator) ? parentPage.find(By.xpath(locator)) : parentPage
                .find(By.cssSelector(locator));
        /** Wrap panel element into panel class. */
        panelInstance.initWith(parentPage, panel);
        invokeWhenInitializedMethods(panelInstance);
        return panelInstance;
    }

    /**
     * Method checks panels visibility gracefully.
     *
     * @param panelClass panelClass
     * @param parentPage parentPage
     * @return boolean
     */
    public static boolean isPanelVisible(Class<? extends AbstractPanel> panelClass, AbstractPage parentPage) {
        String locatorValue = UnifiedDataProviderFactory.getInstance(getPanel(panelClass)).getBase();
        By locator = parentPage.isXpath(locatorValue) ? By.xpath(locatorValue) : By.cssSelector(locatorValue);
        return parentPage.getTrick(Visibility.class).isElementVisible(locator);
    }

    public static <T extends AbstractPanel> List<T> getAll(final Class<T> panelClass, final AbstractPage parentPage) {
        applyGlobalStrategy(panelClass, parentPage);
        String locator = UnifiedDataProviderFactory.getInstance(getPanel(panelClass)).getBase();
        /** Block ability to init a list of floating panels without base. */
        if (FloatingPanel.class.isAssignableFrom(panelClass) && locator == null) {
            throw new IllegalStateException(
                    String.format("Try to init a list of Floating Panels for %s", panelClass.getSimpleName()));
        }
        verifyBaseAttribute(panelClass, locator);
        List<WebElementFacade> panels = parentPage.isXpath(locator) ? parentPage.findAll(By.xpath(locator)) : parentPage
                .findAll(By.cssSelector(locator));
        /** Wrap panel elements into list of panel classes. */
        List<T> panelList = new ArrayList<>();
        for (WebElementFacade element : panels) {
            T panel = getPanel(panelClass);
            panel.initWith(parentPage, element);
            invokeWhenInitializedMethods(panel);
            panelList.add(panel);
        }
        return panelList;
    }

    private static <T extends AbstractPanel> void verifyBaseAttribute(Class<T> panelClass, String locator) {
        if (locator == null) {
            String className = panelClass.getSimpleName();
            LOG.error("Base attribute is absent for {}. Specify it in locators source.", panelClass);
            throw new IllegalStateException(String.format("Unable to init %s panel", className));
        }
    }

    private static <T extends AbstractPanel> void applyGlobalStrategy(Class<T> panelClass, AbstractPage parentPage) {
        if (!panelClass.isAnnotationPresent(DisableGlobalInitStrategy.class)) {
            strategy.apply(parentPage);
        }
    }

    private static void invokeWhenInitializedMethods(AbstractPanel panel) {
        for (Method method : panel.getClass().getMethods()) {
            if (method.isAnnotationPresent(WhenPanelInitializes.class)) {
                LOG.debug("Found local init method for {} at {}", panel, method);
                try {
                    method.setAccessible(true);
                    method.invoke(panel);
                } catch (ReflectiveOperationException e) {
                    LOG.error("Failed to invoke {} method", method.toString());
                    LOG.error(e.getMessage());
                }
            }
        }
    }

    private static <T extends AbstractPanel> T get(final Class<T> panelClass) {
        return getPanel(panelClass);
    }

    private static <T extends AbstractPanel> T getPanel(final Class<T> panelClass) {
        T panelInstance;
        try {
            panelInstance = panelClass.newInstance();
            LOG.debug("New {} is initialized", panelInstance);
        } catch (ReflectiveOperationException e) {
            LOG.error("Failed to init panel - {}", e.getCause());
            throw new IllegalStateException(String.format("Failed to init %s", panelClass));
        }
        return panelInstance;
    }
}
