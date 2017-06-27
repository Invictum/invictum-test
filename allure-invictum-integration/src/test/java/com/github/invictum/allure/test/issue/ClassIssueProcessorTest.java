package com.github.invictum.allure.test.issue;

import com.github.invictum.allure.issue.ClassIssueProcessor;
import com.github.invictum.allure.issue.IssueProcessor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(JUnit4.class)
public class ClassIssueProcessorTest {

    private IssueProcessor processor = new ClassIssueProcessor(TestClass.class);
    private Set<String> expected;

    @Before
    public void beforeTest() {
        expected = new HashSet<>();
    }

    @Test
    public void collectTestIssuesTest() {
        expected.add("class_1");
        expected.add("class_2");
        expected.add("class_3");
        assertThat("Class issues are wrong.", processor.collectTestIssues(), equalTo(expected));
    }

    @Test
    public void collectTestMethodIssuesTest() {
        expected.add("method_1");
        expected.add("method_2");
        expected.add("method_3");
        assertThat("Method issues are wrong.", processor.collectMethodIssues("method"), equalTo(expected));
    }
}
