package com.github.invictum.unified.data.provider.parsers;

import com.github.invictum.unified.data.provider.UnifiedDataProvider;

public interface Parser {
    public UnifiedDataProvider load(final String fileName);
}
