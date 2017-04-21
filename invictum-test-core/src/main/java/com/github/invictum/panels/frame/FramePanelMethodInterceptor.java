package com.github.invictum.panels.frame;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

public class FramePanelMethodInterceptor implements MethodInterceptor {

    private static final Logger LOG = LoggerFactory.getLogger(FramePanelMethodInterceptor.class);

    private WebDriver driver;
    private By locator;

    public FramePanelMethodInterceptor(WebDriver driver, By locator) {
        this.driver = driver;
        this.locator = locator;
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        LOG.debug("Processing {}", method.toGenericString());
        if (isNativeMethod(method, o)) {
            LOG.info("Switching to {} frame", locator);
            driver.switchTo().frame(driver.findElement(locator));
            Object result = methodProxy.invokeSuper(o, objects);
            LOG.info("Switching to default content");
            driver.switchTo().defaultContent();
            return result;
        }
        return methodProxy.invokeSuper(o, objects);
    }

    private boolean isNativeMethod(Method method, Object object) {
        return object.getClass().getSuperclass().equals(method.getDeclaringClass());
    }
}
