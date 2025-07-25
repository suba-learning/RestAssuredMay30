package speech_ai;

import base.BaseRekognition;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.internal.util.IOUtils;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class Text2SpeechTest extends BaseRekognition {
    RequestSpecification spec;

    @BeforeEach
    public void ammend() {
        spec = new RequestSpecBuilder()
                .setBaseUri("https://image-ai.portnov.com/api/Speech")
                .addHeader("x-api-key", api_Key)
                .setContentType(ContentType.JSON)
                .build();
    }

    @Test
    public void Text2Speech() throws FileNotFoundException {

        Map<String, String> body = new HashMap<>();
        body.put("text","Hello, this is text to speech test!");

        Response response = given()
                .spec(spec)
                .body(body)
                .when()
                .post("convert-text-to-speech")
                .then()
                .statusCode(200)
                .extract().response();
        //response.prettyPrint();
        byte[] bytes = response.asByteArray();
        try (FileOutputStream stream = new FileOutputStream("text2speech.mp3")) {
            stream.write(bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }


}
