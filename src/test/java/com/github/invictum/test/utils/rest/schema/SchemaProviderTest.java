package com.github.invictum.test.utils.rest.schema;

import com.github.invictum.utils.ResourceProvider;
import com.github.invictum.utils.properties.EnhancedSystemProperty;
import com.github.invictum.utils.properties.PropertiesUtil;
import com.github.invictum.utils.rest.schema.SchemaProvider;
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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(PowerMockRunner.class)
@PrepareForTest({PropertiesUtil.class, ResourceProvider.class})
public class SchemaProviderTest {

    @Rule
    private static TemporaryFolder folder = new TemporaryFolder();
    private static File resourceFile = null;

    @BeforeClass
    public static void beforeClass() throws IOException {
        PowerMockito.mockStatic(PropertiesUtil.class);
        PowerMockito.when(PropertiesUtil.getProperty(EnhancedSystemProperty.SchemasDirectory)).thenReturn("root");
        folder.create();
        resourceFile = folder.newFile();
        PowerMockito.mockStatic(ResourceProvider.class);
        PowerMockito.when(ResourceProvider.getFile("root", "schemaKey")).thenReturn(resourceFile);
    }

    @Test
    public void getTest() {
        File actualFile = SchemaProvider.get("schemaKey");
        assertThat("Schema file is wrong.", actualFile, equalTo(resourceFile));
    }
}
