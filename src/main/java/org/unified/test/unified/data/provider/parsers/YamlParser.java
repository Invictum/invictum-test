package org.unified.test.unified.data.provider.parsers;

import org.unified.test.Log;
import org.unified.test.unified.data.provider.UnifiedDataProvider;
import org.unified.test.utils.ResourceProvider;
import org.unified.test.utils.properties.PropertiesUtil;
import org.unified.test.utils.properties.EnhancedSystemProperty;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class YamlParser implements Parser {

    private String locatorDirectory = PropertiesUtil.getProperty(EnhancedSystemProperty.LocatorsDirectory);

    @Override
    public UnifiedDataProvider load(final String fileName) {
        UnifiedDataProvider unifiedDataProvider = null;
        try {
            File yamlFile = ResourceProvider.getFile(locatorDirectory, fileName + ".yaml");
            Yaml yaml = new Yaml(new Constructor(UnifiedDataProvider.class));
            unifiedDataProvider = (UnifiedDataProvider) yaml.load(new FileInputStream(yamlFile));
        } catch (FileNotFoundException e) {
            Log.error("Yaml file not found for {}", fileName);
        }
        return unifiedDataProvider;
    }
}
