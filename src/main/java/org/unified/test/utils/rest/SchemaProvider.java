package org.unified.test.utils.rest;

import org.unified.test.utils.ResourceProvider;
import org.unified.test.utils.properties.EnhancedSystemProperty;
import org.unified.test.utils.properties.PropertiesUtil;

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
