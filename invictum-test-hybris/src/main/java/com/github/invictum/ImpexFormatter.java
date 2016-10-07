package com.github.invictum;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class ImpexFormatter {

    private final static Logger LOG = LoggerFactory.getLogger(ImpexFormatter.class);

    private ImpexFormatter() {
    }

    public static String formatImpex(File impex, String... params) {
        try {
            String fileContent = FileUtils.readFileToString(impex, "UTF-8");
            LOG.debug("Loaded impex template: {}", fileContent);
            int index = 1;
            for (String param : params) {
                fileContent = fileContent.replace("{" + index + "}", param);
                index++;
            }
            LOG.debug("Impex string to import: {}", fileContent);
            return fileContent;
        } catch (IOException e) {
            throw new IllegalStateException("Impex file not found");
        }
    }
}
