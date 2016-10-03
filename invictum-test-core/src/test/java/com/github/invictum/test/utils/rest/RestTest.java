package com.github.invictum.test.utils.rest;

import com.github.invictum.utils.properties.EnhancedSystemProperty;
import com.github.invictum.utils.properties.PropertiesUtil;
import com.github.invictum.utils.rest.ProfileManager;
import com.github.invictum.utils.rest.Rest;
import com.github.invictum.utils.rest.RestProfile;
import net.serenitybdd.rest.SerenityRest;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(PowerMockRunner.class)
@PrepareForTest({SerenityRest.class, ProfileManager.class, PropertiesUtil.class})
public class RestTest {

    @Mock
    private RestProfile profileMock;

    @BeforeClass
    public static void beforeClass() {
        PowerMockito.mockStatic(PropertiesUtil.class);
        PowerMockito.when(PropertiesUtil.getProperty(EnhancedSystemProperty.ApiEndpointDefault))
                .thenReturn("http://example.org");
    }

    @Test
    public void baseUriTest() {
        Rest.setBaseUri("http://example.org");
        assertThat("Base URI is wrong.", Rest.getBaseUri(), equalTo("http://example.org"));
    }

    @Test
    public void responseTest() {
        PowerMockito.mockStatic(SerenityRest.class);
        PowerMockito.when(SerenityRest.then()).thenReturn(null);
        assertThat("Response is wrong.", Rest.response(), equalTo(null));
    }

    @Test
    public void requestProfileTest() {
        PowerMockito.mockStatic(ProfileManager.class);
        PowerMockito.when(ProfileManager.getProfile("profile")).thenReturn(profileMock);
        Rest.request("profile");
        Mockito.verify(profileMock, Mockito.times(1)).getHeaders();
        Mockito.verify(profileMock, Mockito.times(1)).getCookies();
        Mockito.verify(profileMock, Mockito.times(1)).getPath();
    }

    @Test
    public void requestSavedProfileTest() {
        PowerMockito.mockStatic(ProfileManager.class);
        PowerMockito.when(ProfileManager.getProfile("profile")).thenReturn(profileMock);
        Rest.request("profile");
        Mockito.verify(profileMock, Mockito.never()).getHeaders();
        Mockito.verify(profileMock, Mockito.never()).getCookies();
        Mockito.verify(profileMock, Mockito.never()).getPath();
    }

    @Test
    public void requestDefaultProfileTest() {
        PowerMockito.mockStatic(ProfileManager.class);
        PowerMockito.when(ProfileManager.getProfile("default")).thenReturn(profileMock);
        Rest.request();
        Mockito.verify(profileMock, Mockito.times(1)).getHeaders();
        Mockito.verify(profileMock, Mockito.times(1)).getCookies();
        Mockito.verify(profileMock, Mockito.times(1)).getPath();
    }
}
