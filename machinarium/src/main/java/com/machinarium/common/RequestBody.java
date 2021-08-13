package com.machinarium.common;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.stream.Collectors;

public final class RequestBody {

    /**
     * Returns the body contents of the specified {@link HttpServletRequest} as a
     * map.
     *
     * @param request The request to parse the body contents from
     * @return The body contents as a {@link JSONObject} object.
     */
    public static JSONObject parse(HttpServletRequest request) {

        try {

            String body = request.getReader().lines().collect(Collectors.joining(" "));
            System.out.println("Body: " + body);

            return (JSONObject) new JSONParser().parse(body);

        } catch (IOException | ParseException e) {e.printStackTrace();}

        return null;
    }

    /**
     * Converts the specified JSON contents to a string.
     *
     * @param data The data as a {@link JSONObject}.
     * @return A stringified version of the JSON contents.
     *
     */
    public static String Build(JSONObject data) {
        return data.toJSONString();
    }

    /* You should not create an instance of this class. */
    private RequestBody() {}
}
