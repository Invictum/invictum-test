package com.github.invictum.dto.attribute.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ServiceLoader;

public class ConverterUtil {

    private final static Logger LOG = LoggerFactory.getLogger(ConverterUtil.class);
    private static ServiceLoader<Converter> converters = ServiceLoader.load(Converter.class);

    private ConverterUtil() {
        //disable constructor.
    }

    public static String convert(Object candidate) {
        for (Converter converter : converters) {
            if (converter.isTypeCompatible(candidate)) {
                return converter.convert(candidate);
            }
        }
        LOG.warn("Unable to convert {}. null value will be returned.", candidate.getClass().getSimpleName());
        LOG.warn(
                "You may define custom converter via implementing Converter interface and registering it as a service.");
        return null;
    }
}
