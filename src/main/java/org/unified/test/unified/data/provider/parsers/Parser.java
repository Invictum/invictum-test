package org.unified.test.unified.data.provider.parsers;

import org.unified.test.unified.data.provider.UnifiedDataProvider;

public interface Parser {
    public UnifiedDataProvider load(final String fileName);
}
