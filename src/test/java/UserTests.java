import model.User;
import org.testng.annotations.Test;
import specifications.ResponseSpecs;

import static helper.DataHelper.generateRandomEmail;
import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.StringContains.containsString;

public class UserTests extends BaseTest{

    private static String resourcePath = "/v1/user";

    @Test
    public void Test_Create_User_Already_Exist(){

        User user = new User("pablotest","pablo@test.com","test1234");
        given()
                .body(user)
        .when()
                .post(resourcePath + "/register")
        .then()
                .body("message", equalTo("User already exists"))
        .and()
                .statusCode(406)
                .spec(ResponseSpecs.defaultSpec());
    }//Test_Create_User_Already_Exist

    @Test
    public void Test_Create_User_Successful(){

        User user = new User("Test", generateRandomEmail(),"Test123");
        given()
                .body(user)
        .when()
                .post(resourcePath + "/register")
        .then()
                .body("message", equalTo("Successfully registered"))
        .and()
                .statusCode(200)
                .spec(ResponseSpecs.defaultSpec());

    }//Test_Create_User_Successful

    @Test
    public void Test_Login_User_Successful(){

        User user = new User("Test", generateRandomEmail(),"Test123");
        given()
                .body(user)
                .when()
                .post(resourcePath + "/register")
                .then()
                .body("message", equalTo("Successfully registered"))
                .and()
                .statusCode(200);

        given()
                .body(user)
                .when()
                .post(resourcePath + "/login")
                .then()
                .body("message", equalTo("User signed in"))
                .and()
                .statusCode(200);
    }//Test_Login_User_Successful
}
