package taskboard;


import io.restassured.path.json.JsonPath;
import org.json.JSONObject;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class PostMethodTest {

    @BeforeEach
    public void setup() {
        baseURI = "https://taskboard.portnov.com";
    }

    @Test
    public void postTask() {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("id", 0);
        jsonObject.put("taskName", "Suba IJ");
        jsonObject.put("description", "Test Task");
        jsonObject.put("dueDate", "2025-06-03T02:35:10.378Z");
        jsonObject.put("priority", 1);
        jsonObject.put("status","TODO");
        jsonObject.put("author", "Suba");
        System.out.println(jsonObject);
        String stringBody = jsonObject.toString();

        Response response = given()
                .contentType(ContentType.JSON)
                .body(stringBody)
                .when()
                .post("/api/Task/");

        JsonPath jp = response.jsonPath();

        response.prettyPrint();
        assertEquals(201, response.statusCode());
        assertEquals("Suba IJ",jp.getString("taskName"));

    }

}
