package com.github.invictum.utils;

import net.serenitybdd.core.Serenity;
import org.assertj.core.api.SoftAssertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SoftAssertsUtil {

    private final static String ASSERT_KEY = "softAssert";
    private final static Logger LOG = LoggerFactory.getLogger(SoftAssertsUtil.class);

    public static SoftAssertions instance() {
        if (!Serenity.getCurrentSession().containsKey(ASSERT_KEY)) {
            Serenity.getCurrentSession().put(ASSERT_KEY, new SoftAssertions());
            LOG.debug("New Soft assert instance was created");
        }
        return (SoftAssertions) Serenity.getCurrentSession().get(ASSERT_KEY);
    }

    public static void assertAllSoftly() {
        if (Serenity.getCurrentSession().containsKey(ASSERT_KEY)) {
            ((SoftAssertions) Serenity.getCurrentSession().get(ASSERT_KEY)).assertAll();
        } else {
            LOG.warn("Attempt to assert all softly. Did assertAllSoftly() step method was invoked?");
        }
    }
}
