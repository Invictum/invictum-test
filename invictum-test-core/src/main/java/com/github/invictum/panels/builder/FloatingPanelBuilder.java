package com.github.invictum.panels.builder;

import com.github.invictum.locator.factory.LocatorFactory;
import com.github.invictum.panels.AbstractPanel;
import com.github.invictum.panels.init.PanelInitUtil;
import com.github.invictum.panels.proxy.LazyPanelElementFactory;
import com.github.invictum.unified.data.provider.UnifiedDataProviderFactory;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.List;

public class FloatingPanelBuilder<T extends AbstractPanel> extends PanelBuilder<T> {

    public FloatingPanelBuilder(Class<T> panelClass) {
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
        if (UnifiedDataProviderFactory.getInstance(panelClass()).getBase() == null) {
            throw new IllegalStateException(String
                    .format("Attempt to init a list of %s Floating Panels", panelClass().getSimpleName()));
        }
        getPage().activateIfJQueryRelated(locator());
        PanelInitUtil.applyGlobalInitStrategy(panelClass(), getPage());
        List<WebElementFacade> panels = getPage().findAll(locator());
        List<T> panelList = new ArrayList<>();
        for (WebElementFacade element : panels) {
            T panel = assemblePanelWithReflection();
            panel.initWith(getPage(), element);
            PanelInitUtil.invokeWhenInitializedMethods(panel);
            panelList.add(panel);
        }
        return panelList;
    }

    @Override
    protected By locator() {
        String base = UnifiedDataProviderFactory.getInstance(panelClass()).getBase();
        return LocatorFactory.build((base == null) ? "//body" : base);
    }
}
