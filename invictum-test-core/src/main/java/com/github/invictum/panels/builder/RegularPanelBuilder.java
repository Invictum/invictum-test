package com.github.invictum.panels.builder;

import com.github.invictum.panels.AbstractPanel;
import com.github.invictum.panels.init.PanelInitUtil;
import com.github.invictum.panels.proxy.LazyPanelElementFactory;
import net.serenitybdd.core.pages.WebElementFacade;

import java.util.ArrayList;
import java.util.List;

public class RegularPanelBuilder<T extends AbstractPanel> extends PanelBuilder<T> {

    public RegularPanelBuilder(Class<T> panelClass) {
        super(panelClass);
    }

    @Override
    public T build() {
        getPage().activateIfJQueryRelated(locator());
        PanelInitUtil.applyGlobalInitStrategy(panelClass(), getPage());
        T panelInstance = assemblePanelWithReflection();
        panelInstance.initWith(getPage(), LazyPanelElementFactory.produce(getPage(), locator()));
        PanelInitUtil.invokeWhenInitializedMethods(panelInstance);
        return panelInstance;
    }

    @Override
    public List<T> buildAll() {
        getPage().activateIfJQueryRelated(locator());
        List<WebElementFacade> panels = getPage().findAll(locator());
        PanelInitUtil.applyGlobalInitStrategy(panelClass(), getPage());
        List<T> panelList = new ArrayList<>();
        for (WebElementFacade element : panels) {
            T panel = assemblePanelWithReflection();
            panel.initWith(getPage(), element);
            PanelInitUtil.invokeWhenInitializedMethods(panel);
            panelList.add(panel);
        }
        return panelList;
    }
}
