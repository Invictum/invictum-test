#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.panels;

import com.github.invictum.panels.AbstractPanel;

public class SearchPanel extends AbstractPanel {

    public void fillSearchCriteria(String searchCriteria) {
        locate("search.input").type(searchCriteria);
    }

    public void clickSearchButton() {
        locate("search.button").click();
    }
}
