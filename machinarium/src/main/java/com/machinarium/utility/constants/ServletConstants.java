package com.machinarium.utility.constants;

/**
 * This class contains all constants related to requests sent to the servlets, such as:
 * 1. Request Parameter names.
 * 2. Context or Session related attribute names.
 */
public final class ServletConstants {

    public static final String PARAMETER_USER_NAME = "user_name";
    public static final String PARAMETER_PASSWORD = "password";
    public static final String PARAMETER_EMAIL = "email";

    public static final String PARAMETER_FIRST_PLACE_COUNT = "first_place_count";
    public static final String PARAMETER_SECOND_PLACE_COUNT = "second_place_count";
    public static final String PARAMETER_THIRD_PLACE_COUNT = "third_place_count";
    public static final String PARAMETER_LOSS_COUNT = "loss_count";

    public static final String PARAMETER_STATUS = "status";
    public static final String PARAMETER_USERS = "users";
    public static final String PARAMETER_ROLE = "role";

    public static final String PARAMETER_CARS = "cars";
    public static final String PARAMETER_ITEMS = "items";

    public static final String PARAMETER_CAR_NAME = "car_name";

    public static final String PARAMETER_USER_ORDERS = "user_orders";
    public static final String PARAMETER_ORDERS = "orders";

    public static final String ATTRIBUTE_USER_DAO = "user_dao";
    public static final String ATTRIBUTE_USER = "user";
    public static final String ATTRIBUTE_GARAGE_DAO = "garage_dao";
    public static final String ATTRIBUTE_STATISTICS_DAO = "statistics_dao";
    public static final String ATTRIBUTE_ORDER_DAO = "order_dao";
    public static final String ATTRIBUTE_GAME_DAO = "game_dao";


    public static final String VALUE_HOST = "host";
    public static final String VALUE_GUEST = "guest";

    /* This class is meant for holding constants. */
    private ServletConstants() {}
}
