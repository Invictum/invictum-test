package com.github.invictum.test.locator.factory;

import com.github.invictum.locator.factory.LocatorFactory;
import net.serenitybdd.core.annotations.findby.By;
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

    @Test
    public void buildXpathTest() {
        assertThat("Xpath locator was built wrong.", LocatorFactory.build("//div"), equalTo(By.xpath("//div")));
    }

    @Test
    public void buildCssTest() {
        assertThat("Css locator was built wrong.", LocatorFactory.build("div.class"),
                equalTo(By.cssSelector("div.class")));
    }

    @Test
    public void buildParametrizedTest() {
        assertThat("Parametrized locator was built wrong.",
                LocatorFactory.build("//div[@id = 'test-{2}-{1}']", "one", "two"),
                equalTo(By.xpath("//div[@id = 'test-two-one']")));
    }

    @Test
    public void buildParametrizedBoundsTest() {
        assertThat("Parametrized locator was built wrong.", LocatorFactory.build("{1}#temp-{2}", "div", "id"),
                equalTo(By.cssSelector("div#temp-id")));
    }

    @Test
    public void nonParametrizedTemplateTest() {
        assertThat("Parametrized locator was built wrong.",
                LocatorFactory.build("//div[@id = 'test-one-two']", "one", "two"),
                equalTo(By.xpath("//div[@id = 'test-one-two']")));
    }
}
