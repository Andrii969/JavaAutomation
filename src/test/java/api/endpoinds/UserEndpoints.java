package api.endpoinds;

import api.payload.User;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserEndpoints {

    public static Response createUser(User payload) {
        Response response =
        given()
                .contentType("application/json")
                .accept("application/json")
                .body(payload)
        .when()
                .post(Routes.POST_USER_URL);

        return response;
    }

    public static Response getUser(String userName) {
        Response response =
        given()
                .pathParam("userName", userName)
        .when()
                .get(Routes.GET_USER_URL);

        return response;
    }

    public static Response updateUser(String userName, User payload) {
        Response response =
        given()
                .pathParam("userName", userName)
                .contentType("application/json")
                .accept("application/json")
                .body(payload)
        .when()
                .put(Routes.PUT_USER_URL);

        return response;
    }

    public static Response deleteUser(String userName) {
        Response response =
        given()
                .pathParam("userName", userName)
        .when()
                .delete(Routes.DELETE_USER_URL);

        return response;
    }
}
