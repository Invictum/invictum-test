package com.github.invictum.utils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;

import java.io.File;
import java.util.Collection;

public class ResourceProvider {

    @SuppressWarnings("all")
    private static File getRoot(String rootPath) {
        return new File(Thread.currentThread().getContextClassLoader().getResource(rootPath).getFile());
    }

    public static File getFile(final String root, final String fileName) {
        File rootDirectory = getRoot(root);
        Collection<File> fileList = FileUtils
                .listFiles(rootDirectory, FileFilterUtils.suffixFileFilter(fileName), FileFilterUtils.trueFileFilter());
        if (fileList.size() == 1) {
            return fileList.iterator().next();
        } else {
            String message = String.format("Unable to locate one '%s' file in '%s' directory", fileName, rootDirectory);
            throw new IllegalStateException(message);
        }
    }
}
