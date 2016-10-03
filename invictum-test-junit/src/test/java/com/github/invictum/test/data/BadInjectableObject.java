package com.github.invictum.test.data;

import com.github.invictum.data.injector.TestData;

public class BadInjectableObject {

    @TestData("invalid-test-data.yaml")
    private TestDto testDto;
}
