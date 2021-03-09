import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.*;

import static io.restassured.RestAssured.given;

public class BaseTest {

    @Parameters("baseURL")
    @BeforeClass
    public void setup(@Optional("https://api-coffee-testing.herokuapp.com") String baseURL){
        RestAssured.baseURI = baseURL;

    }//setup
}
