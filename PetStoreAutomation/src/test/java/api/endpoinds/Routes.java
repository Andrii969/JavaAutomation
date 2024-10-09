package api.endpoinds;

public class Routes {

    public static String BASE_URL = "https://petstore.swagger.io/v2";

    // User
    public static String POST_USER_URL = BASE_URL + "/user";
    public static String GET_USER_URL = BASE_URL + "/user/{userName}";
    public static String PUT_USER_URL = BASE_URL + "/user/{userName}";
    public static String DELETE_USER_URL = BASE_URL + "/user/{userName}";

    // Pet
    public static String POST_PET_URL = BASE_URL + "/pet";

    // Store
    public static String GET_STORE_INVENTORY_URL = BASE_URL + "/store/inventory";
    public static String POST_STORE_ORDER_URL = BASE_URL + "/store/order";
    public static String GET_STORE_ORDER_URL = BASE_URL + "/store/order/{orderId}";
    public static String DELETE_STORE_ORDER_URL = BASE_URL + "/store/order/{orderId}";

}
