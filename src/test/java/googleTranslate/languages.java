

package googleTranslate;

import base.BaseTrasnlate;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static io.restassured.RestAssured.baseURI;
import static org.junit.jupiter.api.Assertions.assertTrue;






public class languages extends BaseTrasnlate {
    //private static final String api_Key = System.getenv("API_KEY");
    //List<String> expectedLanguages = readFromJsonFile("src/test/resources/tranaslate/languages.json");

    @Test
    public void testallLanguage(){
//        Response response = given()
//                .queryParam("key",api_Key)
//                .when()
//                .get("/languages")
//                .then()
//                .statusCode(200)
//                .extract().response();
//        //response.prettyPrint();
//        List<String> returnedLanguages = response.jsonPath().getList("data.languages.language");
//        //assertTrue(returnedLanguages.contains("en"));
//        assertTrue(returnedLanguages.contains("ta"),"Language code can't be found");
//        //System.out.println(returnedLanguages);
//
//        for (String code: expectedLanguages) {
//            assertTrue(returnedLanguages.contains(code));
//            System.out.println("Found "+code+" Successfully");
//        }
        System.out.println(loadExpectedLanguages());

    }


}
