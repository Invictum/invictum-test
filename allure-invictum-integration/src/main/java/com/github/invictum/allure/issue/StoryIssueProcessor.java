package com.github.invictum.allure.issue;

import net.thucydides.core.model.Story;

import java.util.HashSet;
import java.util.Set;

public class StoryIssueProcessor implements IssueProcessor {

    private Story story;

    public StoryIssueProcessor(Story story) {
        this.story = story;
    }

    public Set<String> collectTestIssues() {
        //TODO: Investigate how to extract BDD issue annotations
        return new HashSet<>();
    }

    public Set<String> collectMethodIssues(String methodName) {
        //TODO: Investigate how to extract BDD issue annotations
        return new HashSet<>();
    }
}
