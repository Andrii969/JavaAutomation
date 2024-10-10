package api.test;

import api.endpoinds.StoreEndpoints;
import api.payload.Order;
import com.github.javafaker.Faker;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Set;

import static org.hamcrest.Matchers.equalTo;

public class StoreTests {

    Faker faker;
    Order orderPayload;

    public Logger logger;

    @BeforeClass
    public void setup() {
        faker = new Faker();
        orderPayload = new Order();

        orderPayload.setId(faker.idNumber().hashCode());
        orderPayload.setQuantity(faker.number().numberBetween(1, 10));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        OffsetDateTime shipDate = OffsetDateTime.now(ZoneOffset.UTC);
        String formattedDate = shipDate.format(formatter);

        orderPayload.setShipDate(formattedDate);

        logger = LogManager.getLogger(this.getClass()); // logs
    }

    @Test(priority = 1)
    public void testGetInventory() {
        logger.info("Sending GET request to fetch inventory.");
        Response response = StoreEndpoints.getInventory();

        response
        .then().log().all()
        .statusCode(200);
    }

    @Test(priority = 2)
    public void testPlaceOrderWithValidData() {
        logger.info("Placing an order with valid data");
        Response response = StoreEndpoints.placeOrder(this.orderPayload);

        response
        .then().log().all()
        .statusCode(200)
        .body("id", equalTo(this.orderPayload.getId()))
        .body("petId", equalTo(this.orderPayload.getPetId()))
        .body("quantity", equalTo(this.orderPayload.getQuantity()))
        .body("shipDate", equalTo(this.orderPayload.getShipDate()))
        .body("status", equalTo(this.orderPayload.getStatus()))
        .body("complete", equalTo(this.orderPayload.isComplete()))
        .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("SchemaForPostGetStore.json"));

