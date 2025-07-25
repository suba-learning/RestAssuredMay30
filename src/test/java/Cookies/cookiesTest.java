package Cookies;

import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class cookiesTest {
    @BeforeAll
    public static void setup() {
        baseURI = "https://image-ai.portnov.com/api/Cookie/";
    }

    @Test
    public void cookieTest(){
        Response response = given()
                .when()
                .get("get")
                .then()
                .statusCode(200)
                .extract().response();

        response.prettyPrint();

        Map<String, String> cookies =  response.getCookies();
        System.out.println(cookies);
        System.out.println(cookies.get("test"));

        Response check_cookieresponse = given()
                .cookies(cookies)
                .when()
                .get("check")
                .then()
                .extract().response();

        check_cookieresponse.prettyPrint();

        assertEquals("Cookie is valid", check_cookieresponse.asString());


    }

}
