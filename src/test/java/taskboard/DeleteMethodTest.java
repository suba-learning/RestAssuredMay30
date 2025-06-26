package taskboard;

import io.restassured.http.ContentType;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static io.restassured.RestAssured.baseURI;

public class DeleteMethodTest {

    //Import Postmethod first and do the delete for that ID
    @BeforeEach
    public void setup() {
        // Classic style
        baseURI = "https://taskboard.portnov.com/";
    }

    @Test
    public void deleteTask(){

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("id", 0);
        jsonObject.put("taskName", "Suba Delete Task");
        jsonObject.put("description", "Test DELETE");
        jsonObject.put("dueDate", "2025-06-03T02:35:10.378Z");
        jsonObject.put("priority", 1);
        jsonObject.put("status","TODO");
        jsonObject.put("author", "Suba");
        //System.out.println(jsonObject);
        String stringBody = jsonObject.toString();

        Response response = given()
                .contentType(ContentType.JSON)
                .body(stringBody)
                .when()
                .post("/api/v1/Task/");

        JsonPath jp = response.jsonPath();

        response.prettyPrint();
        assertEquals(201, response.statusCode());
        String taskId= jp.getString("id");
        System.out.println("Task Id: "+taskId);
        Response delete_response = given()
                .contentType(ContentType.JSON)
                .when()
                .delete("/api/v1/Task/" + taskId);

        //System.out.println(delete_response.statusCode());
        assertEquals(204, delete_response.statusCode());


        Response get_response = get("api/v1/Task/"+taskId);
        int status = get_response.statusCode();
        assertEquals(404, status);
    }

}


