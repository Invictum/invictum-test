package com.github.invictum.panels;

import com.github.invictum.pages.AbstractPage;
import com.github.invictum.panels.strategy.NoWaitStrategy;
import com.github.invictum.panels.strategy.PanelInitStrategy;
import com.github.invictum.unified.data.provider.UnifiedDataProviderFactory;
import com.github.invictum.utils.ResourceProvider;
import com.github.invictum.utils.properties.EnhancedSystemProperty;
import com.github.invictum.utils.properties.PropertiesUtil;
import net.serenitybdd.core.pages.WebElementFacade;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PanelFactory {

    public static final String SUFFIX = "Panel";
    public static final String PANELS_PACKAGE = PropertiesUtil.getProperty(EnhancedSystemProperty.PanelsPackageName);
    public static final Logger LOG = LoggerFactory.getLogger(PanelFactory.class);
    final static public String FLOATING_PANEL_BASE_LOCATOR = "//body";

    private static PanelInitStrategy strategy = new NoWaitStrategy();

    private PanelFactory() {
        // disable constructor.
    }

    static {
        if (!ResourceProvider.isPackagePresent(PANELS_PACKAGE)) {
            LOG.error("Configure panels package with '{}' property", EnhancedSystemProperty.PanelsPackageName);
        }
    }

    /**
     * Set panel init wait strategy.
     *
     * @param strategyToSet
     */
    public static void setPanelInitWaitStrategy(PanelInitStrategy strategyToSet) {
        strategy = strategyToSet;
        LOG.info("Set {} strategy", strategyToSet);
    }

    /**
     * Method returns initialized panel by its class.
     *
     * @param panelClass
     * @param parentPage
     * @param <T>
     * @return Panel
     */
    public static <T extends AbstractPanel> T get(final Class<T> panelClass, final AbstractPage parentPage) {
        applyGlobalStrategy(panelClass, parentPage);
        T panelInstance = getPanel(panelClass);
        String locator = UnifiedDataProviderFactory.getInstance(panelInstance).getBase();
        WebElementFacade panel;
        if (locator == null) {
            panel = parentPage.find(By.xpath(FLOATING_PANEL_BASE_LOCATOR));
        } else {
            panel = parentPage.isXpath(locator) ? parentPage.find(By.xpath(locator)) : parentPage
                    .find(By.cssSelector(locator));
        }
        /** Wrap panel element into panel class. */
        panelInstance.initWith(parentPage, panel);
        invokeWhenInitializedMethods(panelInstance);
        return panelInstance;
    }

    public static <T extends AbstractPanel> List<T> getAll(final Class<T> panelClass, final AbstractPage parentPage) {
        applyGlobalStrategy(panelClass, parentPage);
        String locator = UnifiedDataProviderFactory.getInstance(getPanel(panelClass)).getBase();
        List<WebElementFacade> panels;
        if (locator == null) {
            panels = parentPage.findAll(By.xpath(FLOATING_PANEL_BASE_LOCATOR));
        } else {
            panels = parentPage.isXpath(locator) ? parentPage.findAll(By.xpath(locator)) : parentPage
                    .findAll(By.cssSelector(locator));
        }
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

    /**
     * Returns panel representation as WebElementFacade.
     *
     * @param panelName
     * @return WebElementFacade
     */
    @Deprecated
    public static WebElementFacade getAsWebElement(String panelName) {
        String fullPanelName = String.format("%s%s", panelName, SUFFIX);
        AbstractPanel resultPanel = null;
        Reflections reflections = new Reflections(ClasspathHelper.forPackage(PANELS_PACKAGE));
        Set<Class<? extends AbstractPanel>> availableClasses = reflections.getSubTypesOf(AbstractPanel.class);
        for (Class<? extends AbstractPanel> panelClass : availableClasses) {
            if (StringUtils.equals(panelClass.getSimpleName(), fullPanelName)) {
                resultPanel = PanelFactory.get(panelClass);
                break;
            }
        }
        if (resultPanel == null) {
            LOG.error("{} panel is not found. Check class name and location", panelName);
            throw new IllegalStateException();
        }
        return resultPanel.asWebElement();
    }
}
