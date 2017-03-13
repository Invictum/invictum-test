package com.github.invictum.allure.test.utils;

import com.github.invictum.allure.utils.ScreenshotUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(JUnit4.class)
public class ScreenshotUtilTest {

    @Test
    public void screenshotNoUiTest() {
        assertThat("Screenshot data is wrong.", ScreenshotUtil.takeScreenshotContent(), nullValue());
    }
}
