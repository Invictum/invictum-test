#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import com.google.inject.Injector;
import com.google.inject.Module;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.batches.BatchManager;
import net.thucydides.core.batches.BatchManagerProvider;
import net.thucydides.core.guice.Injectors;
import net.thucydides.core.webdriver.Configuration;
import net.thucydides.core.webdriver.WebDriverFactory;
import net.thucydides.core.webdriver.WebdriverManager;
import ${package}.urls.core.Url;
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
        super(klass, webDriverFactory, Injectors.getInjector().getInstance(Configuration.class));
    }

    public EnhancedSerenityRunner(final Class<?> klass, final WebDriverFactory webDriverFactory,
            final Configuration configuration) throws InitializationError {
        super(klass, webDriverFactory, configuration, new BatchManagerProvider(configuration).get());
    }

    public EnhancedSerenityRunner(final Class<?> klass, final WebDriverFactory webDriverFactory,
            final Configuration configuration, final BatchManager batchManager) throws InitializationError {
        super(klass, webDriverFactory, configuration, batchManager);
    }

    public EnhancedSerenityRunner(final Class<?> klass, final BatchManager batchManager) throws InitializationError {
        super(klass, batchManager);
    }

    public EnhancedSerenityRunner(final Class<?> klass, final WebdriverManager webDriverManager,
            final Configuration configuration, final BatchManager batchManager) throws InitializationError {
        super(klass, webDriverManager, configuration, batchManager);
    }

    @Override
    protected Statement methodInvoker(FrameworkMethod method, Object test) {
        overrideUrls(method);
        return super.methodInvoker(method, test);
    }

    private void overrideUrls(FrameworkMethod method) {
        Url urlAnnotation = method.getAnnotation(Url.class);
        if (urlAnnotation != null) {
            Serenity.getCurrentSession().addMetaData("url", urlAnnotation.value());
        }
    }
}
