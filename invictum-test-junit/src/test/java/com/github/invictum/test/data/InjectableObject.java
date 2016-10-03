package com.github.invictum.test.data;

import com.github.invictum.data.injector.TestData;

import java.util.List;

public class InjectableObject {

    @TestData("test-data.yaml")
    private TestDto testDto;

    @TestData(value = "test-velocity-data.yaml", velocity = true)
    private TestDto velocityDto;

    @TestData("test-data-list.yaml")
    private List<TestDto> listTestDto;

    public TestDto getData() {
        return testDto;
    }

    public List<TestDto> getListData() {
        return listTestDto;
    }

    public TestDto getVelocityData() {
        return velocityDto;
    }
}
