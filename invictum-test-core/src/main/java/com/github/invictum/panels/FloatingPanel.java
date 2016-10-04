package com.github.invictum.panels;

import com.github.invictum.unified.data.provider.UnifiedDataProviderUtil;

public class FloatingPanel extends AbstractPanel {

    @Override
    protected String locatorValue(final String locatorKey) {
        return UnifiedDataProviderUtil.getLocatorByKey(locatorKey, dataProvider);
    }
}
