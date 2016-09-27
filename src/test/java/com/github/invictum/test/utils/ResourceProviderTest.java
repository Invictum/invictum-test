package com.github.invictum.test.utils;

import com.github.invictum.utils.ResourceProvider;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.io.IOException;
import java.util.NoSuchElementException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(JUnit4.class)
public class ResourceProviderTest {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder(new File(this.getClass().getResource("/").getFile()));

    @Test
    public void getFileTest() throws IOException {
        File expected = folder.newFile("file");
        File actual = ResourceProvider.getFile("", "file");
        assertThat("Newest selected file is wrong.", actual, equalTo(expected));
    }

    @Test
    public void getFileSuffixTest() throws IOException {
        folder.newFile("oneFile");
        File expected = folder.newFile("File");
        File actual = ResourceProvider.getFile("", "File");
        assertThat("Newest file selected wrong.", actual, equalTo(expected));
    }

    @Test(expected = NoSuchElementException.class)
    public void fileNonExistTest() throws IOException {
        ResourceProvider.getFile("", "file");
    }

    @Test(expected = NoSuchElementException.class)
    public void fileNonExistSuffixTest() throws IOException {
        folder.newFile("firstFile");
        folder.newFile("secondFile");
        ResourceProvider.getFile("", "File");
    }

    @Test
    public void isPackagePresentTest() {
        assertThat("Package presence isn't detected.",
                ResourceProvider.isPackagePresent("com.github.invictum.test.utils"), is(true));
    }

    @Test
    public void isPackageNotPresentTest() {
        assertThat("Package should be absent.", ResourceProvider.isPackagePresent("com.example"), is(false));
    }
}
