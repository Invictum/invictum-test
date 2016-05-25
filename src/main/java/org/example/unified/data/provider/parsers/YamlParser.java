package org.example.unified.data.provider.parsers;

import org.example.Log;
import org.example.unified.data.provider.UnifiedDataProvider;
import org.example.utils.ResourceProvider;
import org.example.utils.properties.PropertiesUtil;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static org.example.utils.properties.EnhancedSystemProperty.LocatorsDirectory;

public class YamlParser implements Parser {

    private String locatorDirectory = PropertiesUtil.getProperty(LocatorsDirectory);

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
