package com.github.invictum.allure.issue;

import com.google.common.base.Optional;
import net.thucydides.core.annotations.TestAnnotations;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ClassIssueProcessor implements IssueProcessor {

    private Class<?> testClass;
    private TestAnnotations testAnnotations;

    public ClassIssueProcessor(Class<?> testClass) {
        this.testClass = testClass;
        this.testAnnotations = TestAnnotations.forClass(testClass);
    }

    public Set<String> collectTestIssues() {
        Set<String> issues = new HashSet<>();
        String[] list = testAnnotations.getAnnotatedIssuesForTestCase(testClass);
        if (list != null) {
            issues.addAll(Arrays.asList(list));
        }
        String issue = testAnnotations.getAnnotatedIssueForTestCase(testClass);
        if (issue != null) {
            issues.add(issue);
        }
        return issues;
    }

    public Set<String> collectMethodIssues(String methodName) {
        Set<String> issues = new HashSet<>();
        String[] list = testAnnotations.getAnnotatedIssuesForMethod(methodName);
        if (list != null) {
            issues.addAll(Arrays.asList(list));
        }
        Optional<String> issue = testAnnotations.getAnnotatedIssueForMethod(methodName);
        if (issue.isPresent()) {
            issues.add(issue.get());
        }
        return issues;
    }
}
