package com.github.invictum.panels.builder;

import com.github.invictum.locator.factory.LocatorFactory;
import com.github.invictum.pages.AbstractPage;
import com.github.invictum.panels.AbstractPanel;
import com.github.invictum.unified.data.provider.UnifiedDataProviderFactory;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public abstract class PanelBuilder<T extends AbstractPanel> {

    public static final Logger LOG = LoggerFactory.getLogger(PanelBuilder.class);

    private AbstractPage parentPage;
    private Class<T> panelClass;

    public PanelBuilder(Class<T> panelClass) {
        this.panelClass = panelClass;
    }

    public PanelBuilder<T> setPage(AbstractPage page) {
        this.parentPage = page;
        return this;
    }

    protected AbstractPage getPage() {
        return parentPage;
    }

    protected Class<T> panelClass() {
        return panelClass;
    }

    protected By locator() {
        String locator = UnifiedDataProviderFactory.getInstance(panelClass()).getBase();
        return LocatorFactory.build(locator);
    }

    protected T assemblePanelWithReflection() {
        LOG.debug("Assemble panel via reflection");
        T panel = null;
        try {
            panel = panelClass().newInstance();
        } catch (ReflectiveOperationException e) {
            LOG.error("Failed to assemble {} panel", panelClass().getSimpleName());
            throw new AssemblePanelException(panelClass());
        }
        return panel;
    }

    public abstract T build();

    public abstract List<T> buildAll();
}
