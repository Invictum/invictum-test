package org.unified.test.utils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.commons.io.filefilter.FileFilterUtils;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

public class FileScannerUtil {

    public static File getNewestFileFromDirectory(final String directory, final String extension) {
        File dir = new File(directory);
        Collection<File> files = FileUtils
                .listFiles(dir, FileFilterUtils.suffixFileFilter(extension), FileFilterUtils.trueFileFilter());
        return Collections.max(files, LastModifiedFileComparator.LASTMODIFIED_COMPARATOR);
    }

    public static void replaceFile(final File src, final File destination) {
        String fileName = destination.getPath();
        FileUtils.deleteQuietly(destination);
        try {
            FileUtils.copyFile(src, new File(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
