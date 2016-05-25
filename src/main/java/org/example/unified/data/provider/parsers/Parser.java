package org.example.unified.data.provider.parsers;

import org.example.unified.data.provider.UnifiedDataProvider;

public interface Parser {
    public UnifiedDataProvider load(final String fileName);
}
