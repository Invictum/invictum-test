package org.example.utils;

import java.awt.*;

public class ScreenResolutionUtil {

    private static GraphicsDevice getDevice() {
        return GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    }

    public static String getHeight() {
        return String.valueOf(getDevice().getDisplayMode().getHeight());
    }

    public static String getWidth() {
        return String.valueOf(getDevice().getDisplayMode().getWidth());
    }
}