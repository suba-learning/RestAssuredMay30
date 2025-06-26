package recruit;

import base.BaseTest;
import io.restassured.config.EncoderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Random;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class E2ETest extends BaseTest {
    private static String token;
    private static String candidateID;
    private static String positionID;
    private static String applicationID;



    @Test
    // Create new Candidate with Post /Candidate
    public void createCandidate() throws IOException {
        String path = "src/test/resources/candidateInfo.json";
        Files.readString(Paths.get(path));
        String candidate = readFromJsonFile(path);
        System.out.println("Candidate Info:");
        System.out.println(candidate);

        Random random = new Random();
        int randomNumber = 1000 + random.nextInt(9000);
        String email = "candidate" + randomNumber + "@example.com";
        candidate = candidate.replace("SN1June18@test.com", email);
        String firstName ="Suba";
        String lastName ="Test";

//        JSONObject candidate = new JSONObject();
//        candidate.put("firstName", "Suba");
//        candidate.put("middleName", "");
//        candidate.put("lastName", "Hetfield");
//        candidate.put("email", email);
//        candidate.put("password", "welcome");
//        candidate.put("address", " 323 San Dimas St");
//        candidate.put("city", "San Fran");
//        candidate.put("state", "CA");
//        candidate.put("zip", "94949");
//        candidate.put("summary", "YEAAAAAHH I am the table");

        candidateID = createNewCandidate(candidate).jsonPath().getString("id");
        System.out.println("Candidate ID: "+candidateID);
        System.out.println("===================================");

// Login with new candidate to get token> Post /Login

        JSONObject credentials = new JSONObject();
        credentials.put("email", email);
        credentials.put("password", "welcome");

        Response loginResponse = given()
                .contentType(ContentType.JSON)
                .body(credentials.toString())
                .when()
                .post("/login")
                .then()
                .statusCode(200)
               // .log().body()
                .extract().response();

        token = loginResponse.jsonPath().getString("token");
        System.out.println("token "+token);


//Create New position with Post /position
        JSONObject position = new JSONObject();
        String date = LocalDate.now().toString();
        position.put("title", "Singer Testing");
        position.put("address", "1 First St");
        position.put("city", "City");
        position.put("state","CA");
        position.put("zip", "90000");
        position.put("description","Testing role");
        position.put("dateOpen", date);
        position.put("company","Apple");
        System.out.println("Position Info:");
        positionID = createPosition(position.toString(),token).jsonPath().getString("id");
        System.out.println("Position Id: "+positionID);
        System.out.println("===================================");

        //Create a new application (Candidate + Postion) > POST /application

        JSONObject application = new JSONObject();
        application.put("candidateId", candidateID);
        application.put("positionId", positionID);
        application.put("dateApplied", date);

        System.out.println("Application Info:");
        applicationID = createApplication(application.toString(),token).jsonPath().getString("id");
        System.out.println("Application Id: "+applicationID);
        System.out.println("===================================");

//Validate the Application > GET /application/Id
        System.out.println("Full Application Info:");
        Response validateResponse = given()
                .contentType(ContentType.JSON)
                .when()
                .get("/applications/" + applicationID)
                .then()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchema("src/test/resources/recruit/verifyResponseSchema.json"))
                .log().body()
                .extract().response();
        JsonPath jsonValidate = validateResponse.jsonPath();

//        assertEquals(applicationID, jsonValidate.getString("id"));
//        assertEquals(candidateID, jsonValidate.getString("candidateId"));
//        assertEquals(positionID, jsonValidate.getString("positionId"));
//
//        assertEquals(firstName, jsonValidate.getString("firstName"));
//        assertEquals(lastName, jsonValidate.getString("lastName"));
//        assertEquals(email,jsonValidate.getString("email"));
//        assertEquals("Singer Testing", jsonValidate.getString("title"));
//        assertEquals(date, jsonValidate.getString("dateApplied").substring(0, 10));
//        assertEquals("Candidate Summary", jsonValidate.getString("summary"));
//        assertEquals("Testing role", jsonValidate.getString("description"));
//        assertEquals(date, jsonValidate.getString("dateOpen").substring(0, 10));
//        assertEquals("Apple", jsonValidate.getString("company"));
//        assertEquals("123 Collins St", jsonValidate.getString("candidate_address"));
//        assertEquals("Melbourne", jsonValidate.getString("candidate_city"));
//        assertEquals("CA", jsonValidate.getString("candidate_state"));
//        assertEquals("95014", jsonValidate.getString("candidate_zip"));
//        assertEquals("1 First St", jsonValidate.getString("position_address"));
//        assertEquals("City", jsonValidate.getString("position_city"));
//        assertEquals("CA", jsonValidate.getString("position_state"));
//        assertEquals("90000", jsonValidate.getString("position_zip"));
    }











    //Delete Backwards to delete




}
