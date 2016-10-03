package com.github.invictum.test.utils.rest;

import com.github.invictum.utils.ResourceProvider;
import com.github.invictum.utils.properties.EnhancedSystemProperty;
import com.github.invictum.utils.properties.PropertiesUtil;
import com.github.invictum.utils.rest.ProfileManager;
import com.github.invictum.utils.rest.RestProfile;
import org.apache.commons.io.FileUtils;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ProfileManager.class, PropertiesUtil.class, ResourceProvider.class})
public class ProfileMangerTest {

    @Rule
    public static TemporaryFolder folder = new TemporaryFolder();

    private static RestProfile profile = null;

    @BeforeClass
    public static void beforeClass() throws IOException {
        PowerMockito.mockStatic(PropertiesUtil.class);
        PowerMockito.when(PropertiesUtil.getProperty(EnhancedSystemProperty.ProfilesDirectory)).thenReturn("root");
        folder.create();
        File yamlFile = folder.newFile();
        FileUtils.write(yamlFile, "name: Profile\n" +
                "path: /\n" +
                "headers:\n" +
                "    Content-Type: application/json\n" +
                "cookies:\n" +
                "    Test-Cookie: cookieValue");
        PowerMockito.mockStatic(ResourceProvider.class);
        PowerMockito.when(ResourceProvider.getFile("root", "profile.yaml")).thenReturn(yamlFile);
        profile = ProfileManager.getProfile("profile");
    }

    @Test
    public void getPathTest() {
        assertThat("Path is wrong.", profile.getPath(), equalTo("/"));
    }

    @Test
    public void getNameTest() {
        assertThat("Name is wrong.", profile.getName(), equalTo("Profile"));
    }

    @Test
    public void getHeadersTest() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        assertThat("Headers are wrong.", profile.getHeaders(), equalTo(headers));
    }

    @Test
    public void getCookiesTest() {
        Map<String, String> cookies = new HashMap<>();
        cookies.put("Test-Cookie", "cookieValue");
        assertThat("Cookies are wrong.", profile.getCookies(), equalTo(cookies));
    }
}
