package com.github.invictum.unified.data.provider.parsers;

import com.github.invictum.Log;
import com.github.invictum.unified.data.provider.UnifiedDataProvider;
import com.github.invictum.utils.properties.EnhancedSystemProperty;
import com.github.invictum.utils.properties.PropertiesUtil;
import com.github.invictum.utils.ResourceProvider;
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
