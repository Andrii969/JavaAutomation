package api.endpoinds;

import api.payload.Order;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class StoreEndpoints {

    public static Response getInventory() {
        Response response =
        given()
        .when()
                .get(Routes.GET_STORE_INVENTORY_URL);

        return response;
    }

    public static Response placeOrder(Order payload) {
        Response response =
        given()
                .contentType("application/json")
                .accept("application/json")
                .body(payload)
        .when()
                .post(Routes.POST_STORE_ORDER_URL);

        return response;
    }

    public static Response placeOrder(Map<String, Object> payload) {
        Response response =
        given()
                .contentType("application/json")
                .accept("application/json")
                .body(payload)
        .when()
                .post(Routes.POST_STORE_ORDER_URL);

        return response;
    }

    public static Response getOrder(int orderId) {
        Response response =
        given()
                .pathParam("orderId", orderId)
        .when()
                .get(Routes.GET_STORE_ORDER_URL);

        return response;
    }

    public static Response deleteOrder(int orderId) {
        Response response =
        given()
                .pathParam("orderId", orderId)
        .when()
                .delete(Routes.DELETE_STORE_ORDER_URL);

        return response;
    }




}
