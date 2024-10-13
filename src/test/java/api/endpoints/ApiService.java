package api.endpoints;

import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class ApiService {

    public static RequestSpecification setUp() {
        return given()
                .baseUri("https://petstore.swagger.io/v2")
                .filters(new RequestLoggingFilter(), new ResponseLoggingFilter())
                .accept(JSON);
    }
}
