package com.github.invictum.aspect;

import net.thucydides.core.annotations.Step;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import ru.yandex.qatools.allure.Allure;
import ru.yandex.qatools.allure.events.StepFailureEvent;
import ru.yandex.qatools.allure.events.StepFinishedEvent;
import ru.yandex.qatools.allure.events.StepStartedEvent;

import static ru.yandex.qatools.allure.aspects.AllureAspectUtils.getName;
import static ru.yandex.qatools.allure.aspects.AllureAspectUtils.getTitle;

@Aspect
public class SerenityStepAspect {

    private static Allure ALLURE = Allure.LIFECYCLE;

    @Pointcut("@annotation(net.thucydides.core.annotations.Step)")
    public void withStepAnnotation() {
    }

    @Pointcut("execution(* *(..))")
    public void anyMethod() {
    }

    @Before("anyMethod() && withStepAnnotation()")
    public void stepStart(JoinPoint joinPoint) {
        String stepTitle = createTitle(joinPoint);
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        StepStartedEvent startedEvent = new StepStartedEvent(getName(methodSignature.getName(), joinPoint.getArgs()));
        if (!stepTitle.isEmpty()) {
            startedEvent.setTitle(stepTitle);
        }
        ALLURE.fire(startedEvent);
    }

    @AfterThrowing(pointcut = "anyMethod() && withStepAnnotation()", throwing = "e")
    public void stepFailed(JoinPoint joinPoint, Throwable e) {
        ALLURE.fire(new StepFailureEvent().withThrowable(e));
        ALLURE.fire(new StepFinishedEvent());
    }

    @AfterReturning(pointcut = "anyMethod() && withStepAnnotation()", returning = "result")
    public void stepStop(JoinPoint joinPoint, Object result) {
        ALLURE.fire(new StepFinishedEvent());
    }

    public String createTitle(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Step step = methodSignature.getMethod().getAnnotation(Step.class);
        return step == null ? "" : getTitle(step.value(), methodSignature.getName(), joinPoint.getThis(),
                joinPoint.getArgs());
    }
}
