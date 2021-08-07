package com.machinarium.utility.validators;

import com.machinarium.common.TestCase;
import com.machinarium.common.TestData;
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

        for(Validator validator: validatorTestData.keySet()) {

            TestData testData = validatorTestData.get(validator);

            logger.log(Level.INFO, "Testing Validator Class: " + validator.getClass().toString());

            for (TestCase testCase : testData) {

                String input = testCase.get(INPUT);
                String expectedResult = testCase.get(EXPECTED_RESULT);

                assert Set.of(VALID, INVALID).contains(expectedResult) :
                        "The value " + expectedResult + " in test case " + testCase.getID() + " is not a valid expected result.";

                logger.log(Level.INFO, testCase.toString());

                if (expectedResult.equals(VALID)) assertNull(validator.on(input));
                else assertEquals(testCase.get(REJECT_REASON), validator.on(input));
            }
        }
    }

    @Test
    void validate() {

        for(Validator validator: validatorTestData.keySet()) {

            TestData testData = validatorTestData.get(validator);

            logger.log(Level.INFO, "Testing Validator Class: " + validator.getClass().toString());

            for (TestCase testCase : testData) {

                String input = testCase.get(INPUT);
                String expectedResult = testCase.get(EXPECTED_RESULT);

                assert Set.of(VALID, INVALID).contains(expectedResult) :
                        "The value " + expectedResult + " in test case " + testCase.getID() + " is not a valid expected result.";

                logger.log(Level.INFO, testCase.toString());

                if (expectedResult.equals(VALID)) assertTrue(validator.validate(input));
                else assertFalse(validator.validate(input));
            }
        }
    }

    private Map<Validator, TestData> validatorTestData;

    /**
     * Logger set up.
     */
    private static final Logger logger = Logger.getLogger("ValidatorTest");

    static {

        Handler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new Formatter() {

            private static final String formatString =
                    "%2$s Message in Test Method(%1$s):\n" +
                    "%3$s\n\n";

            @Override
            public String format(LogRecord record) {
                return String.format(formatString,
                        record.getSourceMethodName(),
                        record.getLevel().getLocalizedName(),
                        record.getMessage());
            }
        });

        logger.setUseParentHandlers(false);
        logger.addHandler(consoleHandler);
    }
}
