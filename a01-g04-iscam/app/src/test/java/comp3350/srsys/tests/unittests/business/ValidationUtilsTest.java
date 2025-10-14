package comp3350.srsys.tests.unittests.business;

import comp3350.fittrack.business.utils.ValidationUtils;
import comp3350.fittrack.business.exercise.exceptions.InvalidExerciseException;
import comp3350.fittrack.business.food.exceptions.InvalidFoodException;
import comp3350.fittrack.business.food.exceptions.InvalidFoodNameException;
import comp3350.fittrack.business.user.exceptions.InvalidInputException;

import org.junit.Test;
import static org.junit.Assert.*;

public class ValidationUtilsTest {

    // Test parseDouble method
    @Test
    public void testParseDoubleValid() throws InvalidInputException {
        double result = ValidationUtils.parseDouble("10.5", "Test Field", 0, 20);
        assertEquals("The parsed value should be 10.5.", 10.5, result,0.000001d);
    }

    @Test
    public void testParseDoubleInvalidRange() {
        assertThrows(InvalidInputException.class, () -> {
            ValidationUtils.parseDouble("25.5", "Test Field", 0, 20);
        });
    }

    @Test
    public void testParseDoubleInvalidNumberFormat() {
        assertThrows(InvalidInputException.class, () -> {
            ValidationUtils.parseDouble("abc", "Test Field", 0, 20);
        });
    }

    // Test validateInput method
    @Test
    public void testValidateInputValid() throws InvalidInputException {
        String result = ValidationUtils.validateInput("Valid Input", "Test Field");
        assertEquals("Valid Input", result);
    }

    @Test
    public void testValidateInputInvalidEmpty() {
        assertThrows(InvalidInputException.class, () -> {
            ValidationUtils.validateInput("  ", "Test Field");
        });
    }

    // Test validatePassword method
    @Test
    public void testValidatePasswordValid() throws InvalidInputException {
        String result = ValidationUtils.validatePassword("Password1!");
        assertEquals("Password1!", result);
    }

    @Test
    public void testValidatePasswordInvalidTooShort() {
        assertThrows(InvalidInputException.class, () -> {
            ValidationUtils.validatePassword("Short1!");
        });
    }

    @Test
    public void testValidatePasswordInvalidNoLowercase() {
        assertThrows(InvalidInputException.class, () -> {
            ValidationUtils.validatePassword("NOLOWERCASE1!");
        });
    }

    @Test
    public void testValidatePasswordInvalidNoUppercase() {
        assertThrows(InvalidInputException.class, () -> {
            ValidationUtils.validatePassword("nouppercase1!");
        });
    }

    @Test
    public void testValidatePasswordInvalidNoNumber() {
        assertThrows(InvalidInputException.class, () -> {
            ValidationUtils.validatePassword("NoNumber!");
        });
    }

    @Test
    public void testValidatePasswordInvalidNoSpecialChar() {
        assertThrows(InvalidInputException.class, () -> {
            ValidationUtils.validatePassword("NoSpecial1");
        });
    }

    // Test numInRange method
    @Test
    public void testNumInRangeValid() throws InvalidInputException {
        boolean result = ValidationUtils.numInRange(5, 10, 0);
        assertTrue("The number should be in the range.", result);
    }

    @Test
    public void testNumInRangeInvalid() {
        assertThrows(InvalidInputException.class, () -> {
            ValidationUtils.numInRange(15, 10, 0);
        });
    }

    // Test validateFood method
    @Test
    public void testValidateFoodValid() {
        // Simply run the method and it will pass if no exception is thrown
        ValidationUtils.validateFood("Apple", 100);
    }

    @Test
    public void testValidateFoodInvalidNameEmpty() {
        assertThrows(InvalidFoodNameException.class, () -> {
            ValidationUtils.validateFood("", 100);
        });
    }

    @Test
    public void testValidateFoodInvalidNameTooLong() {
        assertThrows(InvalidFoodNameException.class, () -> {
            ValidationUtils.validateFood("ThisFoodNameIsWayTooLong", 100);
        });
    }

    @Test
    public void testValidateFoodInvalidCaloriesNegative() {
        assertThrows(InvalidFoodException.class, () -> {
            ValidationUtils.validateFood("Apple", -100);
        });
    }

    // Test validateExerciseParams method
    @Test
    public void testValidateExerciseParamsValid() throws InvalidExerciseException {
        // Simply run the method and it will pass if no exception is thrown
        ValidationUtils.validateExerciseParams("Running", 100);
    }

    @Test
    public void testValidateExerciseParamsInvalidNameEmpty() {
        assertThrows(InvalidExerciseException.class, () -> {
            ValidationUtils.validateExerciseParams("", 100);
        });
    }

    @Test
    public void testValidateExerciseParamsInvalidCaloriesNonPositive() {
        assertThrows(InvalidExerciseException.class, () -> {
            ValidationUtils.validateExerciseParams("Running", 0);
        });
    }

    // Test validateInputs method
    @Test
    public void testValidateInputsValid() {
        // Simply run the method and it will pass if no exception is thrown
        ValidationUtils.validateInputs("Running", "200");
    }

    @Test
    public void testValidateInputsInvalid() {
        assertThrows(IllegalArgumentException.class, () -> {
            ValidationUtils.validateInputs("Running", "");
        });
    }
}


