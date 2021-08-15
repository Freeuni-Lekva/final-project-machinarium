package com.machinarium.model;

import org.json.simple.JSONObject;

import java.util.Map;

public interface JSONData {

    /**
     * Returns a Map representation of this object which can later be converted
     * to {@link JSONObject}.
     *
     * @return The object data as a {@link Map<String, Object>}.
     */
    Map<String, Object> toJSONMap();
}
