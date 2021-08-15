package com.machinarium.utility.common;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class JSONRequest extends HttpServletRequestWrapper {

    private final static Logger logger = ConfiguredLogger.getLogger("JSONRequest");

    /**
     * Creates a {@link JSONRequest} object from the specified {@link HttpServletRequest}. Objects
     * of this class offer an additional method to retrieve the body of the request as a {@link JSONObject}.
     *
     * @param request The {@link HttpServletRequest} with a body in JSON format.
     */
    public JSONRequest(HttpServletRequest request) {

        super(request);
        body = JSONRequest.parse(request);
    }

    /**
     * Returns the body contents of this {@link JSONRequest}.
     *
     * @return The body contents as a {@link JSONObject} object.
     */
    public JSONObject getBody() {return this.body;}

    /**
     * Returns the body contents of this {@link HttpServletRequest}.
     *
     * @param request The request to parse the body contents from
     * @return The body contents as a {@link JSONObject} object.
     */
    public static JSONObject parse(HttpServletRequest request) {

        try {

            String body = request.getReader().lines().collect(Collectors.joining(" "));
            logger.log(Level.FINE, "Received request body: " + body);

            return (JSONObject) new JSONParser().parse(body);

        } catch (IOException | ParseException e) {e.printStackTrace();}

        return null;
    }

    private final JSONObject body;
}
