package base;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BaseTest {

    @BeforeAll
    public static void setup()
    {
        baseURI = "http://recruit-1.portnov.com/recruit/api/v1/";
    }

//    protected String readFromJsonFile(String path) throws IOException {
//        return Files.readString(Paths.get(path));
//    }

    protected String readFromJsonFile(String path) {
        try {
            return Files.readString(Paths.get(path));
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to read JSON file: " + path, e);
        }}

    public String generateToken() throws IOException {
        String path = "src/test/resources/studentLogin.json";
        Files.readString(Paths.get(path));
        String credentials = readFromJsonFile(path);

        //System.out.println(credentials);

        Response response =
                given()
                        .contentType(ContentType.JSON)
                        .body(credentials)
                        .when()
                        .post("/login")
                        .then()
                        .statusCode(200).extract().response();
        response.prettyPrint();
        JsonPath jp = response.jsonPath();
        return jp.getString("token");
    }

    public Response createNewCandidate(String candidate){
        Response Candidateresponse =
                given()
                        .contentType(ContentType.JSON)
                        .body(candidate)
                        .when()
                        .post("/candidates")
                        .then()
                        .statusCode(201)
                        .log().body()
                        .extract().response();
        return Candidateresponse;
    }

    public Response createPosition(String positionbody,String token){
        String date = LocalDate.now().toString();

        Response position_response = given()
                .header("Authorization", "Bearer " + token )
                .contentType(ContentType.JSON)
                .body(positionbody)
                .when()
                .post("/positions")
                .then()
                .statusCode(201)
                .log().body()
                .extract().response();
        return position_response;
    }

    public Response createApplication(String applicationBody, String token){
        Response applicationResponse = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(applicationBody)
                .when()
                .post("/applications")
                .then()
                .statusCode(201)
                .log().body()
                .extract().response();

        return applicationResponse;
    }

}
