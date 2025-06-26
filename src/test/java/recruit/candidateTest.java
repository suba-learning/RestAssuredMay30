package recruit;

import base.BaseTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class candidateTest extends BaseTest{
    public static String candidateid;
    private static String token;
    //CRUD

    @Test
    @Order(1)
    public void createCandidate(){
        //String path = "src/test/resources/recruit/createCandidate.json";
        String candidateBody = readFromJsonFile("src/test/resources/recruit/createCandidate.json");
        //System.out.println(candidate);
        Response candidate = createNewCandidate(candidateBody);
        assertEquals(201,candidate.statusCode());
        candidateid=candidate.jsonPath().getString("id");
    }

    @Test
    @Order(2)
    public void readCandidate(){

        Response get = given()
                .contentType(ContentType.JSON)
                .pathParam("id",candidateid)
                .when()
                .get("/candidates/{id}")
                .then()
                .statusCode(200).extract().response();

    }

    @Test
    @Order(3)
    public void updateCandidate() throws IOException {
        String candidateBody = readFromJsonFile("src/test/resources/recruit/putCandidate.json");
        token = generateToken();

        Response update = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token )
                .body(candidateBody)
                .pathParam("id",candidateid)
                .when()
                .put("/candidates/{id}")
                .then()
                .statusCode(200).extract().response();
    }

    @Test
    @Order(4)
    public void deleteCandidate(){
        Response delete = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token )
                .pathParam("id",candidateid)
                .when()
                .delete("/candidates/{id}")
                .then()
                .statusCode(204).extract().response();

        Response get = given()
                .contentType(ContentType.JSON)
                .pathParam("id",candidateid)
                .when()
                .get("/candidates/{id}")
                .then()
                .statusCode(400).extract().response();


    }


}

