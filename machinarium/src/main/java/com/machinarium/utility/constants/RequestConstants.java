package com.machinarium.utility.constants;

/**
 * This class contains all constants related to requests sent to the servlets, such as:
 * 1. Request Parameter names.
 * 2. Context or Session related attribute names.
 */
public final class RequestConstants {

    public static final String PARAMETER_USER_NAME = "user_name";
    public static final String PARAMETER_PASSWORD = "password";
    public static final String PARAMETER_EMAIL = "email";

    public static final String ATTRIBUTE_USER_DAO = "user_dao";

    /* This class is meant for holding constants. */
    private RequestConstants() {}
}
