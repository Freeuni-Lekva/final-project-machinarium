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

    public static final String PARAMETER_CARS = "cars";
    public static final String PARAMETER_SPARE_ITEMS = "spare_items";


    public static final String ATTRIBUTE_USER_DAO = "user_dao";
    public static final String ATTRIBUTE_USER = "user";


    /* This class is meant for holding constants. */
    private ServletConstants() {}
}
