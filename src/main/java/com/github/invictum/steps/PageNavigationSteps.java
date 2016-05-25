package com.github.invictum.steps;

import com.github.invictum.pages.AbstractPage;
import com.github.invictum.utils.properties.EnhancedSystemProperty;
import com.github.invictum.utils.properties.PropertiesUtil;
import net.thucydides.core.annotations.Step;
import org.apache.commons.lang3.StringUtils;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;

import java.util.Set;
import java.util.concurrent.TimeUnit;

public class PageNavigationSteps extends AbstractSteps {

    public static final String PAGES_PACKAGE = PropertiesUtil.getProperty(EnhancedSystemProperty.PagesPackageName);
    public final static String PAGE_SUFFIX = "Page";

    @Step
    public void openPage(String pageName) {
        AbstractPage pageToOpen = getPageByName(pageName);
        pageToOpen.setImplicitTimeout(30, TimeUnit.SECONDS);
        pageToOpen.open();
        pageToOpen.resetImplicitTimeout();
    }

    @Step
    public void openPageWithParams(String pageName, String... params) {
        AbstractPage pageToOpen = getPageByName(pageName);
        pageToOpen.setImplicitTimeout(30, TimeUnit.SECONDS);
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
}
