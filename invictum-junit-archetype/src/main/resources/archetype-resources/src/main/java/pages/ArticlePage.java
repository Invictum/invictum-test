#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.pages;

import com.github.invictum.pages.AbstractPage;

public class ArticlePage extends AbstractPage {
    public String getArticleTitle() {
        return locate("title").getText();
    }
}
