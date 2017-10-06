package com.github.invictum.panels.proxy;

import com.github.invictum.pages.AbstractPage;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.By;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class LazyPanelHandler implements InvocationHandler {

    private AbstractPage page;
    private By locator;

    public LazyPanelHandler(AbstractPage page, By locator) {
        this.page = page;
        this.locator = locator;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try {
            WebElementFacade webElement = page.find(locator);
            return method.invoke(webElement, args);
        } catch (Exception e) {
            throw e.getCause() == null ? e : e.getCause();
        }
    }
}
