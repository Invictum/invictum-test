package com.github.invictum.panels.builder;

import com.github.invictum.locator.factory.LocatorFactory;
import com.github.invictum.panels.AbstractPanel;
import com.github.invictum.panels.init.PanelInitUtil;
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
        String base = UnifiedDataProviderFactory.getInstance(panelClass()).getBase();
        String locator = (base == null) ? "//body" : base;
        By panelLocator = LocatorFactory.build(locator);
        getPage().activateIfJQueryRelated(panelLocator);
        WebElementFacade panel = getPage().find(panelLocator);
        PanelInitUtil.applyGlobalInitStrategy(panelClass(), getPage());
        T panelInstance = assemblePanelWithReflection();
        panelInstance.initWith(getPage(), panel);
        PanelInitUtil.invokeWhenInitializedMethods(panelInstance);
        return panelInstance;
    }

    @Override
    public List<T> buildAll() {
        String locator = UnifiedDataProviderFactory.getInstance(panelClass()).getBase();
        if (locator == null) {
            throw new IllegalStateException(String
                    .format("Attempt to init a list of % Floating Panels", panelClass().getSimpleName()));
        }
        By panelLocator = LocatorFactory.build(locator);
        getPage().activateIfJQueryRelated(panelLocator);
        List<WebElementFacade> panels = getPage().findAll(panelLocator);
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

    @Override
    protected By locator() {
        String base = UnifiedDataProviderFactory.getInstance(panelClass()).getBase();
        return LocatorFactory.build((base == null) ? "//body" : base);
    }
}
