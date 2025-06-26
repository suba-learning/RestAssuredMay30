package recruit;

import base.BaseTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class positionsTest extends BaseTest {
    private static String positionId ;
    private static String token;

    @Test
    @Order(1)
    public void createPosition() throws IOException {
        String positionBody = readFromJsonFile("src/test/resources/recruit/createPosition.json");
        String date = LocalDate.now().toString();
        //System.out.println(date);
        positionBody = positionBody.replace("today",date);
        //System.out.println(positionBody);
        token = generateToken();
        Response position = createPosition(positionBody,token);
        assertEquals(201,position.statusCode());
        positionId=position.jsonPath().getString("id");
        //System.out.println(positionId);
    }

    @Test
    @Order(2)
    public void readPosition(){

        Response get = given()
                .contentType(ContentType.JSON)
                .pathParam("id",positionId)
                .when()
                .get("/positions/{id}")
                .then()
                .log().body()
                .statusCode(200).extract().response();

    }
    @Test
    @Order(3)
    public void updatePosition() throws IOException {
        String positionBody = readFromJsonFile("src/test/resources/recruit/putPosition.json");
        token = generateToken();
        String date = LocalDate.now().toString();
        //System.out.println(date);
        positionBody = positionBody.replace("today",date);

        Response update = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token )
                .body(positionBody)
                .pathParam("id",positionId)
                .when()
                .put("/positions/{id}")
                .then()
                .log().body()
                .statusCode(200).extract().response();
    }

    @Test
    @Order(4)
    public void deletePosition(){
        Response delete = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token )
                .pathParam("id",positionId)
                .when()
                .delete("/positions/{id}")
                .then()
                .statusCode(204).extract().response();

        Response get = given()
                .contentType(ContentType.JSON)
                .pathParam("id",positionId)
                .when()
                .get("/positions/{id}")
                .then()
                .statusCode(400).extract().response();

    }

}
