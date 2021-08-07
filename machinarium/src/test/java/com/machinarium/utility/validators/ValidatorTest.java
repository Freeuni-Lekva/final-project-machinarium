package com.machinarium.utility.validators;

import com.machinarium.common.TestCase;
import com.machinarium.common.TestLogger;
import com.machinarium.common.TestData;
import com.machinarium.common.TestFunction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.*;

import static org.junit.jupiter.api.Assertions.*;

public class ValidatorTest {

    private static final String PASSWORD_VALIDATOR_TEST_DATA_PATH = "./src/test/resources/validators/PasswordValidatorTestData.csv";
    private static final String USERNAME_VALIDATOR_TEST_DATA_PATH = "./src/test/resources/validators/UsernameValidatorTestData.csv";

    private static final String INPUT = "Test Input";
    private static final String EXPECTED_RESULT = "Expected Result";
    private static final String REJECT_REASON = "Reject Reason";

    private static final String VALID = "VALID";
    private static final String INVALID = "INVALID";

    @BeforeEach
    void setUp() {
        validatorTestData = new HashMap<>();
        validatorTestData.put(PasswordValidator.getInstance(), TestData.loadTestData(PASSWORD_VALIDATOR_TEST_DATA_PATH));
        validatorTestData.put(UserNameValidator.getInstance(), TestData.loadTestData(USERNAME_VALIDATOR_TEST_DATA_PATH));
    }

    @Test
    void on() {

        this.testExecutor((validator, testCase) -> {

            String input = testCase.get(INPUT);
            String expectedResult = testCase.get(EXPECTED_RESULT);

            assert Set.of(VALID, INVALID).contains(expectedResult) :
                    "The value " + expectedResult + " in test case " + testCase.getID() + " is not a valid expected result.";

            if (expectedResult.equals(VALID)) assertNull(validator.on(input));
            else assertEquals(testCase.get(REJECT_REASON), validator.on(input));
        });
    }

    @Test
    void validate() {

        this.testExecutor((validator, testCase) -> {

            String input = testCase.get(INPUT);
            String expectedResult = testCase.get(EXPECTED_RESULT);

            assert Set.of(VALID, INVALID).contains(expectedResult) :
                    "The value " + expectedResult + " in test case " + testCase.getID() + " is not a valid expected result.";

            if (expectedResult.equals(VALID)) assertTrue(validator.validate(input));
            else assertFalse(validator.validate(input));
        });
    }

    /**
     * Executes the provided test function for instances of all classes implementing the {@link Validator} interface
     * for all test cases provided in their respective {@link TestData}.
     *
     * @param testFunction The {@link TestFunction} to be executed.
     */
    void testExecutor(TestFunction<Validator> testFunction) {

        for(Validator validator: validatorTestData.keySet()) {

            TestData testData = validatorTestData.get(validator);

            logger.log(Level.INFO, "Testing Validator Class: " + validator.getClass().toString());

            for (TestCase testCase : testData) {

                logger.log(Level.INFO, testCase.toString());
                testFunction.execute(validator, testCase);
            }
        }
    }

    private Map<Validator, TestData> validatorTestData;

    private static final Logger logger = TestLogger.getLogger("ValidatorTest");
}
