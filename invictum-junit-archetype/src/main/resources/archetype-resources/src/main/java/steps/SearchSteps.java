#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.steps;

import com.github.invictum.steps.AbstractSteps;
import net.thucydides.core.annotations.Step;
import ${package}.dto.SearchData;
import ${package}.pages.ArticlePage;
import ${package}.pages.MainPage;
import ${package}.panels.SearchPanel;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class SearchSteps extends AbstractSteps {

    MainPage mainPage;
    ArticlePage articlePage;

    @Step
    public void fillSearchForm(String searchCriteria) {
        mainPage.getPanel(SearchPanel.class).fillSearchCriteria(searchCriteria);
    }

    @Step
    public void performSearch() {
        mainPage.getPanel(SearchPanel.class).clickSearchButton();
    }

    @Step
    public SearchData getSearchResults() {
        SearchData actual = new SearchData();
        actual.setResultTitle(articlePage.getArticleTitle());
        return actual;
    }

    @Step
    public void verifySearchResults(SearchData expected) {
        assertThat("Search results is wrong.", getSearchResults(), equalTo(expected));
    }
}
