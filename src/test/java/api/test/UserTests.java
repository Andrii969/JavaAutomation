package api.test;

import api.endpoints.UserService;
import api.payload.User;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;

public class UserTests extends BaseTest {
    private static final String USER_SCHEMA = "SchemaForPostPutDeleteUser.json";
    private static final String GET_USER_SCHEMA = "SchemaForGetUser.json";

    final UserService userService = new UserService();

    private void cleanupUser(Object username) {
        userService.deleteUser(username);
    }

    @Test
    public void testCreateUser() {
        User userPayload = userService.getValidModel();
        Response response = userService.createUser(userPayload);

        validateStatusCode(response, HttpStatus.SC_OK);
        validateSchema(response, USER_SCHEMA);

        cleanupUser(userPayload.getUsername());
    }

    @Test
    public void testGetUser() {
        User userPayload = userService.getValidModel();
        userService.createUser(userPayload);

        Response response = userService.getUser(userPayload.getUsername());

        validateStatusCode(response, HttpStatus.SC_OK);
        validateSchema(response, GET_USER_SCHEMA);

        User responseUser = userService.getUserFromResponse(response);
        assertEquals(responseUser, userPayload, "The retrieved user should match the created user");

        cleanupUser(userPayload.getUsername());
    }

    @Test
    public void testUpdateUser() {
        User userPayload = userService.getValidModel();
        userService.createUser(userPayload);

        userPayload.setId(FAKER.idNumber().hashCode());
        userPayload.setFirstName(FAKER.name().firstName());
        userPayload.setLastName(FAKER.name().lastName());
        userPayload.setEmail(FAKER.internet().safeEmailAddress());
        userPayload.setPassword(FAKER.internet().password(5, 10));
        userPayload.setPhone(FAKER.phoneNumber().cellPhone());
        userPayload.setUserStatus(1);

        Response response = userService.updateUser(userPayload.getUsername(), userPayload);

        validateStatusCode(response, HttpStatus.SC_OK);
        validateSchema(response, USER_SCHEMA);

        Response getUserResponse = userService.getUser(userPayload.getUsername());
        User fetchedUser = userService.getUserFromResponse(getUserResponse);
        assertEquals(fetchedUser, userPayload, "The fetched user should match the updated user");

        cleanupUser(userPayload.getUsername());
    }

    @Test
    public void testDeleteUser() {
        User userPayload = userService.getValidModel();
        userService.createUser(userPayload);
        Response response = userService.deleteUser(userPayload.getUsername());

        validateStatusCode(response, HttpStatus.SC_OK);
        validateSchema(response, USER_SCHEMA);

        Response getUserResponse = userService.getUser(userPayload.getUsername());

        validateStatusCode(getUserResponse, HttpStatus.SC_NOT_FOUND);
        validateSchema(getUserResponse, USER_SCHEMA);
    }
}