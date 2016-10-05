#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import com.github.invictum.utils.rest.Rest;
import ${package}.core.AbstractApiTest;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.github.invictum.utils.rest.schema.EnhancedJsonSchemaValidator.matchesJsonSchema;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(EnhancedSerenityRunner.class)
public class Md5HashApiTest extends AbstractApiTest {

    @Test
    public void validateMd5HashResponse() {
        Rest.request("md5", "default").queryParameter("text", "example").get();
        String hashValue = Rest.response().statusCode(200).extract().path("md5");
        assertThat("MD5 hash calculated wrong.", hashValue, equalTo("1a79a4d60de6718e8e5b326e338ae533"));
    }

    @Test
    public void validateMd5ResponseSignature() {
        Rest.request("md5", "default").queryParameter("text", "example").get();
        Rest.response().body(matchesJsonSchema("md5response.json"));
    }
}
