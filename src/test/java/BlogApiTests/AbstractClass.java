package BlogApiTests;import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static io.restassured.RestAssured.basic;

public class AbstractClass {
    static Properties properties = new Properties();
    private static InputStream configFile;
    private static String getLoginUrl;
    private static String getPostUrl;
    private static String getX_auth_token;
    protected static ResponseSpecification responseSpecification;
    protected static RequestSpecification requestSpecification;

    @BeforeAll
    static void initTest() throws IOException {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        configFile = new FileInputStream("src/main/resources/resources.properties");
        properties.load(configFile);

        getPostUrl = properties.getProperty("getPostUrl");
        getX_auth_token = properties.getProperty("getX_auth_token");
        getLoginUrl = properties.getProperty("getLoginUrl");

        requestSpecification = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();

        responseSpecification = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .expectResponseTime(Matchers.lessThan(5000L))
                .build();

        RestAssured.responseSpecification = responseSpecification;
        RestAssured.requestSpecification = requestSpecification;
    }

    public static String getLoginUrl() {return getLoginUrl;}

    public static String getGetPostUrl() {
        return getPostUrl;
    }

    public static String getGetX_auth_token() {
        return getX_auth_token;
    }

    public static RequestSpecification getRequestSpecification() {
        return requestSpecification;
    }
}
