package org.example.utils.rest;

import org.example.utils.ResourceProvider;
import org.example.utils.properties.EnhancedSystemProperty;
import org.example.utils.properties.PropertiesUtil;

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
