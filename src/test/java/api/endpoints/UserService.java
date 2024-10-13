package api.endpoints;

import api.payload.User;
import io.restassured.response.Response;
import static api.endpoints.Endpoints.User.USER;
import static api.endpoints.Endpoints.User.USER_NAME;
import static api.test.BaseTest.FAKER;
import static io.restassured.http.ContentType.JSON;

public class UserService extends ApiService {

    public Response createUser(User payload) {
        return setUp()
                .contentType(JSON)
                .body(payload)
                .when()
                .post(USER);
    }

    public Response getUser(Object username) {
        return setUp()
                .pathParam("userName", username)
                .when()
                .get(USER_NAME);
    }

    public Response updateUser(Object username, User payload) {
        return setUp()
                .pathParam("userName", username)
                .contentType(JSON)
                .body(payload)
                .when()
                .put(USER_NAME);
    }

    public Response deleteUser(Object username) {
        return setUp()
                .pathParam("userName", username)
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

    public User getUserFromResponse(Response response) {
        return response.as(User.class);
    }
}