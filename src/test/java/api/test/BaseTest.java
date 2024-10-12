package api.test;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeTest;

public class BaseTest {
    public static Faker FAKER = new Faker();

    @BeforeTest
    public void setApiUrl() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
    }
}
