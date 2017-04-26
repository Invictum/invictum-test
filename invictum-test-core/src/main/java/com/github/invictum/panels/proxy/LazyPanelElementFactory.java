package com.github.invictum.panels.proxy;

import com.github.invictum.pages.AbstractPage;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.By;

import java.lang.reflect.Proxy;

public class LazyPanelElementFactory {

    public static WebElementFacade produce(AbstractPage page, By locator) {
        LazyPanelHandler handler = new LazyPanelHandler(page, locator);
        return (WebElementFacade) Proxy.newProxyInstance(LazyPanelElementFactory.class
                .getClassLoader(), new Class[]{WebElementFacade.class}, handler);
    }
}
