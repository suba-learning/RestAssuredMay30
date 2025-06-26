package taskboard;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PutMethodTest {

    //Import Postmethod first and do the delete for that ID
    @BeforeEach
    public void setup() {
        // Classic style
        baseURI = "https://taskboard.portnov.com/";
    }

    @Test
    public void updateTask(){

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("id", 0);
        jsonObject.put("taskName", "Suba IJ update test");
        jsonObject.put("description", "Test Task");
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
        String taskId= jp.getString("id");

        jsonObject.put("id", taskId);
        jsonObject.put("taskName", "IJ Update");
        jsonObject.put("description", "Update Task");
        jsonObject.put("dueDate", "2025-06-24T02:35:10.378Z");
        //jsonObject.put("priority", 1);
        jsonObject.put("status","COMPLETED");
        jsonObject.put("author", "IJ");
        String putBody = jsonObject.toString();


        Response put_response = given()
                .contentType(ContentType.JSON)
                .body(putBody)
                .when()
                .put("/api/v1/Task/" + taskId);

        //System.out.println("PUT Response");
        put_response.prettyPrint();
        assertEquals(204, put_response.statusCode());

        Response get_response = get("api/v1/Task/" +  taskId);
        //System.out.println(response.statusCode());
        JsonPath get_jp = get_response.jsonPath();

        response.prettyPrint();
        assertEquals(200, get_response.statusCode());
        assertEquals("IJ Update",get_jp.getString("taskName"));
        assertEquals("Update Task",get_jp.getString("description"));
        assertEquals("2025-06-24T02:35:10.378",get_jp.getString("dueDate"));
        assertEquals("1",get_jp.getString("priority"));
        assertEquals("COMPLETED",get_jp.getString("status"));
        assertEquals("IJ",get_jp.getString("author"));

    }
}
