package com.github.invictum.locator.factory;

import com.github.invictum.locator.factory.providers.LocatorProvider;
import net.serenitybdd.core.annotations.findby.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ServiceLoader;

public class LocatorFactory {

    private final static Logger LOG = LoggerFactory.getLogger(LocatorFactory.class);
    private static ServiceLoader<LocatorProvider> providers = ServiceLoader.load(LocatorProvider.class);

    private LocatorFactory() {
        //disable constructor.
    }

    private static boolean isParametrizedTemplate(String template) {
        return template.matches("^(.+?|)\\{\\d+?\\}(.+?|)$");
    }

    private static String constructParametrized(String template, String... parameters) {
        if (!isParametrizedTemplate(template)) {
            LOG.warn("Proposed template '{}' isn't ready for parameterization.", template);
            return template;
        }
        for (int index = 1; index <= parameters.length; index++) {
            String replaceParameter = String.format("{%s}", index);
            template = template.replace(replaceParameter, parameters[index - 1]);
        }
        return template;
    }

    public static boolean isXpath(String xpathCandidate) {
        return xpathCandidate.matches("^(html/|(\\.|)/).+$");
    }

    public static org.openqa.selenium.By build(String locatorString, String... parameters) {
        String locatorCandidate = locatorString;
        if (parameters.length > 0) {
            locatorCandidate = constructParametrized(locatorCandidate, parameters);
        }
        for (LocatorProvider provider : providers) {
            if (provider.isAcceptableTo(locatorCandidate)) {
                return provider.wrap(locatorCandidate);
            }
        }
        return isXpath(locatorCandidate) ? By.xpath(locatorCandidate) : By.cssSelector(locatorCandidate);
    }
}
