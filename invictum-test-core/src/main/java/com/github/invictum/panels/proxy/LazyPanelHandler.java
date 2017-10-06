package com.github.invictum.panels.proxy;

import com.github.invictum.pages.AbstractPage;
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
        Object invoked;
        try {
            invoked = method.invoke(page.find(locator), args);
        }
        catch (Exception e) {
            if (e.getCause() != null) {
                throw e.getCause();
            }
            throw e;
        }
        return invoked;
    }
}
