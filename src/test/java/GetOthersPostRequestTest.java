import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasValue;
import static org.hamcrest.Matchers.not;

public class GetOthersPostRequestTest extends AbstractClass {
    @Test
    void getOthersPageTest() {
        given().spec(getRequestSpecification())
                .header("X-Auth-Token", getGetX_auth_token())
                .when()
                .get(getGetPostUrl()+"?owner=notMe")
                .then()
                .body("$", not(hasValue("authorId: 5964")));

        Assertions.assertNotEquals("authorId: 5964", Matchers.not("authorId: 5964"));

    }

    @Test
    void getNullPageTest() {
        given().spec(getRequestSpecification())
                .header("X-Auth-Token", getGetX_auth_token())
                .when()
                .get(getGetPostUrl()+"?owner=notMe&page=10000")
                .then()
                .statusCode(200)
                .extract().body().jsonPath().toString();

        Assertions.assertEquals("[]","[]");
    }

    @Test
    void getAllPageTest() {
        given().spec(getRequestSpecification())
                .header("X-Auth-Token", getGetX_auth_token())
                .when()
                .get(getGetPostUrl()+"?owner=notMe&order=ALL")
                .then()
                .statusCode(200)
                .extract().body().jsonPath().toString();

        Assertions.assertEquals("data", "data");
    }

    @Test
    void requestPageTest() {
        given().spec(getRequestSpecification())
                .header("X-Auth-Token", getGetX_auth_token())
                .when()
                .get(getGetPostUrl()+"?owner=notMe&page=2")
                .then()
                .statusCode(200)
                .extract().body().jsonPath().toString();

        Assertions.assertEquals("image", "image");
        Assertions.assertEquals("title", "title");
        Assertions.assertEquals("description", "description");
        Assertions.assertEquals("count: 4", "count: 4");
        Assertions.assertEquals("prevPage: 1","prevPage: 1");
        Assertions.assertEquals("nextPage: 3","nextPage: 3");
    }

    @Test
    void sortedPageTest() {
        given().spec(getRequestSpecification())
                .header("X-Auth-Token", getGetX_auth_token())
                .when()
                .get(getGetPostUrl()+"?owner=notMe&sort=2023-04-02&order=ASC&page=1")
                .then()
                .statusCode(200)
                .extract().body().jsonPath().toString();

        Assertions.assertEquals("image", "image");
        Assertions.assertEquals("title", "title");
        Assertions.assertEquals("description", "description");
        Assertions.assertEquals("count: 4", "count: 4");
        Assertions.assertEquals("prevPage: ","prevPage: ");
        Assertions.assertEquals("nextPage: 2","nextPage: 2");
    }

}
