package com.github.invictum.panels;

import com.github.invictum.locator.factory.LocatorFactory;
import com.github.invictum.pages.AbstractPage;
import com.github.invictum.panels.builder.BuilderFactory;
import com.github.invictum.panels.builder.PanelBuilder;
import com.github.invictum.tricks.Visibility;
import com.github.invictum.unified.data.provider.UnifiedDataProviderFactory;
import com.github.invictum.utils.ResourceProvider;
import com.github.invictum.utils.properties.PropertiesUtil;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.github.invictum.utils.properties.EnhancedSystemProperty.PanelsPackageName;

public class PanelFactory {

    public static final String PANELS_PACKAGE = PropertiesUtil.getProperty(PanelsPackageName);
    public static final Logger LOG = LoggerFactory.getLogger(PanelFactory.class);

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
        PanelBuilder<T> builder = BuilderFactory.builderInstance(panelClass);
        return builder.setPage(parentPage).build();
    }

    /**
     * Method checks panels visibility gracefully.
     *
     * @param panelClass panelClass
     * @param parentPage parentPage
     * @return boolean
     */
    public static boolean isPanelVisible(Class<? extends AbstractPanel> panelClass, AbstractPage parentPage) {
        String locatorValue = UnifiedDataProviderFactory.getInstance(panelClass).getBase();
        By panelLocator = LocatorFactory.build(locatorValue);
        parentPage.activateIfJQueryRelated(panelLocator);
        return parentPage.getTrick(Visibility.class).isElementVisible(panelLocator);
    }

    public static <T extends AbstractPanel> List<T> getAll(final Class<T> panelClass, final AbstractPage parentPage) {
        PanelBuilder<T> builder = BuilderFactory.builderInstance(panelClass);
        return builder.setPage(parentPage).buildAll();
    }

    private static <T extends AbstractPanel> void verifyBaseAttribute(Class<T> panelClass, String locator) {
        if (locator == null) {
            String className = panelClass.getSimpleName();
            LOG.error("Base attribute is absent for {}. Specify it in locators source.", panelClass);
            throw new IllegalStateException(String.format("Unable to init %s panel", className));
        }
    }
}
