package com.github.invictum.panels.builder;

import com.github.invictum.panels.AbstractPanel;

public class AssemblePanelException extends RuntimeException {

    public AssemblePanelException(Class<? extends AbstractPanel> panelClass) {
        String message = String.format("Failed to init %s panel", panelClass.getSimpleName());
        throw new RuntimeException(message);
    }
}
