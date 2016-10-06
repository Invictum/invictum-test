package com.github.invictum.test;

import com.github.invictum.ImpexFormatter;
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
public class ImpexFormatterTest {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test(expected = IllegalStateException.class)
    public void formatImpexNoFile() {
        ImpexFormatter.formatImpex(new File(""), "param1");
    }

    @Test
    public void formatImpexNoParams() throws IOException {
        File impex = folder.newFile("impexFile");
        String content = "INSERT_UPDATE Table;id[unique=true];name\nname";
        FileUtils.writeStringToFile(impex, content, "UTF-8");
        assertThat("Formatted string is wrong.", ImpexFormatter.formatImpex(impex), equalTo(content));
    }

    @Test
    public void formatImpexWithParams() throws IOException {
        File impex = folder.newFile("impexFile");
        String content = "INSERT_UPDATE Table;id[unique=true];name;set;temp\n{2};{1};{2}";
        FileUtils.writeStringToFile(impex, content, "UTF-8");
        String expected = "INSERT_UPDATE Table;id[unique=true];name;set;temp\nparam2;param1;param2";
        String actual = ImpexFormatter.formatImpex(impex, "param1", "param2");
        assertThat("Formatted string is wrong.", actual, equalTo(expected));
    }
}
