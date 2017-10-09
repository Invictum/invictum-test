#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import com.github.invictum.EnhancedSerenityRunner;
import io.restassured.specification.RequestSpecification;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.WithTagValuesOf;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.github.invictum.utils.rest.schema.EnhancedJsonSchemaValidator.matchesJsonSchema;

@RunWith(EnhancedSerenityRunner.class)
@WithTagValuesOf("area:api")
public class DateApiTest {

    private static RequestSpecification requestSpecification = null;

    @BeforeClass
    public static void makeDateRequest() {
        requestSpecification = SerenityRest.rest().baseUri("http://date.jsontest.com");
    }

    @Test
    public void validateDateResponseCode() {
        requestSpecification.get();
        SerenityRest.then().statusCode(200);
    }

    @Test
    public void validateDateResponseSignature() {
        requestSpecification.get();
        SerenityRest.then().body(matchesJsonSchema("example.json"));
    }
}
