#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import com.github.invictum.steps.PageNavigationSteps;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import net.thucydides.core.annotations.WithTag;
import ${package}.core.AbstractUiTest;
import ${package}.steps.SearchSteps;
import ${package}.urls.core.Url;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(EnhancedSerenityRunner.class)
public class WikiSearchTest extends AbstractUiTest {

    @Steps
    PageNavigationSteps pageNavigationSteps;

    @Steps
    SearchSteps searchSteps;

    @Test
    @Title("Basic search on Wiki")
    @WithTag("feature2, feature1")
    public void basicSearch() {
        pageNavigationSteps.openPage("Main");
        searchSteps.fillSearchForm("xml");
        searchSteps.performSearch();
        assertThat("Search result title is wrong.", searchSteps.getArticleTitle(), equalTo("XML"));
    }

    @Test
    @Url("italian:default")
    @Title("Basic search on Italian Wiki")
    @WithTag("feature1")
    public void basicItalianSearch() {
        pageNavigationSteps.openPage("Main");
        searchSteps.fillSearchForm("json");
        searchSteps.performSearch();
        assertThat("Search result title is wrong.", searchSteps.getArticleTitle(),
                equalTo("JavaScript Object Notation"));
    }
}
