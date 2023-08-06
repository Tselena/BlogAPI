package BlogApiTests;import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class AuthorizationTest extends AbstractClass{


    @Test
    @DisplayName("Authorization with valid login and password")
    void authByLoginPswTest() {
       RestAssured.registerParser("text/plain;charset=UTF-8", Parser.JSON);
       given().spec(getRequestSpecification())
                .header("Content-Type", "multipart/form-data")
                .formParam("token", "2016bbb695f03eb97382e9c802ff5283")
                .multiPart("username", "Ivanovych", "Content-Type=application/json")
                .multiPart("password", "cd6659c5c6", "Content-Type=application/json")
                .when()
                .post(getLoginUrl()+"/gateway/login")
                .getStatusCode();
    }

    @Test
    @DisplayName("Authorization with specSymbolPassword")
    void loginBySpecSymbolsPswTest() {
        RestAssured.registerParser("text/plain;charset=UTF-8", Parser.JSON);
        given().spec(getRequestSpecification())
                .header("Content-Type", "multipart/form-data")
                .formParam("token", "2016bbb695f03eb97382e9c802ff5283")
                .multiPart("username", "Ivanovych", "Content-Type=application/json")
                .multiPart("password", "!#!@$@#%$##^", "Content-Type=application/json")
                .when()
                .post(getLoginUrl()+"/gateway/login")
                .getStatusCode();
    }

    @Test
    @DisplayName("Authorization by login only")
    void loginWithoutPswTest() {
        RestAssured.registerParser("text/plain;charset=UTF-8", Parser.JSON);
        given().spec(getRequestSpecification())
                .header("Content-Type", "multipart/form-data")
                .multiPart("username", "Ivanovych", "Content-Type=application/json")
                .multiPart("password", " ", "Content-Type=application/json")
                .when()
                .post(getLoginUrl()+"/gateway/login")
                .getStatusCode();
    }
}
