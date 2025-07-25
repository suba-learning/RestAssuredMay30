package image_ai;

import base.BaseRekognition;
import io.restassured.http.ContentType;
import io.restassured.internal.util.IOUtils;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

//API_KEY=AIzaSyANriv7yszmw-sbe8YN1GAmZDE5WYDDq1k
public class DetectLabelTest extends BaseRekognition {
    List<String > labels = List.of("Head", "Person", "People", "Face", "Man");

    @Test
    public void testDetectlables() throws IOException {
        byte[] imageByte;

        try {
            InputStream stream = getClass().getClassLoader().getResourceAsStream("Images/arnie-2.jpg");
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
                .post("https://image-ai.portnov.com/api/Image/detect-labels")
                .then()
                .statusCode(200)
                .extract().response();

       // response.prettyPrint();
        String responseString = response.asString();
        assertTrue(responseString.contains("Human"));
    }

    @Test
    public void testDetectlablesMultiple() throws IOException {
        byte[] imageByte;

        try {
            InputStream stream = getClass().getClassLoader().getResourceAsStream("Images/arnie-2.jpg");
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
                .post("https://image-ai.portnov.com/api/Image/detect-labels")
                .then()
                .statusCode(200)
                .extract().response();

        List<String> list = response.jsonPath().getList("name");
        System.out.println(list);

        for(String l : labels){
           assertTrue(list.contains(l));
        }
    }

}
