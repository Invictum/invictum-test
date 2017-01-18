package com.github.invictum;

import com.github.invictum.events.StepPendingEvent;
import com.github.invictum.events.StepSkippedEvent;
import net.thucydides.core.model.DataTable;
import net.thucydides.core.model.Story;
import net.thucydides.core.model.TestOutcome;
import net.thucydides.core.steps.ExecutedStepDescription;
import net.thucydides.core.steps.StepFailure;
import net.thucydides.core.steps.StepListener;
import org.apache.commons.lang3.StringUtils;
import ru.yandex.qatools.allure.Allure;
import ru.yandex.qatools.allure.events.*;
import ru.yandex.qatools.allure.utils.AnnotationManager;

import java.util.Map;
import java.util.UUID;

public class AllureStepListener implements StepListener {

    private Allure allure = Allure.LIFECYCLE;
    private String suitUid = StringUtils.EMPTY;

    private String getSuitUid() {
        suitUid = UUID.randomUUID().toString();
        return suitUid;
    }

    private void makeAttachmentIfPossible(String title) {
        byte[] content = ScreenshotUtil.takeScreenshotContent();
        if (content != null) {
            allure.fire(new MakeAttachmentEvent(content, title, "image/png"));
        }
    }

    @Override
    public void testSuiteStarted(Class<?> storyClass) {
        allure.fire(new TestSuiteStartedEvent(getSuitUid(), storyClass.getSimpleName()));
    }

    @Override
    public void testSuiteStarted(Story story) {
        allure.fire(new TestSuiteStartedEvent(getSuitUid(), story.getStoryName()));
    }

    @Override
    public void testSuiteFinished() {
        allure.fire(new TestSuiteFinishedEvent(suitUid));
    }

    @Override
    public void testStarted(String description) {
        TestCaseStartedEvent testCaseStartedEvent = new TestCaseStartedEvent(suitUid, description);
        allure.fire(AnnotationManager.withExecutorInfo(testCaseStartedEvent));
    }

    @Override
    public void testStarted(String description, String id) {
        TestCaseStartedEvent testCaseStartedEvent = new TestCaseStartedEvent(suitUid,
                String.format("%s - %s", id, description));
        allure.fire(AnnotationManager.withExecutorInfo(testCaseStartedEvent));
    }

    @Override
    public void testFinished(TestOutcome result) {
        if (result.getFailingStep().isPresent()) {
            TestCaseFailureEvent event = new TestCaseFailureEvent();
            event.withThrowable(result.getFailingStep().get().getException().toException());
            allure.fire(event);
        }
        allure.fire(new TestCaseFinishedEvent());
    }

    @Override
    public void testRetried() {
        //TODO: Investigate how to integrate it.
    }

    @Override
    public void stepStarted(ExecutedStepDescription description) {
        allure.fire(new StepStartedEvent(description.getTitle()));
        makeAttachmentIfPossible("Step started");
    }

    @Override
    public void skippedStepStarted(ExecutedStepDescription description) {
        allure.fire(new StepStartedEvent(description.getTitle()));
        allure.fire(new StepSkippedEvent());
        allure.fire(new StepFinishedEvent());
    }

    @Override
    public void stepFailed(StepFailure failure) {
        allure.fire(new StepFailureEvent().withThrowable(failure.getException()));
        makeAttachmentIfPossible(String.format("Step failed: %s", failure.getMessage()));
        allure.fire(new StepFinishedEvent());
    }

    @Override
    public void lastStepFailed(StepFailure failure) {
        //Isn't used in allure.
    }

    @Override
    public void stepIgnored() {
        //TODO: Investigate how to integrate it.
    }

    @Override
    public void stepPending() {
        allure.fire(new StepStartedEvent(StringUtils.EMPTY));
        allure.fire(new StepPendingEvent());
        allure.fire(new StepFinishedEvent());
    }

    @Override
    public void stepPending(String message) {
        allure.fire(new StepStartedEvent(message));
        allure.fire(new StepPendingEvent());
        allure.fire(new StepFinishedEvent());
    }

    @Override
    public void stepFinished() {
        makeAttachmentIfPossible("Step finished");
        allure.fire(new StepFinishedEvent());
    }

    @Override
    public void testFailed(TestOutcome testOutcome, Throwable cause) {
        allure.fire(new TestCaseFailureEvent().withThrowable(cause));
    }

    @Override
    public void testIgnored() {
        allure.fire(new TestCasePendingEvent());
        allure.fire(new TestCaseFinishedEvent());
    }

    @Override
    public void testSkipped() {
        //TODO: Investigate how to integrate it.
    }

    @Override
    public void testPending() {
        allure.fire(new TestCasePendingEvent());
    }

    @Override
    public void testIsManual() {
        //Not supported in Allure.
    }

    @Override
    public void notifyScreenChange() {
        //Isn't used in allure.
    }

    @Override
    public void useExamplesFrom(DataTable table) {
        //Isn't used in allure.
    }

    @Override
    public void addNewExamplesFrom(DataTable table) {
        //Isn't used in allure.
    }

    @Override
    public void exampleStarted(Map<String, String> data) {
        //Isn't used in allure.
    }

    @Override
    public void exampleFinished() {
        //Isn't used in allure.
    }

    @Override
    public void assumptionViolated(String message) {
        //Isn't used in allure.
    }

    @Override
    public void testRunFinished() {
        //Isn't used in allure.
    }
}
