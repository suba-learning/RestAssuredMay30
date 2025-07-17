package googleTranslate;

import base.BaseTrasnlate;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static io.restassured.RestAssured.baseURI;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class detectTest extends BaseTrasnlate {

    //private static final String api_Key = System.getenv("API_KEY");

    @BeforeAll
    static void setup() {
        baseURI = "https://translation.googleapis.com/language/translate/v2";
    }

    @Test
    public void testdetectLanguage() {

        String translate = "வராஹி நவராத்திரி";
        Response response = given()
                .queryParam("key", api_Key)
                .queryParam("q", translate)
                .when()
                .post("/detect")
                .then()
                .statusCode(200)
                .extract().response();
        response.prettyPrint();

        //String res_object = response.jsonPath().getString("data.detections[0][0]");
        //System.out.println(res_object);
        Map<String, Object> detections = response.jsonPath().getMap("data.detections[0][0]");
        assertEquals("ta", detections.get("language"));
        //assertTrue("false", detections.get("isReliable"));


    }
}
