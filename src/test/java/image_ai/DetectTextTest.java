package image_ai;

import base.BaseRekognition;
import io.restassured.http.ContentType;
import io.restassured.internal.util.IOUtils;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DetectTextTest extends BaseRekognition {

    @Test
    public void testDetectText(){
        byte[] imageByte;

        try {
            InputStream stream = getClass().getClassLoader().getResourceAsStream("Images/lic-plate-1.jpg");
            Assertions.assertNotNull(stream, "Image stream is null");
            imageByte = IOUtils.toByteArray(stream);
            Assertions.assertTrue(imageByte.length > 0, "Image byte array is empty");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String imagebyte64 = Base64.getEncoder().encodeToString(imageByte);

        Map<String, Object> body = new HashMap<>();
        body.put("base64Image",imagebyte64);
        body.put("maxLabels", 5);
        body.put("minConfidence",1);

        Response response = given()
                .contentType(ContentType.JSON)
                .header("x-api-key", api_Key)
                .body(body)
                .when()
                .post("https://image-ai.portnov.com/api/Image/detect-text")
                .then()
                .statusCode(200)
                .extract().response();

       // response.prettyPrint();
        String responseString = response.asString();
        assertTrue(responseString.contains("WISCONSIN"));


    }

}
