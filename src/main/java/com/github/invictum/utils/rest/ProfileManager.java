package com.github.invictum.utils.rest;

import com.github.invictum.Log;
import com.github.invictum.utils.ResourceProvider;
import com.github.invictum.utils.properties.EnhancedSystemProperty;
import com.github.invictum.utils.properties.PropertiesUtil;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ProfileManager {

    /** Rest profiles root starting from resources */
    private final static String PROFILES_ROOT = PropertiesUtil.getProperty(EnhancedSystemProperty.ProfilesDirectory);

    public static RestProfile getProfile(String profileName) {
        RestProfile profile = null;
        try {
            File profileFile = ResourceProvider.getFile(PROFILES_ROOT, profileName + ".yaml");
            Yaml yaml = new Yaml(new Constructor(RestProfile.class));
            profile = (RestProfile) yaml.load(new FileInputStream(profileFile));
        } catch (FileNotFoundException e) {
            Log.error("Failed to load '{}' profile", profileName);
        }
        return profile;
    }
}
