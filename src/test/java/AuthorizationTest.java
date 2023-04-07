import io.restassured.authentication.FormAuthConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class AuthorizationTest extends AbstractClass{

    @Test
    void getAuthByLoginPswTest() {
        given().spec(getRequestSpecification())
                .formParam("username","Dum")
                .formParam("password", "2761a156a4")
                .when()
                .post("https://test-stand.gb.ru/192.168.0.1/login")
                .then()
                .statusCode(200);
    }

    @Test
    void getAuthByWrongLoginPswTest() {
        given().spec(getRequestSpecification())
                .formParam("username","Dum")
                .formParam("password", " ")
                .when()
                .post("https://test-stand.gb.ru/192.168.0.1/login")
                .then()
                .statusCode(200);
    }

    @Test
    void getAuthTest() {
        given().spec(getRequestSpecification())
                .header("X-Auth-Token","cb514dc64803d69ded1707a062fd2385")
                .when()
                .post("https://test-stand.gb.ru/192.168.0.1/login")
                .then()
                .statusCode(200);
    }
}
