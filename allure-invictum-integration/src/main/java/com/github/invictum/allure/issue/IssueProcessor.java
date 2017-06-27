package com.github.invictum.allure.issue;

import java.util.Set;

public interface IssueProcessor {

    Set<String> collectTestIssues();

    Set<String> collectMethodIssues(String methodName);
}
