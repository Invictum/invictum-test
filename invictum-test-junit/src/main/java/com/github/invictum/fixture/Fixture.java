package com.github.invictum.fixture;

import com.github.invictum.fixtures.AbstractFixture;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Fixture {
    Class<? extends AbstractFixture> value();
    String[] parameters() default "";
}
