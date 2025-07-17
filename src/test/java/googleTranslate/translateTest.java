package googleTranslate;

import base.BaseTrasnlate;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class translateTest extends BaseTrasnlate {

    @Test
    public void testTranslate(){
        String[] text = {"விவசாயத்தினை  மீட்பது", "Hola", "Sawubona"} ;
        String[] translatedResult = {"Restoring agriculture", "Hello", "Hello"};
        String language = "en";
        String[] detectedLanguage = {"ta","es","zu"};

        Response response = given()
                .queryParam("key", api_Key)
                .queryParam("q",text)
                .queryParam("target",language)
                .contentType(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract().response();
        response.prettyPrint();

        for (int i=0;i<text.length;i++){
            String translatedText=response.jsonPath().getString("data.translations[" +i+ "].translatedText");
            //System.out.println(translatedText);
            String detectedSourceLanguage=response.jsonPath().getString("data.translations["+i+"].detectedSourceLanguage");
            //System.out.println(detectedSourceLanguage);
            assertEquals(detectedLanguage[i], detectedSourceLanguage);
            assertEquals(translatedResult[i], translatedText);

        }


    }
}
