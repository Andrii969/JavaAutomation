package api.endpoinds;

public class Endpoints {

    public static String BASE_URL = "https://petstore.swagger.io/v2";

    static class User {
        public static final String USER = BASE_URL + "/user";
        public static final String USER_NAME = BASE_URL + "/user/{userName}";
    }

    static class Store {
        public static final String INVENTORY = BASE_URL + "/store/inventory";
        public static final String ORDER = BASE_URL + "/store/order";
        public static final String ORDER_ID = BASE_URL + "/store/order/{orderId}";
    }
}
