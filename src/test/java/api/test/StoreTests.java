package api.test;

import api.endpoints.StoreService;
import api.payload.Order;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;

public class StoreTests extends BaseTest {
    private static final String POST_GET_ORDER_SCHEMA = "SchemaForPostGetStore.json";
    private static final String DELETE_ORDER_OR_ERROR_SCHEMA = "SchemaForDeleteSuccessOrErrorsOrder.json";

    StoreService storeService = new StoreService();

    private void cleanupOrder(Object orderId) {
        storeService.deleteOrder(orderId);
    }

    @Test
    public void testGetInventory() {
        Response response = storeService.getInventory();
        validateStatusCode(response, HttpStatus.SC_OK);
    }

    @Test
    public void testPlaceOrderWithValidData() {
        Order orderPayload = storeService.getValidModel();
        Response response = storeService.placeOrder(orderPayload);

        validateStatusCode(response, HttpStatus.SC_OK);
        validateSchema(response, POST_GET_ORDER_SCHEMA);

        Order responseOrder = storeService.getOrderFromResponse(response);
        assertEquals(responseOrder, orderPayload, "The retrieved order should match the created order");

        cleanupOrder(orderPayload.getId());
    }

    @Test
    public void testGetExistingOrder() {
        Order orderPayload = storeService.getValidModel();
        storeService.placeOrder(orderPayload);
        Response response = storeService.getOrder(orderPayload.getId());

        validateStatusCode(response, HttpStatus.SC_OK);
        validateSchema(response, POST_GET_ORDER_SCHEMA);

        Order responseOrder = storeService.getOrderFromResponse(response);
        assertEquals(responseOrder, orderPayload, "The retrieved order should match the created order");

        cleanupOrder(orderPayload.getId());
    }

    @Test
    public void testDeleteExistingOrder() {
        Order orderPayload = storeService.getValidModel();
        storeService.placeOrder(orderPayload);

        Response response = storeService.deleteOrder(orderPayload.getId());

        validateStatusCode(response, HttpStatus.SC_OK);
        validateSchema(response, DELETE_ORDER_OR_ERROR_SCHEMA);

        Response getOrderResponse = storeService.getOrder(orderPayload.getId());

        validateStatusCode(getOrderResponse, HttpStatus.SC_NOT_FOUND);
        validateSchema(getOrderResponse, DELETE_ORDER_OR_ERROR_SCHEMA);
    }

    @Test
    public void testDeleteOrderAfterItIsDeleted() {
        Order orderPayload = storeService.getValidModel();
        storeService.placeOrder(orderPayload);
        storeService.deleteOrder(orderPayload.getId());

        Response response = storeService.deleteOrder(orderPayload.getId());

        validateStatusCode(response, HttpStatus.SC_NOT_FOUND);
        validateSchema(response, DELETE_ORDER_OR_ERROR_SCHEMA);
    }

    @Test
    public void testPlaceOrderWithInvalidIdField() {
        Order orderPayload = storeService.getValidModel();
        orderPayload.setId("invalid-id");
        Response response = storeService.placeOrder(orderPayload);

        validateStatusCode(response, HttpStatus.SC_INTERNAL_SERVER_ERROR);
        validateSchema(response, DELETE_ORDER_OR_ERROR_SCHEMA);
    }

    @Test(priority = 7)
    public void testPlaceOrderWithInvalidQuantityField() {
        Order orderPayload = storeService.getValidModel();
        orderPayload.setQuantity("invalid-quantity");
        Response response = storeService.placeOrder(orderPayload);

        validateStatusCode(response, HttpStatus.SC_INTERNAL_SERVER_ERROR);
        validateSchema(response, DELETE_ORDER_OR_ERROR_SCHEMA);
    }

    @Test
    public void testPlaceOrderWithInvalidShipDateField() {
        Order orderPayload = storeService.getValidModel();
        orderPayload.setShipDate("invalid-shipDate");
        Response response = storeService.placeOrder(orderPayload);

        validateStatusCode(response, HttpStatus.SC_INTERNAL_SERVER_ERROR);
        validateSchema(response, DELETE_ORDER_OR_ERROR_SCHEMA);
    }

    @Test(priority = 9)
    public void testPlaceOrderWithInvalidCompleteField() {
        Order orderPayload = storeService.getValidModel();
        orderPayload.setComplete("invalid-complete");
        Response response = storeService.placeOrder(orderPayload);

        validateStatusCode(response, HttpStatus.SC_INTERNAL_SERVER_ERROR);
        validateSchema(response, DELETE_ORDER_OR_ERROR_SCHEMA);
    }
}