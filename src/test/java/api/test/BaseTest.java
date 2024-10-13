package api.test;

import com.github.javafaker.Faker;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

import static org.testng.Assert.assertEquals;

public class BaseTest {
    public static Faker FAKER = new Faker();

    void validateStatusCode(Response response, int expectedStatusCode) {
        assertEquals(response.getStatusCode(), expectedStatusCode, "Status code did not match");
    }

    void validateSchema(Response response, String schemaFileName) {
        response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath(schemaFileName));
    }
}