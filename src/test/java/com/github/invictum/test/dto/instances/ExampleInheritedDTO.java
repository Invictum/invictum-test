package com.github.invictum.test.dto.instances;

import com.github.invictum.dto.annotation.DtoAttribute;

public class ExampleInheritedDTO extends ExampleDto {

    @DtoAttribute
    private String text1;

    @DtoAttribute
    private String text2;

    public String getText1() {
        return text1;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }

    public String getText2() {
        return text2;
    }

    public void setText2(String text2) {
        this.text2 = text2;
    }
}
