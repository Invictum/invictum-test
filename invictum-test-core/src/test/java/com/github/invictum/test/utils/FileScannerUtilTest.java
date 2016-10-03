package com.github.invictum.test.utils;

import com.github.invictum.utils.FileScannerUtil;
import org.apache.commons.io.FileUtils;
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
public class FileScannerUtilTest {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void getNewestFileFromDirectoryTest() throws IOException {
        folder.newFile("first.txt").setLastModified(1000);
        File expected = folder.newFile("second.txt");
        expected.setLastModified(2000);
        folder.newFile("first.png");
        File actual = FileScannerUtil.getNewestFileFromDirectory(folder.getRoot().getAbsolutePath(), "txt");
        assertThat("Newest file selected wrong.", actual, equalTo(expected));
    }

    @Test
    public void replaceFileTest() throws IOException {
        File source = folder.newFile("src");
        FileUtils.writeStringToFile(source, "src");
        File destination = folder.newFile("dst");
        FileScannerUtil.replaceFile(source, destination);
        /** Content of source and destination should be the same after replacing. */
        assertThat("File isn't replaced.", FileUtils.readFileToString(source),
                equalTo(FileUtils.readFileToString(destination)));
    }
}
