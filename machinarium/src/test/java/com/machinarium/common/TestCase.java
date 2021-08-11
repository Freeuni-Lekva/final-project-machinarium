package com.machinarium.common;

import java.util.Collections;
import java.util.Map;

import static com.machinarium.common.TestData.COLUMN_ID;

public class TestCase {

    /**
     * Returns the value for the specified field from the test case data.
     *
     * @param fieldName Name of the field.
     * @return Value of the specified field.
     *
     * @throws AssertionError if the specified field is not present.
     */
    public String get(String fieldName) {return this.data.get(checkField(fieldName));}

    /**
     * Returns the integer value for the specified field from the test case data.
     *
     * @param fieldName Name of the field.
     * @return Value of the specified field parsed as an int.
     *
     * @throws AssertionError if the specified field is not present.
     * @throws NumberFormatException if the value for the field can't be converted to an integer.
     */
    public int getInt(String fieldName) {return Integer.parseInt(this.get(fieldName));}

    /**
     * Returns the double value for the specified field from the test case data.
     *
     * @param fieldName Name of the field.
     * @return Value of the specified field parsed as a double.
     *
     * @throws AssertionError if the specified field is not present.
     * @throws NumberFormatException if the value for the field can't be converted to a double.
     */
    public double getDouble(String fieldName) {return Double.parseDouble(this.get(fieldName));}

    /**
     * @return The ID of the test case.
     */
    public String getID() {return this.data.get(COLUMN_ID);}

    @Override
    public String toString() {

        StringBuilder stringBuilder = new StringBuilder("Test Case (").append(this.getID()).append("): {\n");

        for(String field: data.keySet()) {

            if(!field.equals(COLUMN_ID)) {
                stringBuilder.append("\t").append(field).append(": \"").append(data.get(field)).append("\",\n");
            }
        }

        stringBuilder.append("}");

        return stringBuilder.toString();
    }

    TestCase(Map<String, String> data) {this.data = Collections.unmodifiableMap(data);}

    private String checkField(String fieldName) {

        assert this.data.containsKey(fieldName):
                "Test Case (" + this.getID() + ") does not have the data field " + fieldName + ".";

        return fieldName;
    }

    private final Map<String, String> data;
}
