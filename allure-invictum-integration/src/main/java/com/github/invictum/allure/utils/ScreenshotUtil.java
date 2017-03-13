package com.github.invictum.allure.utils;

import net.thucydides.core.model.TestOutcome;
import net.thucydides.core.steps.StepEventBus;
import net.thucydides.core.webdriver.ThucydidesWebDriverSupport;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ScreenshotUtil {

    public static final Logger LOG = LoggerFactory.getLogger(ScreenshotUtil.class);

    private ScreenshotUtil() {
        //disable default constructor.
    }

    private static boolean currentTestIsABrowserTest() {
        return ThucydidesWebDriverSupport.isDriverInstantiated();
    }

    private static <T> T last(List<T> list) {
        return list.get(list.size() - 1);
    }

    private static byte[] fileContent(File file) {
        byte[] content = new byte[] {};
        while (!file.exists()) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                LOG.error("Serenity screenshot wait was interrupted.");
            }
        }
        try {
            content = FileUtils.readFileToByteArray(file);
        } catch (IOException e) {
            LOG.debug("Failed to get screenshot from Serenity. No screenshot will be attached.");
        }
        return content;
    }

    private static byte[] getScreenshotData() {
        List<TestOutcome> testOutcome = StepEventBus.getEventBus().getBaseStepListener().getTestOutcomes();
        if (!testOutcome.isEmpty()) {
            if (!last(testOutcome).getScreenshotAndHtmlSources().isEmpty()) {
                File file = last(last(testOutcome).getScreenshotAndHtmlSources()).getScreenshot();
                byte[] content = fileContent(file);
                return (content.length == 0) ? null : content;
            }
        }
        return null;
    }

    public static byte[] takeScreenshotContent() {
        return currentTestIsABrowserTest() ? getScreenshotData() : null;
    }
}
