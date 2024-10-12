package api.endpoinds;

import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class ApiService {

    public static RequestSpecification setUp() {
        return given()
                .filters(new RequestLoggingFilter(), new ResponseLoggingFilter())
                .contentType(JSON)
                .accept(JSON);
    }
}
