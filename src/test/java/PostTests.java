import helper.DataHelper;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import model.Post;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Test;
import specifications.RequestSpecs;
import specifications.ResponseSpecs;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class PostTests extends BaseTest{

    private static String resourcePath= "/v1/post";
    private static Integer createdPost = 0;

    @BeforeGroups("create_post")
    public void createPost(){

        Post testPost = new Post(DataHelper.generateRandomTitle(), DataHelper.generateRandomContent());

        Response response = given()
                .spec(RequestSpecs.generateToken())
                .body(testPost)
                .post(resourcePath);

        JsonPath jsonPathEvaluator = response.jsonPath();
        createdPost = jsonPathEvaluator.get("id");
    }

    @Test
    public void Create_Post_Success(){

        Post testPost = new Post(DataHelper.generateRandomTitle(), DataHelper.generateRandomContent());

        given()
                .spec(RequestSpecs.generateToken())
                .body(testPost)
                .post(resourcePath)
        .then()
                .body("message", equalTo("Post created"))
                .statusCode(200)
                .spec(ResponseSpecs.defaultSpec());
    }//A_Create_Post_Success

    @Test
    public void Create_Post_Unsuccessful(){

        Post testPost = new Post(DataHelper.generateRandomTitle(), "");

        given()
                .spec(RequestSpecs.generateToken())
                .body(testPost)
                .post(resourcePath)
        .then()
                .body("message", equalTo("Invalid form"))
                .statusCode(406)
                .spec(ResponseSpecs.defaultSpec());
    }//B_Create_Invalid_Post

    @Test(groups = "create_post")
    public void Get_One_Post() {
        given()
                .spec(RequestSpecs.generateToken())
                .get(resourcePath + "/" + createdPost.toString())
        .then()
                .statusCode(200)
                .spec(ResponseSpecs.defaultSpec());
    }//Get_One_Post

    @Test(groups = "create_post")
    public void Get_Invalid_Post() {
        given()
                .spec(RequestSpecs.generateToken())
                .get(resourcePath + "/" + createdPost + createdPost.toString())
        .then()
                .body("Message", equalTo("Post not found"))
                .statusCode(404)
                .spec(ResponseSpecs.defaultSpec());
    }//Get_Invalid_Post

    @Test(groups = "create_post")
    public void Get_All_Posts() {
        given()
                .spec(RequestSpecs.generateToken())
                .get(resourcePath + "s")
        .then()
                .statusCode(200)
                .spec(ResponseSpecs.defaultSpec());
    }//Get_All_Posts

    @Test(groups = "create_post")
    public void Update_Post_Success(){
        Post testPost = new Post(DataHelper.generateRandomTitle(), DataHelper.generateRandomContent());
        given()
                .spec(RequestSpecs.generateToken())
                .body(testPost)
                .put(resourcePath +"/"+ createdPost.toString())
        .then()
                .body("message", equalTo("Post updated"))
                .statusCode(200)
                .spec(ResponseSpecs.defaultSpec());
    }//Update_Post_Success

    @Test(groups = "create_post")
    public void Update_Post_Unsuccessful(){
        Post testPost = new Post("", DataHelper.generateRandomContent());
        given()
                .spec(RequestSpecs.generateToken())
                .body(testPost)
                .put(resourcePath +"/"+ createdPost.toString())
                .then()
                .body("message", equalTo("Invalid form"))
                .statusCode(406)
                .spec(ResponseSpecs.defaultSpec());
    }//Update_Invalid_Post

    @Test(groups = "create_post")
    public void Delete_Post_Success(){
        given()
                .spec(RequestSpecs.generateToken())
                .delete(resourcePath + "/" + createdPost.toString())
        .then()
                .body("message", equalTo("Post deleted"))
                .statusCode(200)
                .spec(ResponseSpecs.defaultSpec());
    }//Delete_Post_Success

    @Test(groups = "create_post")
    public void Delete_Invalid_Post(){
        given()
                .spec(RequestSpecs.generateToken())
                .delete(resourcePath + "/" + createdPost + createdPost.toString())
        .then()
                .body("message", equalTo("Post could not be deleted"))
                .statusCode(406)
                .spec(ResponseSpecs.defaultSpec());
    }//Delete_Invalid_Post

    @Test
    public void Test_Invalid_Token_New_Post(){

        Post testPost = new Post(DataHelper.generateRandomTitle(), DataHelper.generateRandomContent());

        given()
                .spec(RequestSpecs.generateFakeToken())
                .body(testPost)
                .post(resourcePath)
        .then()
                .statusCode(401)
                .spec(ResponseSpecs.defaultSpec());
    }//Test_Invalid_Token_New_Post

}
