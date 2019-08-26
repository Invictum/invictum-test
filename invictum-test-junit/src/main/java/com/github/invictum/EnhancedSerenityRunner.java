package com.github.invictum;

import com.github.invictum.data.injector.DataInjector;
import com.github.invictum.fixture.Fixture;
import com.github.invictum.fixture.Fixtures;
import com.github.invictum.fixtures.FixtureProcessor;
import com.github.invictum.url.Url;
import com.google.inject.Injector;
import com.google.inject.Module;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.batches.BatchManager;
import net.thucydides.core.batches.BatchManagerProvider;
import net.thucydides.core.guice.Injectors;
import net.thucydides.core.webdriver.DriverConfiguration;
import net.thucydides.core.webdriver.WebDriverFactory;
import net.thucydides.core.webdriver.WebdriverManager;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

public class EnhancedSerenityRunner extends SerenityRunner {

    public EnhancedSerenityRunner(final Class<?> klass) throws InitializationError {
        super(klass);
    }

    public EnhancedSerenityRunner(Class<?> klass, Module module) throws InitializationError {
        super(klass, module);
    }

    public EnhancedSerenityRunner(final Class<?> klass, final Injector injector) throws InitializationError {
        super(klass, injector);
    }

    public EnhancedSerenityRunner(final Class<?> klass, final WebDriverFactory webDriverFactory)
            throws InitializationError {
        super(klass, webDriverFactory, Injectors.getInjector().getInstance(DriverConfiguration.class));
    }

    public EnhancedSerenityRunner(final Class<?> klass, final WebDriverFactory webDriverFactory,
            final DriverConfiguration configuration) throws InitializationError {
        super(klass, webDriverFactory, configuration, new BatchManagerProvider(configuration).get());
    }

    public EnhancedSerenityRunner(final Class<?> klass, final WebDriverFactory webDriverFactory,
            final DriverConfiguration configuration, final BatchManager batchManager) throws InitializationError {
        super(klass, webDriverFactory, configuration, batchManager);
    }

    public EnhancedSerenityRunner(final Class<?> klass, final BatchManager batchManager) throws InitializationError {
        super(klass, batchManager);
    }

    public EnhancedSerenityRunner(final Class<?> klass, final WebdriverManager webDriverManager,
            final DriverConfiguration configuration, final BatchManager batchManager) throws InitializationError {
        super(klass, webDriverManager, configuration, batchManager);
    }

    @Override
    protected Statement methodInvoker(FrameworkMethod method, Object test) {
        DataInjector.injectInto(test);
        overrideUrls(method);
        applyFixtures(method);
        Statement statement = super.methodInvoker(method, test);
        return new InvictumStatement(statement);
    }

    private void applyFixtures(FrameworkMethod method) {
        Fixtures fixtures = method.getAnnotation(Fixtures.class);
        if (fixtures != null) {
            for (Fixture fixture : fixtures.value()) {
                FixtureProcessor.put(fixture.value(), fixture.parameters());
            }
        }
        Fixture fixture = method.getAnnotation(Fixture.class);
        if (fixture != null) {
            FixtureProcessor.put(fixture.value(), fixture.parameters());
        }
    }

    private void overrideUrls(FrameworkMethod method) {
        Url urlAnnotation = method.getAnnotation(Url.class);
        if (urlAnnotation != null) {
            Serenity.getCurrentSession().addMetaData("url", urlAnnotation.value());
        }
    }
}
