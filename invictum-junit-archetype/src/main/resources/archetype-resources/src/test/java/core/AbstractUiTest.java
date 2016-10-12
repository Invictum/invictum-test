#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.core;

import net.thucydides.core.annotations.Managed;
import org.openqa.selenium.WebDriver;

public class AbstractUiTest {

    @Managed
    private WebDriver driver;
}
