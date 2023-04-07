import io.restassured.authentication.FormAuthConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import static io.restassured.RestAssured.*;
import static io.restassured.authentication.FormAuthConfig.formAuthConfig;
import static org.hamcrest.Matchers.hasValue;
import static org.hamcrest.Matchers.not;

public class GetMyPostRequestTest extends AbstractClass{

    @Test
    void getMyPageTest() {
        given().spec(getRequestSpecification())
                .header("X-Auth-Token", getGetX_auth_token())
                .when()
                .get(getGetPostUrl()+"?sort=createdAt&order=ASC&page=2")
                .then()
                .statusCode(200)
                        .extract().body().jsonPath().toString();

          Assertions.assertEquals("prevPage: 1","prevPage: 1");
          Assertions.assertEquals("nextPage: 3","nextPage: 3");
    }

    @Test
    void getNullPageTest() {
        given().spec(getRequestSpecification())
                .header("X-Auth-Token", getGetX_auth_token())
                .when()
                .get(getGetPostUrl()+"?page=10")
                .then()
                .statusCode(200)
                .extract().body().jsonPath().toString();

        Assertions.assertEquals("[]","[]");
    }

    @Test
    void getSortedPageTest() {
        given().spec(getRequestSpecification())
                .header("X-Auth-Token", getGetX_auth_token())
                .when()
                .get(getGetPostUrl()+"?sort=2023-04-02&page=1")
                .then()
                .statusCode(200)
                .body("$", hasValue("createdAt: 2023-04-02"));

        Assertions.assertEquals("createdAt: 2023-04-02", "createdAt: 2023-04-02");
    }

    @Test
    void requestPageTest() {
        given().spec(getRequestSpecification())
                .header("X-Auth-Token", getGetX_auth_token())
                .when()
                .get(getGetPostUrl()+"?page=1")
                .then()
                .statusCode(200)
                .extract().body().jsonPath().toString();

        Assertions.assertEquals("image", "image");
        Assertions.assertEquals("title", "title");
        Assertions.assertEquals("description", "description");
        Assertions.assertEquals("count: 10", "count: 10");
    }

    @Test
    void notAuthorizedRequestTest() {
        given().spec(getRequestSpecification())
                //.header("X-Auth-Token", getGetX_auth_token())
                .when()
                .get(getGetPostUrl()+"?sort=createdAt&order=ASC&page=1")
                .then()
                .statusCode(401)
                .statusLine("Auth header required X-Auth-Token");

        Assertions.assertEquals("Auth header required X-Auth-Token", "Auth header required X-Auth-Token");
    }


}
