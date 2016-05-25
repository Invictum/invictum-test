package com.github.invictum.utils.rest;

import com.github.invictum.utils.properties.EnhancedSystemProperty;
import com.github.invictum.utils.properties.PropertiesUtil;
import com.github.invictum.utils.ResourceProvider;

import java.io.File;

public class SchemaProvider {

    private static String schemaRootDir = PropertiesUtil.getProperty(EnhancedSystemProperty.SchemasDirectory);

    private SchemaProvider() {
        // disable constructor.
    }

    public static File get(final String schemaName) {
        return ResourceProvider.getFile(schemaRootDir, schemaName);
    }
}
