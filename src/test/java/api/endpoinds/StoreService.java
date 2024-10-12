package api.endpoinds;

import api.payload.Order;
import io.restassured.response.Response;

import java.util.Map;

import static api.endpoinds.Endpoints.Store.*;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class StoreService extends ApiService{

    public static Response getInventory() {
        return given()
                .when()
                .get(INVENTORY);
    }

    public static Response placeOrder(Order payload) {
        return setUp()
                .body(payload)
                .when()
                .post(ORDER);
    }

    public static Response placeOrder(Map<String, Object> payload) {
        return setUp()
                .body(payload)
                .when()
                .post(ORDER);
    }

    public static Response getOrder(int orderId) {
        return given()
                .pathParam("orderId", orderId)
                .when()
                .get(ORDER_ID);
    }

    public static Response deleteOrder(int orderId) {
        return given()
                .pathParam("orderId", orderId)
                .when()
                .delete(ORDER_ID);
    }
}