        Map<String, Object> responseBody = response.jsonPath().getMap("$"); // the $ symbol represents the root element of the JSON structure
        assert responseBody.keySet().equals(Set.of("id", "petId", "quantity", "shipDate", "status", "complete"))
                : "Unexpected fields found in response";
    }

    @Test(priority = 3)
    public void testGetExistingOrder() {
        logger.info("Fetching an existing order with ID: {}", orderPayload.getId());
        Response response = StoreEndpoints.getOrder(this.orderPayload.getId());

        response
        .then().log().all()
        .statusCode(200)
        .body("id", equalTo(this.orderPayload.getId()))
        .body("petId", equalTo(this.orderPayload.getPetId()))
        .body("quantity", equalTo(this.orderPayload.getQuantity()))
        .body("shipDate", equalTo(this.orderPayload.getShipDate()))
        .body("status", equalTo(this.orderPayload.getStatus()))
        .body("complete", equalTo(this.orderPayload.isComplete()))
        .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("SchemaForPostGetStore.json"));


        Map<String, Object> responseBody = response.jsonPath().getMap("$"); // the $ symbol represents the root element of the JSON structure
        assert responseBody.keySet().equals(Set.of("id", "petId", "quantity", "shipDate", "status", "complete"))
                : "Unexpected fields found in response";
    }

    @Test(priority = 4)
    public void testDeleteExistingOrder() {
        logger.info("Deleting the order with ID: {}", orderPayload.getId());
        Response response = StoreEndpoints.deleteOrder(this.orderPayload.getId());

        response
        .then().log().all()
        .statusCode(200)
        .body("code", equalTo(200))
        .body("type", equalTo("unknown"))
        .body("message", equalTo(String.valueOf(this.orderPayload.getId())))
        .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("SchemaForDeleteSuccessOrErrorsOrder.json"));

        Map<String, Object> responseBody = response.jsonPath().getMap("$"); // the $ symbol represents the root element of the JSON structure
        assert responseBody.containsKey("code") : "Response should contain 'code'";
        assert responseBody.containsKey("type") : "Response should contain 'type'";
        assert responseBody.containsKey("message") : "Response should contain 'message'";
        assert responseBody.keySet().equals(Set.of("code", "type", "message")) : "Unexpected fields found in response";

        logger.info("Fetching an already deleted order with ID: {}", orderPayload.getId());
        Response testOrderDeleted = StoreEndpoints.getOrder(this.orderPayload.getId());

        testOrderDeleted
        .then().log().all()
        .statusCode(404)
        .body("code", equalTo(1))
        .body("type", equalTo("error"))
        .body("message", equalTo("Order not found"));
    }

    @Test(priority = 5)
    public void testDeleteOrderAfterItIsDeleted() {
        logger.info("Trying to delete an already deleted order with ID: {}", orderPayload.getId());
        Response response = StoreEndpoints.deleteOrder(this.orderPayload.getId());

        response
        .then().log().all()
        .statusCode(404)
        .body("code", equalTo(404))
        .body("type", equalTo("unknown"))
        .body("message", equalTo("Order Not Found"))
        .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("SchemaForDeleteSuccessOrErrorsOrder.json"));

        Map<String, Object> responseBody = response.jsonPath().getMap("$");
        assert responseBody.keySet().equals(Set.of("code", "type", "message")) : "Unexpected fields found in response";
    }

    @Test(priority = 6)
    public void testPlaceOrderWithInvalidIdField() {
        logger.info("Placing an order with an invalid ID: 'invalid-id'");
        Map<String, Object> invalidOrderPayload = Map.of(
                "id", "invalid-id",
                "petId", orderPayload.getPetId(),
                "quantity", orderPayload.getQuantity(),
                "shipDate", orderPayload.getShipDate(),
                "status", orderPayload.getStatus(),
                "complete", orderPayload.isComplete()
        );

        Response response = StoreEndpoints.placeOrder(invalidOrderPayload);

        response
        .then().log().all()
        .statusCode(500)
        .body("code", equalTo(500))
        .body("type", equalTo("unknown"))
        .body("message", equalTo("something bad happened"))
        .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("SchemaForDeleteSuccessOrErrorsOrder.json"));

        Map<String, Object> responseBody = response.jsonPath().getMap("$");
        assert responseBody.keySet().equals(Set.of("code", "type", "message")) : "Unexpected fields found in response";
    }

    @Test(priority = 7)
    public void testPlaceOrderWithInvalidQuantityField() {
        logger.info("Placing an order with invalid quantity: 'invalid-quantity'");
        Map<String, Object> invalidOrderPayload = Map.of(
                "id", orderPayload.getId(),
                "petId", orderPayload.getPetId(),
                "quantity", "invalid-quantity",
                "shipDate", orderPayload.getShipDate(),
                "status", orderPayload.getStatus(),
                "complete", orderPayload.isComplete()
        );

        Response response = StoreEndpoints.placeOrder(invalidOrderPayload);

        response
        .then().log().all()
        .statusCode(500)
        .body("code", equalTo(500))
        .body("type", equalTo("unknown"))
        .body("message", equalTo("something bad happened"))
        .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("SchemaForDeleteSuccessOrErrorsOrder.json"));

        Map<String, Object> responseBody = response.jsonPath().getMap("$");
        assert responseBody.keySet().equals(Set.of("code", "type", "message")) : "Unexpected fields found in response";
    }

    @Test(priority = 8)
    public void testPlaceOrderWithInvalidShipDateField() {
        logger.info("Placing an order with invalid ship date: 'invalid-shipDate'");
        Map<String, Object> invalidOrderPayload = Map.of(
                "id", orderPayload.getId(),
                "petId", orderPayload.getPetId(),
                "quantity", orderPayload.getShipDate(),
                "shipDate", "invalid-shipDate",
                "status", orderPayload.getStatus(),
                "complete", orderPayload.isComplete()
        );

        Response response = StoreEndpoints.placeOrder(invalidOrderPayload);

        response
        .then().log().all()
        .statusCode(500)
        .body("code", equalTo(500))
        .body("type", equalTo("unknown"))
        .body("message", equalTo("something bad happened"))
        .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("SchemaForDeleteSuccessOrErrorsOrder.json"));

        Map<String, Object> responseBody = response.jsonPath().getMap("$");
        assert responseBody.keySet().equals(Set.of("code", "type", "message")) : "Unexpected fields found in response";
    }

    @Test(priority = 9)
    public void testPlaceOrderWithInvalidCompleteField() {
        logger.info("Placing an order with invalid complete field: 'invalid-complete'");
        Map<String, Object> invalidOrderPayload = Map.of(
                "id", orderPayload.getId(),
                "petId", orderPayload.getPetId(),
                "quantity", orderPayload.getShipDate(),
                "shipDate", orderPayload.getShipDate(),
                "status", orderPayload.getStatus(),
                "complete", "invalid-complete"
        );

        Response response = StoreEndpoints.placeOrder(invalidOrderPayload);

        response
        .then().log().all()
        .statusCode(500)
        .body("code", equalTo(500))
        .body("type", equalTo("unknown"))
        .body("message", equalTo("something bad happened"))
        .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("SchemaForDeleteSuccessOrErrorsOrder.json"));

        Map<String, Object> responseBody = response.jsonPath().getMap("$");
        assert responseBody.keySet().equals(Set.of("code", "type", "message")) : "Unexpected fields found in response";
    }

}