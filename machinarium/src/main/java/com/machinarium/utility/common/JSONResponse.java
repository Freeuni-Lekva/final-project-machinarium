package com.machinarium.utility.common;

import org.json.simple.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JSONResponse extends HttpServletResponseWrapper {

    private final static Logger logger = ConfiguredLogger.getLogger("JSONResponse");

    public static final String DATA_FIELD_MESSAGE = "message";

    /**
     * Creates a {@link JSONResponse} object from the specified {@link HttpServletResponse}. Objects
     * of this class offer an additional method to add JSON contents to the body of the response.
     *
     * @param response The {@link HttpServletResponse}.
     */
    public JSONResponse(HttpServletResponse response) {
        super(response);
    }

    /**
     * Appends the specified data to end of the {@link JSONResponse} body.
     *
     * @param data The data to be appendes as a {@link JSONObject} object.
     */
    public void setBody(JSONObject data) {

        String dataString = data.toJSONString();
        logger.log(Level.FINE, "Data appended to the response body: " + dataString);

        try {
            this.getWriter().println(dataString);
        } catch (IOException e) {e.printStackTrace();}
    }

    public void setResponse(int status, JSONObject data) {

        this.setStatus(status);
        this.setBody(data);
    }

    public void setError(int status, String message) {

        this.setStatus(status);

        JSONObject data = new JSONObject();
        data.put(DATA_FIELD_MESSAGE, message);

        this.setBody(data);
    }
}
