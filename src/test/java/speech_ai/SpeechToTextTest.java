package speech_ai;

import base.BaseRekognition;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SpeechToTextTest extends BaseRekognition {
    RequestSpecification spec;

    @BeforeEach
    public void ammend(){
        spec = new RequestSpecBuilder()
                .setBaseUri("https://image-ai.portnov.com/api/Speech")
                .addHeader("x-api-key", api_Key)
                .build();
    }


    @Test
    public void speech2Text(){
        File mp3 = new File(getClass().getClassLoader().getResource("speech/test_speech.mp3").getFile());
        Response response = given()
                .spec(spec)
                .contentType(ContentType.MULTIPART)
                .multiPart("audioFile",mp3)
                .when()
                .post("/convert-speech-to-text")
                .then()
                .extract().response();

        response.prettyPrint();
        assertEquals("COMPLETED", response.jsonPath().getString("jsonResponse.status"));
        assertEquals("Hello, this is a test for text to speech conversion.", response.jsonPath().getString("jsonResponse.results.transcripts[0].transcript"));
       // System.out.println( response.jsonPath().getString("jsonResponse.results.transcripts[0].transcript"));

    }
}
