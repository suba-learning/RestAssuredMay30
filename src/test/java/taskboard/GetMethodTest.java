package taskboard;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetMethodTest {
    @BeforeEach
    public void setup() {
        // Classic style
        baseURI = "https://taskboard.portnov.com/";
    }

    @Test
    public void getTask() {
        // Classic style
        //baseURI="https://taskboard.portnov.com/";
        Response response = get("api/v1/Task");
        System.out.println(response.statusCode());
        int status = response.statusCode();
        assertEquals(200, status);
    }

    @Test
    public void getTaskBdd() {
        // Classic style
       // baseURI="https://taskboard.portnov.com/";
        Response response = get("api/v1/Task");
        given()
                .get("api/v1/Task")
                .then()
                .statusCode(200).log();
    }

    @Test
    public void getTaskbyId() {
        // Classic style

        Response response = get("api/v1/Task/270");
        System.out.println(response.statusCode());
        int status = response.statusCode();
        assertEquals(200, status);
        JsonPath jp = response.jsonPath();

        response.prettyPrint();
        assertEquals(200, response.statusCode());
        assertEquals("Suba Task IJ",jp.getString("taskName"));
        assertEquals("Test Task",jp.getString("description"));
        assertEquals("2025-06-03T02:35:10.378",jp.getString("dueDate"));
        assertEquals("1",jp.getString("priority"));
        assertEquals("TODO",jp.getString("status"));
        assertEquals("Suba",jp.getString("author"));
    }

}
