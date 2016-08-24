package com.github.invictum.steps;

import com.github.invictum.pages.AbstractPage;
import com.github.invictum.utils.ResourceProvider;
import com.github.invictum.utils.properties.EnhancedSystemProperty;
import com.github.invictum.utils.properties.PropertiesUtil;
import net.serenitybdd.core.SerenitySystemProperties;
import net.thucydides.core.ThucydidesSystemProperty;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.pages.Pages;
import org.apache.commons.lang3.StringUtils;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;
import java.util.concurrent.TimeUnit;

public class PageNavigationSteps extends AbstractSteps {

    public static final String PAGES_PACKAGE = PropertiesUtil.getProperty(EnhancedSystemProperty.PagesPackageName);
    public static final String PAGE_SUFFIX = "Page";
    public static final Logger LOG = LoggerFactory.getLogger(PageNavigationSteps.class);
    public static final int TIMEOUT = SerenitySystemProperties.getProperties()
            .getIntegerValue(ThucydidesSystemProperty.WEBDRIVER_TIMEOUTS_IMPLICITLYWAIT, 30000);

    public PageNavigationSteps() {
        super();
        checkPackage();
    }

    public PageNavigationSteps(Pages pages) {
        super(pages);
        checkPackage();
    }

    @Step
    public void openPage(String pageName) {
        AbstractPage pageToOpen = getPageByName(pageName);
        pageToOpen.setImplicitTimeout(TIMEOUT, TimeUnit.MILLISECONDS);
        pageToOpen.open();
        pageToOpen.resetImplicitTimeout();
    }

    @Step
    public void openPageWithParams(String pageName, String... params) {
        AbstractPage pageToOpen = getPageByName(pageName);
        pageToOpen.setImplicitTimeout(TIMEOUT, TimeUnit.MILLISECONDS);
        pageToOpen.open(params);
        pageToOpen.resetImplicitTimeout();
    }

    private AbstractPage getPageByName(String pageName) {
        String fullPageName = String.format("%s%s", pageName, PAGE_SUFFIX);
        AbstractPage resultPage = null;
        Reflections reflections = new Reflections(ClasspathHelper.forPackage(PAGES_PACKAGE));
        Set<Class<? extends AbstractPage>> availableClasses = reflections.getSubTypesOf(AbstractPage.class);
        for (Class<? extends AbstractPage> pageClass : availableClasses) {
            if (StringUtils.equals(pageClass.getSimpleName(), fullPageName)) {
                resultPage = pages().getPage(pageClass);
                break;
            }
        }
        if (resultPage == null) {
            throw new IllegalStateException(String.format("%s page is not found", pageName));
        }
        return resultPage;
    }

    private void checkPackage() {
        if (!ResourceProvider.isPackagePresent(PAGES_PACKAGE)) {
            LOG.error("Configure pages package with '{}' property", EnhancedSystemProperty.PagesPackageName);
        }
    }
}
