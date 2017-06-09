package com.github.invictum.panels.builder;

import com.github.invictum.panels.AbstractPanel;
import com.github.invictum.panels.frame.FramePanelMethodInterceptor;
import com.github.invictum.panels.init.PanelInitUtil;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

import java.util.List;

public class FramePanelBuilder<T extends AbstractPanel> extends PanelBuilder<T> {

    public FramePanelBuilder(Class<T> panelClass) {
        super(panelClass);
    }

    @Override
    public T build() {
        // Start panel init
        getPage().activateIfJQueryRelated(locator());
        PanelInitUtil.applyGlobalInitStrategy(panelClass(), getPage());
        // Build panel as a proxy with auto switch
        Enhancer enhancer = new Enhancer();
        enhancer.setClassLoader(panelClass().getClassLoader());
        enhancer.setSuperclass(panelClass());
        MethodInterceptor interceptor = new FramePanelMethodInterceptor(getPage().getDriver(), locator());
        enhancer.setCallback(interceptor);
        T panelInstance = (T) enhancer.create();
        // Finish panel init
        panelInstance.initWith(getPage(), null);
        PanelInitUtil.invokeWhenInitializedMethods(panelInstance);
        return panelInstance;
    }

    @Override
    public List<T> buildAll() {
        throw new IllegalStateException("Failed to init several frame panels");
    }
}
