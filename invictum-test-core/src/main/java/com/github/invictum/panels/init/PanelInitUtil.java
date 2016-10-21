package com.github.invictum.panels.init;

import com.github.invictum.pages.AbstractPage;
import com.github.invictum.panels.AbstractPanel;
import com.github.invictum.panels.strategy.PanelInitStrategy;
import com.github.invictum.utils.properties.PropertiesUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

import static com.github.invictum.utils.properties.EnhancedSystemProperty.PanelInitStrategy;

public class PanelInitUtil {

    public static final Logger LOG = LoggerFactory.getLogger(PanelInitUtil.class);
    private static PanelInitStrategy strategy;

    private PanelInitUtil() {
        // disable constructor
    }

    static {
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

    public static <T extends AbstractPanel> void applyGlobalInitStrategy(Class<T> panelClass, AbstractPage parentPage) {
        if (!panelClass.isAnnotationPresent(DisableGlobalInitStrategy.class)) {
            strategy.apply(parentPage);
        }
    }

    public static void invokeWhenInitializedMethods(AbstractPanel panel) {
        LOG.debug("Try to invoke init method for '{}' panel", panel);
        for (Method method : panel.getClass().getMethods()) {
            if (method.isAnnotationPresent(WhenPanelInitializes.class)) {
                LOG.debug("Found '{}' init method for '{}'", method, panel);
                try {
                    method.setAccessible(true);
                    method.invoke(panel);
                } catch (ReflectiveOperationException e) {
                    LOG.error("Failed to invoke '{}' init method for '{}' panel", method.toString(), panel);
                    LOG.error(e.getMessage());
                }
            }
        }
    }
}
