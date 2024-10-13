package api.endpoints;

public class Endpoints {

    static class User {
        public static final String USER = "/user";
        public static final String USER_NAME = "/user/{userName}";
    }

    static class Store {
        public static final String INVENTORY = "/store/inventory";
        public static final String ORDER = "/store/order";
        public static final String ORDER_ID = "/store/order/{orderId}";
    }
}
