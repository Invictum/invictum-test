package com.github.invictum.velocity;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.tools.generic.DateTool;
import org.apache.velocity.tools.generic.MathTool;

import java.io.StringWriter;

public class VelocityProcessor {

    private static VelocityContext context = new VelocityContext();

    static {
        context.put("math", new MathTool());
        context.put("date", new DateTool());
        context.put("choose", new ChooseTool());
        context.put("text", RandomStringUtils.class);
    }

    public static String processContent(String content) {
        Velocity.init();
        StringWriter writer = new StringWriter();
        Velocity.evaluate(context, writer, "longTag", content);
        return writer.toString();
    }
}
