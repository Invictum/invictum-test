package com.github.invictum.test.utils;

import com.github.invictum.utils.ResourceProvider;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(JUnit4.class)
public class ResourceProviderTest {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder(new File(this.getClass().getResource("/").getFile()));

    @Test
    public void getFileTest() throws IOException {
        File expected = folder.newFile("file");
        File actual = ResourceProvider.getFile("", "file");
        assertThat("Newest file selected wrong.", actual, equalTo(expected));
    }

    @Test(expected = IllegalStateException.class)
    public void fileNonExistTest() throws IOException {
        ResourceProvider.getFile("", "file");
    }

    @Test(expected = IllegalStateException.class)
    public void fileDuplicateTest() throws IOException {
        folder.newFile("firstFile");
        folder.newFile("secondFile");
        ResourceProvider.getFile("", "File");
    }
}
