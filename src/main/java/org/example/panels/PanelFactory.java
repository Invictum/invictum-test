package org.example.panels;

import net.serenitybdd.core.pages.WebElementFacade;
import org.apache.commons.lang3.StringUtils;
import org.example.Log;
import org.example.pages.AbstractPage;
import org.example.utils.properties.PropertiesUtil;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;

import java.util.Set;

import static org.example.utils.properties.EnhancedSystemProperty.PanelsPackageName;

public class PanelFactory {

    public static final String SUFFIX = "Panel";
    public static final String PANELS_PACKAGE = PropertiesUtil.getProperty(PanelsPackageName);

    private PanelFactory() {
        // disable constructor.
    }

    public static <T extends AbstractPanel> T get(final Class<T> panelClass, final AbstractPage parentPage) {
        T panelInstance = getPanel(panelClass);
        panelInstance.initWith(parentPage);
        return panelInstance;
    }

    public static <T extends AbstractPanel> T get(final Class<T> panelClass) {
        return getPanel(panelClass);
    }

    private static <T extends AbstractPanel> T getPanel(final Class<T> panelClass) {
        T panelInstance;
        try {
            panelInstance = panelClass.newInstance();
            Log.debug("New {} is initialized", panelInstance);
        } catch (ReflectiveOperationException e) {
            Log.error("Failed to init panel - {}", e.getCause());
            throw new IllegalStateException(String.format("Failed to init %s", panelClass));
        }
        return panelInstance;
    }

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
            throw new IllegalStateException(String.format("%s panel is not found", panelName));
        }
        return resultPanel.asWebElement();
    }
}
