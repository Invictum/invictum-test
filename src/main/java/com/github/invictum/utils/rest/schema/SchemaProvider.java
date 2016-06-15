package com.github.invictum.utils.rest.schema;

import com.github.invictum.utils.ResourceProvider;
import com.github.invictum.utils.properties.EnhancedSystemProperty;
import com.github.invictum.utils.properties.PropertiesUtil;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class SchemaProvider {

    private static String schemaRootDir = PropertiesUtil.getProperty(EnhancedSystemProperty.SchemasDirectory);
    private static Map<String, File> schemas = new HashMap<>();

    private SchemaProvider() {
        // disable constructor.
    }

    public static File get(final String schemaName) {
        if (!schemas.containsKey(schemaName)) {
            File schemaFile = ResourceProvider.getFile(schemaRootDir, schemaName);
            schemas.put(schemaName, schemaFile);
        }
        return schemas.get(schemaName);
    }
}
