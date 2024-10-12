package api.endpoinds;

import api.payload.User;
import com.github.javafaker.Faker;
import io.restassured.response.Response;

import static api.endpoinds.Endpoints.User.USER;
import static api.endpoinds.Endpoints.User.USER_NAME;
import static api.test.BaseTest.FAKER;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class UserService extends ApiService {

    public Response createUser(User payload) {
        return setUp()
                .body(payload)
                .when()
                .post(USER);
    }

    public Response getUser(String userName) {
        return given()
                .pathParam("userName", userName)
                .when()
                .get(USER_NAME);
    }

    public Response updateUser(String userName, User payload) {
        return setUp()
                .pathParam("userName", userName)
                .body(payload)
                .when()
                .put(USER_NAME);
    }

    public Response deleteUser(String userName) {
        return given()
                .pathParam("userName", userName)
                .when()
                .delete(USER_NAME);
    }

    public User getValidModel() {
        return new User() // @Accessors allows to get access to all class properties
                .setId(FAKER.idNumber().hashCode())
                .setUsername(FAKER.name().username())
                .setFirstName(FAKER.name().firstName())
                .setLastName(FAKER.name().lastName())
                .setEmail(FAKER.internet().safeEmailAddress())
                .setPassword(FAKER.internet().password(5, 10))
                .setPhone(FAKER.phoneNumber().cellPhone())
                .setUserStatus(0);
    }
}