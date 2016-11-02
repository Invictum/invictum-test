package com.github.invictum.velocity;

import org.apache.commons.lang3.RandomUtils;
import org.apache.velocity.tools.config.DefaultKey;
import org.apache.velocity.tools.generic.FormatConfig;

@DefaultKey("choose")
public class ChooseTool extends FormatConfig {

    public <T> T one(T[] list) {
        int index = RandomUtils.nextInt(0, list.length);
        return list[index];
    }
}
