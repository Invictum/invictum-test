package com.github.invictum.allure.test.issue;

import net.thucydides.core.annotations.Issue;
import net.thucydides.core.annotations.Issues;

@Issue("class_1")
@Issues({"class_2", "class_3"})
public class TestClass {

    @Issue("method_1")
    @Issues({"method_2", "method_3"})
    public void method() {
        //do nothing
    }
}
