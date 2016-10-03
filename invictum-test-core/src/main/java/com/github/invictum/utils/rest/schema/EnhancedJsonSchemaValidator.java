package com.github.invictum.utils.rest.schema;

import com.jayway.restassured.module.jsv.JsonSchemaValidator;

import java.io.File;

public abstract class EnhancedJsonSchemaValidator {

    public static JsonSchemaValidator matchesJsonSchema(String schemaName) {
        File schemaFile = SchemaProvider.get(schemaName);
        return JsonSchemaValidator.matchesJsonSchema(schemaFile);
    }
}
