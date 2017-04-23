package com.github.invictum.panels.builder;

import com.github.invictum.panels.AbstractPanel;
import com.github.invictum.panels.FloatingPanel;
import com.github.invictum.panels.frame.FramePanel;

public class BuilderFactory {

    public static <T extends AbstractPanel> PanelBuilder<T> builderInstance(Class<T> panelClass) {
        PanelBuilder<T> constructor = new RegularPanelBuilder<>(panelClass);
        if (FloatingPanel.class.isAssignableFrom(panelClass)) {
            constructor = new FloatingPanelBuilder<>(panelClass);
        } else if (FramePanel.class.isAssignableFrom(panelClass)) {
            constructor = new FramePanelBuilder<>(panelClass);
        }
        return constructor;
    }
}
