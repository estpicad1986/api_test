import helper.DataHelper;
import model.Article;
import model.User;
import org.testng.annotations.Test;
import specifications.ResponseSpecs;

import static helper.DataHelper.generateRandomEmail;
import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

public class ArticleTests extends BaseTest{

    private static String resourcePath = "/v1/article";

    @Test
    public void Test_Create_Article(){

        Article testArticle = new Article(DataHelper.generateRandomTitle(), DataHelper.generateRandomContent());
        given()
                .body(testArticle).post(resourcePath)
        .then()
                .statusCode(200)
                .spec(ResponseSpecs.defaultSpec());
    }//Test_Create_Article
}
