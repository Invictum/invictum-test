package com.github.invictum.utils;

import com.github.invictum.velocity.VelocityProcessor;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;

public class ResourceProvider {

    private final static Logger LOG = LoggerFactory.getLogger(ResourceProvider.class);

    @SuppressWarnings("all")
    private static File getRoot(String rootPath) {
        if (isResourcePresent(rootPath)) {
            return new File(Thread.currentThread().getContextClassLoader().getResource(rootPath).getFile());
        }
        throw new IllegalStateException(String.format("Failed to init root at %s", rootPath));
    }

    private static boolean isResourcePresent(String resourcePath) {
        URL resource = Thread.currentThread().getContextClassLoader().getResource(resourcePath);
        return resource != null;
    }

    public static File getFile(final String root, final String fileName) {
        File rootDirectory = getRoot(root);
        Collection<File> fileList = FileUtils
                .listFiles(rootDirectory, FileFilterUtils.nameFileFilter(fileName), FileFilterUtils.trueFileFilter());
        if (fileList.isEmpty()) {
            LOG.error("Unable to find '{}' in '{}' directory", fileName, rootDirectory);
        }
        return fileList.iterator().next();
    }

    public static boolean isPackagePresent(String packageName) {
        String path = StringUtils.replace(packageName, ".", "/");
        if (!isResourcePresent(path)) {
            LOG.error("Unable to find {} package", packageName);
            return false;
        }
        LOG.debug("Package with path {} was found", packageName);
        return true;
    }

    public static String getFileContent(String resourcePath, boolean velocity) {
        LOG.debug("Try to load '{}' resource", resourcePath);
        URL file = Thread.currentThread().getContextClassLoader().getResource(resourcePath);
        if (file == null) {
            throw new IllegalStateException(String.format("Undiscovered resource for %s", resourcePath));
        }
        File resourceFile = new File(file.getFile());
        try {
            String content = FileUtils.readFileToString(resourceFile);
            if (velocity) {
                LOG.debug("Velocity is enabled, processing loaded YML file.");
                return VelocityProcessor.processContent(content);
            }
            return content;
        } catch (IOException e) {
            throw new IllegalStateException(String.format("Failed to read %s file", resourceFile.getPath()));
        }
    }
}
