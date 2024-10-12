package api.test;

import api.endpoinds.UserEndpoints;
import api.payload.User;
import com.github.javafaker.Faker;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Map;
import java.util.Set;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;


public class UserTests {

    Faker faker;
    User userPayload;

    @BeforeClass
    public void setupData() {
        faker = new Faker();
        userPayload = new User();
        userPayload.setId(faker.idNumber().hashCode());
        userPayload.setUsername(faker.name().username());
        userPayload.setFirstName(faker.name().firstName());
        userPayload.setLastName(faker.name().lastName());
        userPayload.setEmail(faker.internet().safeEmailAddress());
        userPayload.setPassword(faker.internet().password(5,10));
        userPayload.setPhone(faker.phoneNumber().cellPhone());
    }

    @Test(priority = 1)
    public void testCreateUser() {
        Response response = UserEndpoints.createUser(this.userPayload);

        response
        .then().log().all()
        .statusCode(200)
        .body("code", equalTo(200))
        .body("type", equalTo("unknown"))
        .body("message", equalTo(String.valueOf(this.userPayload.getId())))
        .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("SchemaForPostPutDeleteUser.json"));

        Map<String, Object> responseBody = response.jsonPath().getMap("$"); // the $ symbol represents the root element of the JSON structure
        assert responseBody.containsKey("code") : "Response should contain 'code'";
        assert responseBody.containsKey("type") : "Response should contain 'type'";
        assert responseBody.containsKey("message") : "Response should contain 'message'";
        assert responseBody.keySet().equals(Set.of("code", "type", "message")) : "Unexpected fields found in response";
    }

    @Test(priority = 2)
    public void testGetUser() {
        Response response = UserEndpoints.getUser(this.userPayload.getUsername());

        response
        .then().log().all()
        .statusCode(200)
        .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("SchemaForGetUser.json"));

        JSONObject jsonObject = new JSONObject(response.asString());
        Assert.assertEquals(jsonObject.getInt("id"), this.userPayload.getId());
        Assert.assertEquals(jsonObject.getString("username"), this.userPayload.getUsername());
        Assert.assertEquals(jsonObject.getString("firstName"), this.userPayload.getFirstName());
        Assert.assertEquals(jsonObject.getString("lastName"), this.userPayload.getLastName());
        Assert.assertEquals(jsonObject.getString("email"), this.userPayload.getEmail());
        Assert.assertEquals(jsonObject.getString("password"), this.userPayload.getPassword());
        Assert.assertEquals(jsonObject.getString("phone"), this.userPayload.getPhone());
        Assert.assertEquals(jsonObject.getInt("userStatus"), this.userPayload.getUserStatus());

        assert response.statusCode() == 200 : "Expected status code to be 200"; // OR
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code to be 200"); // OR
    }

    @Test(priority = 3)
    public void testUpdateUser() {

        //update data
        userPayload.setUsername(faker.name().username());
        userPayload.setFirstName(faker.name().firstName());
        userPayload.setLastName(faker.name().lastName());
        userPayload.setEmail(faker.internet().safeEmailAddress());
        userPayload.setPassword(faker.internet().password(5,10));
        userPayload.setPhone(faker.phoneNumber().cellPhone());

        Response response = UserEndpoints.updateUser(this.userPayload.getUsername(), this.userPayload);

        response
        .then().log().all()
        .statusCode(200)
        .body("code", equalTo(200))
        .body("type", equalTo("unknown"))
        .body("message", instanceOf(String.class))
        .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("SchemaForPostPutDeleteUser.json"));

        Map<String, Object> responseBody = response.jsonPath().getMap("$"); // the $ symbol represents the root element of the JSON structure
        assert responseBody.containsKey("code") : "Response should contain 'code'";
        assert responseBody.containsKey("type") : "Response should contain 'type'";
        assert responseBody.containsKey("message") : "Response should contain 'message'";
        assert responseBody.keySet().equals(Set.of("code", "type", "message")) : "Unexpected fields found in response";

        // checking data after update
        testGetUser();
    }

    @Test(priority = 4)
    public void testDeleteUser() {
        Response response = UserEndpoints.deleteUser(this.userPayload.getUsername());

        response
        .then().log().all()
        .statusCode(200)
        .body("code", equalTo(200))
        .body("type", equalTo("unknown"))
        .body("message", equalTo(this.userPayload.getUsername()));

        Map<String, Object> responseBody = response.jsonPath().getMap("$"); // the $ symbol represents the root element of the JSON structure
        assert responseBody.containsKey("code") : "Response should contain 'code'";
        assert responseBody.containsKey("type") : "Response should contain 'type'";
        assert responseBody.containsKey("message") : "Response should contain 'message'";
        assert responseBody.keySet().equals(Set.of("code", "type", "message")) : "Unexpected fields found in response";

        assert response.statusCode() == 200 : "Expected status code to be 200"; // OR
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code to be 200"); // OR

        // checking data after user deleted
        Response testUserDeleted = UserEndpoints.getUser(this.userPayload.getUsername());
        testUserDeleted
        .then().log().all()
        .statusCode(404)
        .body("code", equalTo(1))
        .body("type", equalTo("error"))
        .body("message", equalTo("User not found"))
        .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("SchemaForPostPutDeleteUser.json"));
    }






}
