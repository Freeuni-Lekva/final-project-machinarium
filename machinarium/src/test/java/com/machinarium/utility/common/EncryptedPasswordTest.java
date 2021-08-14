package com.machinarium.utility.common;

import com.machinarium.common.TestCase;
import com.machinarium.common.TestData;
import com.machinarium.common.TestFunction;
import com.machinarium.common.TestLogger;
import com.machinarium.utility.validators.PasswordValidator;
import com.machinarium.utility.validators.UserNameValidator;
import com.machinarium.utility.validators.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.Assert.*;

public class EncryptedPasswordTest {

    private static final String TEST_DATA_PATH = "./src/test/resources/com/machinarium/utility/common/EncryptedPasswordTestData.csv";

    private static final String INPUT = "Input";
    private static final String ENCRYPTED = "Encrypted";

    @BeforeEach
    void setUp() {testData = TestData.loadTestData(TEST_DATA_PATH);}

    @Test
    public void of() {

        testExecutor((unused, testCase) -> {
            assertEquals(testCase.get(ENCRYPTED), EncryptedPassword.of(testCase.get(INPUT)).toString());
        });
    }

    @Test
    public void from() {

        testExecutor((unused, testCase) -> {
            assertEquals(testCase.get(ENCRYPTED), EncryptedPassword.from(testCase.get(ENCRYPTED)).toString());
        });
    }


    /**
     * Executes the provided test function for instances of all classes implementing the {@link Validator} interface
     * for all test cases provided in their respective {@link TestData}.
     *
     * @param testFunction The {@link TestFunction} to be executed.
     */
    void testExecutor(TestFunction<EncryptedPassword> testFunction) {

        for (TestCase testCase : testData) {

            logger.log(Level.INFO, testCase.toString());
            testFunction.execute(null, testCase);
        }
    }

    private TestData testData;

    private static final Logger logger = TestLogger.getLogger("ValidatorTest");
}