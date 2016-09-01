package com.github.invictum.test.dto;

import com.github.invictum.utils.properties.EnhancedSystemProperty;
import com.github.invictum.utils.properties.PropertiesUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(PowerMockRunner.class)
@PrepareForTest(PropertiesUtil.class)
public class DtoTestFullView {

    private ExampleDto sut = null;

    @Before
    public void initTestDto() {
        PowerMockito.mockStatic(PropertiesUtil.class);
        PowerMockito.when(PropertiesUtil.getProperty(EnhancedSystemProperty.FullDtoView)).thenReturn("true");
        sut = new ExampleDto();
    }

    @Test
    public void toStringTest() {
        sut.setTextValue("name");
        sut.setBoolValue(true);
        sut.setIntValue(1);
        String expected = "{textValue: name, boolValue: true, intValue: 1}";
        assertThat("To string method proceed wrong.", sut.toString(), equalTo(expected));
    }

    @Test
    public void toStringNullObjectTest() {
        String expected = "{textValue: null, boolValue: null, intValue: 0}";
        assertThat("Null object converted to string wrong.", sut.toString(), equalTo(expected));
    }
}
