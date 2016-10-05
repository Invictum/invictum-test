#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.dto;

import com.github.invictum.dto.AbstractDto;
import com.github.invictum.dto.annotation.DtoAttribute;

public class SearchData extends AbstractDto {

    private String searchText;

    @DtoAttribute
    private String resultTitle;

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public String getResultTitle() {
        return resultTitle;
    }

    public void setResultTitle(String resultTitle) {
        this.resultTitle = resultTitle;
    }
}
