package taskboard;
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
        baseURI="https://taskboard.portnov.com/";
        Response response = get("api/Task");
        System.out.println(response.statusCode());
        int status = response.statusCode();
        assertEquals(200, status);
    }

    @Test
    public void getTaskBdd() {
        // Classic style
        baseURI="https://taskboard.portnov.com/";
        Response response = get("api/Task");
        given()
                .get("api/Task")
                .then()
                .statusCode(200).log();
    }


}
