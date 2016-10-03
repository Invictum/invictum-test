package com.github.invictum.test.utils.rest;

import com.github.invictum.utils.rest.RestProfile;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(JUnit4.class)
public class RestProfileTest {

    private RestProfile restProfile = null;

    @Before
    public void beforeClass() {
        restProfile = new RestProfile();
    }

    @Test
    public void nameTest() {
        restProfile.setName("name");
        assertThat("Profile name is wrong.", restProfile.getName(), is("name"));
    }

    @Test
    public void pathTest() {
        restProfile.setPath("path");
        assertThat("Profile path is wrong.", restProfile.getPath(), is("path"));
    }

    @Test
    public void cookiesTest() {
        Map<String, String> cookies = new HashMap<>();
        cookies.put("cookieKey", "cookieValue");
        restProfile.setCookies(cookies);
        assertThat("Profile cookies are wrong.", restProfile.getCookies(), equalTo(cookies));
    }

    @Test
    public void headersTest() {
        Map<String, String> headers = new HashMap<>();
        headers.put("headerKey", "headerValue");
        restProfile.setHeaders(headers);
        assertThat("Profile headers are wrong.", restProfile.getHeaders(), equalTo(headers));
    }
}
