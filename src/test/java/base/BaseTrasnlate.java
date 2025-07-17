package base;



import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;

public class BaseTrasnlate {

    protected static final String api_Key = System.getenv("API_KEY");

    @BeforeAll
    static void setup() {
        baseURI = "https://translation.googleapis.com/language/translate/v2";
    }

    protected String readFromJsonFile(String path) {
        try {
            return Files.readString(Paths.get(path));
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to read JSON file: " + path, e);
        }}

    protected List<String> loadExpectedLanguages(){
        try{
            Map<String, List<String>> languagesFull = new ObjectMapper()
                    .readValue(
                            new File("src/test/resources/tranaslate/languages.json"),
                            new TypeReference<Map<String, List<String>>>() {
                            }
                    );
            return languagesFull.get("languages");
        }catch (IOException e) {
            throw new UncheckedIOException("String not exist",e);
        }
    }
}
