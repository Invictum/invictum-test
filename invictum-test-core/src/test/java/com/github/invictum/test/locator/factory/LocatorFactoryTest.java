package com.github.invictum.test.locator.factory;

import com.github.invictum.locator.factory.LocatorFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(JUnit4.class)
public class LocatorFactoryTest {

    @Test
    public void isXpathTest() {
        assertThat("Specified locator classified wrong.", LocatorFactory.isXpath("//div[@id]"), equalTo(true));
    }

    @Test
    public void isXpathWithRootTest() {
        assertThat("Specified locator classified wrong.", LocatorFactory.isXpath(".//div[@id]"), equalTo(true));
    }

    @Test
    public void isXpathFullDescribedTest() {
        assertThat("Specified locator classified wrong.", LocatorFactory.isXpath("html/div[@id]"), equalTo(true));
    }

    @Test
    public void isXpathCssTest() {
        assertThat("Specified locator classified wrong.", LocatorFactory.isXpath("div.class"), equalTo(false));
    }
}
