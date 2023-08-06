import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasValue;
import static org.hamcrest.Matchers.not;


public class GetOthersPostRequestTest extends AbstractClass {
    @Test
    @DisplayName("Posts of other users are displayed")
    void getOthersPageTest() {
        given().spec(getRequestSpecification())
                .header("X-Auth-Token", getGetX_auth_token())
                .when()
                .get(getGetPostUrl()+"?owner=notMe")
                .then()
                .body("authorId", not(hasValue("authorId: 22130")));
        Assertions.assertNotEquals("authorId: 22130", Matchers.not("authorId: 22130"));
    }

    @Test
    @DisplayName("Not existed page of other user's posts")
    void getNullPageTest() {
        given().spec(getRequestSpecification())
                .header("X-Auth-Token", getGetX_auth_token())
                .when()
                .get(getGetPostUrl()+"?owner=notMe&page=10000")
                .then()
                .statusCode(200);

        String result = given().spec(getRequestSpecification())
                .header("X-Auth-Token", getGetX_auth_token())
                .get(getGetPostUrl()+"?owner=notMe&page=10000")
                .body().jsonPath().get("data").toString();
        Assertions.assertEquals("[]", result);
    }

    @Test
    @DisplayName("All posts of others users are displayed")
    void getAllPageTest() {
        String count = given().spec(getRequestSpecification())
                .header("X-Auth-Token", getGetX_auth_token())
                .when()
                .get(getGetPostUrl()+"?owner=notMe&order=ALL")
                .then()
                .statusCode(200)
                .body("authorId", not(hasValue("authorId: 22130")))
                .extract().body().jsonPath().getJsonObject("meta.count").toString();
        Assertions.assertNotEquals("authorId: 22130", Matchers.not("authorId: 22130"));
        Assertions.assertEquals("39130", count);
    }

    @Test
    @DisplayName("Post of other users has title")
    void requestPageTest() {
        String responseTitle = given().spec(getRequestSpecification())
                .header("X-Auth-Token", getGetX_auth_token())
                .when()
                .get(getGetPostUrl()+"?owner=notMe&page=1504")
                .then()
                .statusCode(200)
                .body("authorId", not(hasValue("authorId: 22130")))
                .extract().body().jsonPath().getJsonObject("data[2].title").toString();
        Assertions.assertEquals("тестовый пост", responseTitle);
        Assertions.assertNotEquals("authorId: 22130", Matchers.not("authorId: 22130"));
    }

    @Test
    @DisplayName("Sort of other user's posts")
    void sortedPageTest() {
        String sortedPostTitle =
        given().spec(getRequestSpecification())
                .header("X-Auth-Token", getGetX_auth_token())
                .when()
                .get(getGetPostUrl()+"?owner=notMe&sort=createdAt&order=ASC&page=3")
                .then()
                .statusCode(200)
                .body("authorId", not(hasValue("authorId: 22130")))
                .extract().body().jsonPath().getJsonObject("data[3].title").toString();
        Assertions.assertEquals("Гора Арагац", sortedPostTitle);
    }

}
