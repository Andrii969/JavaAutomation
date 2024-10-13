package api.endpoints;

import api.payload.Order;
import io.restassured.response.Response;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import static api.endpoints.Endpoints.Store.*;
import static api.test.BaseTest.FAKER;
import static io.restassured.http.ContentType.JSON;

public class StoreService extends ApiService{

    public Response getInventory() {
        return setUp()
                .when()
                .get(INVENTORY);
    }

    public Response placeOrder(Order payload) {
        return setUp()
                .contentType(JSON)
                .body(payload)
                .when()
                .post(ORDER);
    }

    public Response getOrder(Object orderId) {
        return setUp()
                .pathParam("orderId", orderId)
                .when()
                .get(ORDER_ID);
    }

    public Response deleteOrder(Object orderId) {
        return setUp()
                .pathParam("orderId", orderId)
                .when()
                .delete(ORDER_ID);
    }

    public Order getValidModel() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        OffsetDateTime shipDate = OffsetDateTime.now(ZoneOffset.UTC);
        String formattedDate = shipDate.format(formatter);

        return new Order()
        .setId(FAKER.idNumber().hashCode())
        .setPetId(33497253)
        .setQuantity(FAKER.number().numberBetween(1, 10))
        .setShipDate(formattedDate)
        .setStatus("placed")
        .setComplete(true);
    }

    public Order getOrderFromResponse(Response response) {
        return response.as(Order.class);
    }
}