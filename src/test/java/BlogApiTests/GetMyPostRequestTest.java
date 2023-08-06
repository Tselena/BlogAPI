package BlogApiTests;
import io.restassured.authentication.FormAuthConfig;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static io.restassured.RestAssured.*;
import static io.restassured.authentication.FormAuthConfig.formAuthConfig;
import static org.hamcrest.Matchers.*;

public class GetMyPostRequestTest extends AbstractClass{

    @Test
    @DisplayName("10 own posts are displayed per page")
    void getMyPageTest() {
        given().spec(getRequestSpecification())
                .header("X-Auth-Token", getGetX_auth_token())
                .expect()
                .body("data[0].authorId", equalTo(22130))
                .body("data[1].authorId", equalTo(22130))
                .body("data[2].authorId", equalTo(22130))
                .body("data[9].authorId", equalTo(22130))
                .when()
                .get(getGetPostUrl()+"?sort=createdAt&order=ASC&page=2")
                .then()
                .statusCode(200);
    }

    @Test
    @DisplayName("Not-existed page of own posts")
    void getNullPageTest() {
        given().spec(getRequestSpecification())
                .header("X-Auth-Token", getGetX_auth_token())
                .when()
                .get(getGetPostUrl()+"?page=10")
                .then()
                .statusCode(200);

        String result = given().spec(getRequestSpecification())
                .header("X-Auth-Token", getGetX_auth_token())
                .get(getGetPostUrl()+"?page=10")
                .body().jsonPath().get("data").toString();
        Assertions.assertEquals("[]", result);
    }

    @Test
    @DisplayName("Display page of own sorted posts")
    void getSortedPageTest() {
        given().spec(getRequestSpecification())
                .header("X-Auth-Token", getGetX_auth_token())
                .when()
                .get(getGetPostUrl()+"?sort=CreatedAt&page=2")
                .then()
                .statusCode(200);

        String nextPage = given().spec(getRequestSpecification())
                .header("X-Auth-Token", getGetX_auth_token())
                .when()
                .get(getGetPostUrl()+"?sort=CreatedAt&page=2")
                .body().jsonPath().get("meta.nextPage").toString();
        Assertions.assertEquals("3", nextPage);
    }

    @Test
    @DisplayName("Post has title and description")
    void requestPageTest() {
        String responseTitle =
        given().spec(getRequestSpecification())
                .header("X-Auth-Token", getGetX_auth_token())
                .when()
                .get(getGetPostUrl()+"?page=1")
                .then()
                .statusCode(200)
                .extract().body().jsonPath().getJsonObject("data[0].title").toString();
        Assertions.assertEquals("» оп€ть сегодн€ жарко", responseTitle);

        String responseDescription =
                given().spec(getRequestSpecification())
                        .header("X-Auth-Token", getGetX_auth_token())
                        .when()
                        .get(getGetPostUrl()+"?page=1")
                        .then()
                        .statusCode(200)
                        .extract().body().jsonPath().getJsonObject("data[3].description").toString();
        Assertions.assertEquals("про тестирование", responseDescription);
    }

    @Test
    @DisplayName("Not-authorized user")
    void notAuthorizedRequestTest() {
                given().spec(getRequestSpecification())
                        .when()
                        .get(getGetPostUrl()+"?sort=createdAt&order=ASC&page=1")
                        .then()
                        .statusCode(401);
    }

}
