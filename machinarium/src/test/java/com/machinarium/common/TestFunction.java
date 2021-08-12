package com.machinarium.common;

public interface TestFunction<T> {

    /**
     * Executes a test on the given test object based on the input provided via a {@link TestCase}.
     *
     * @param testObject An instance of the class that is being tested.
     * @param testCase The data about the test case.
     */
    void execute(T testObject, TestCase testCase);
}
