#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.steps;

import com.github.invictum.steps.AbstractSteps;
import net.thucydides.core.annotations.Step;
import ${package}.pages.ArticlePage;
import ${package}.pages.MainPage;
import ${package}.panels.SearchPanel;

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
    public String getArticleTitle() {
        return articlePage.getArticleTitle();
    }
}
