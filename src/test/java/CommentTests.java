import helper.DataHelper;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import model.Comment;
import model.Post;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Test;
import specifications.RequestSpecs;
import specifications.ResponseSpecs;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class CommentTests extends BaseTest{

    private static String resourcePathComment= "/v1/comment";
    private static String resourcePathPost= "/v1/post";
    private static Integer createdComment = 0;
    private static Integer createdPost = 0;

    @BeforeGroups("create_post")
    public void createPost(){

        Post testPost = new Post(DataHelper.generateRandomTitle(), DataHelper.generateRandomContent());

        Response response = given()
                .spec(RequestSpecs.generateToken())
                .body(testPost)
                .post(resourcePathPost);

        JsonPath jsonPathEvaluator = response.jsonPath();
        createdPost = jsonPathEvaluator.get("id");
    }
    @BeforeGroups("create_comment")
    public void Create_Comment() {
        Comment testComment = new Comment(DataHelper.generateRandomName(), DataHelper.generateRandomComment());

        Response response2 = given()
                .auth()
                .basic("testuser", "testpass")
                .body(testComment)
                .when()
                .post(resourcePathComment + "/" + createdPost.toString());

        JsonPath jsonPathEvaluator2 = response2.jsonPath();
        createdComment = jsonPathEvaluator2.get("id");
    }

    @Test(groups = "create_post")
    public void Create_Comment_Success(){

        Comment testComment = new Comment(DataHelper.generateRandomName(), DataHelper.generateRandomComment());

        given()
                .auth()
                .basic("testuser", "testpass")
                .body(testComment)
                .post(resourcePathComment + "/" + createdPost.toString())
        .then()
                .statusCode(200)
                .spec(ResponseSpecs.defaultSpec());
        System.out.println(createdPost);
    }//Create_Comment_Success

    @Test(groups = "create_post")
    public void Create_Comment_Unsuccessful(){
        Comment testComment = new Comment("", DataHelper.generateRandomComment());

        given()
                .auth()
                .basic("testuser", "testpass")
                .body(testComment)
                .post(resourcePathComment + "/" + createdPost.toString())
        .then()
                .body("message", equalTo("Invalid form"))
                .statusCode(406)
                .spec(ResponseSpecs.defaultSpec());
    }//Create_Invalid Comment

    @Test(groups = "create_post")
    public void Get_All_Comments() {
        given()
                .auth()
                .basic("testuser", "testpass")
                .get(resourcePathComment + "s/" + createdPost.toString())
        .then()
                .statusCode(200)
                .spec(ResponseSpecs.defaultSpec());
        System.out.println(createdPost);
    }//Get_All_Comments

    @Test(groups = {"create_post", "create_comment"})
    public void Get_One_Comment(){
        System.out.println(createdPost + " " + createdComment);
        given()
                .auth()
                .basic("testuser","testpass")
                .get(resourcePathComment+ "/" +createdPost +"/"+ createdComment.toString())
        .then()
                .statusCode(200)
                .spec(ResponseSpecs.defaultSpec());
    }//Get_One_Comment

    @Test(groups = {"create_post", "create_comment"})
    public void Get_Invalid_Comment(){
        System.out.println(createdPost + " " + createdComment);
        given()
                .auth()
                .basic("testuser","testpass")
                .get(resourcePathComment+ "/" +createdPost +"/"+ createdComment + createdComment.toString())
        .then()
                .body("Message", equalTo("Comment not found"))
                .statusCode(404)
                .spec(ResponseSpecs.defaultSpec());
    }

    @Test(groups = {"create_post", "create_comment"})
    public void Update_Comment_Success(){

        Comment testComment = new Comment(DataHelper.generateRandomName(), DataHelper.generateRandomComment());

        given()
                .auth()
                .basic("testuser","testpass")
                .body(testComment)
                .put(resourcePathComment+ "/"+ createdPost +"/" + createdComment.toString())
        .then()
                .body("message", equalTo("Comment updated"))
                .statusCode(200)
                .spec(ResponseSpecs.defaultSpec());
    }//Update_Comment_Success

    @Test(groups = {"create_post", "create_comment"})
    public void Update_Comment_Unsuccessful(){

        Comment testComment = new Comment(DataHelper.generateRandomName(), "");

        given()
                .auth()
                .basic("testuser","testpass")
                .body(testComment)
                .put(resourcePathComment+ "/"+ createdPost +"/" + createdComment .toString())
        .then()
                .body("message", equalTo("Invalid form"))
                .statusCode(406)
                .spec(ResponseSpecs.defaultSpec());
    }//Update_Comment_Unsuccessful

    @Test(groups = {"create_post", "create_comment"})
    public void Delete_Invalid_Comment(){

        given()
                .auth()
                .basic("testuser","testpass")
                .delete(resourcePathComment +"/"+ createdPost +"/"+ createdComment + createdComment.toString())
        .then()
                .body("message", equalTo("Comment could not be deleted"))
                .statusCode(406)
                .spec(ResponseSpecs.defaultSpec());
    }//Delete_Invalid_Comment

    @Test(groups = {"create_post", "create_comment"})
    public void Delete_Comment_Success(){

        given()
                .auth()
                .basic("testuser","testpass")
                .delete(resourcePathComment+"/"+createdPost+"/"+createdComment.toString())
        .then()
                .body("message", equalTo("Comment deleted"))
                .statusCode(200)
                .spec(ResponseSpecs.defaultSpec());
    }//Delete_Comment_Success

}
