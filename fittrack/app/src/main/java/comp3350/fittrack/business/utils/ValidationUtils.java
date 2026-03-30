/**
 **************************************************
 CLASS NAME: ValidationUtils.java
 CLASS FUNCTION: functions that are used to confirm validation
 **************************************************
 */
package comp3350.fittrack.business.utils;

import comp3350.fittrack.business.config.LiteralsConfig;
import comp3350.fittrack.business.exercise.exceptions.InvalidExerciseException;
import comp3350.fittrack.business.food.exceptions.InvalidFoodException;
import comp3350.fittrack.business.food.exceptions.InvalidFoodNameException;
import comp3350.fittrack.business.user.exceptions.InvalidInputException;

import java.util.function.Supplier;

/**
 * A utility class providing validation methods for user input.
 */
public class ValidationUtils {

    /**
     * Parses a string to a double and ensures it falls within a specified range.
     * @param value The string representation of the number.
     * @param fieldName The name of the field being validated.
     * @param min The minimum acceptable value.
     * @param max The maximum acceptable value.
     * @return The parsed double value if valid.
     * @throws InvalidInputException if the value is not a number or falls outside the range.
     */
    public static double parseDouble(String value, String fieldName, double min, double max) throws InvalidInputException {
        try {
            double parsedValue = Double.parseDouble(value);
            if (parsedValue < min || parsedValue > max) {
                throw new InvalidInputException(String.format(LiteralsConfig.ERROR_FIELD_RANGE, fieldName, min, max));
            }
            return parsedValue;
        } catch (NumberFormatException e) {
            throw new InvalidInputException(String.format(LiteralsConfig.ERROR_FIELD_NUMBER, fieldName));
        }
    }

    /**
     * Validates that a given string input is not empty.
     * @param value The input value to validate.
     * @param fieldName The name of the field being validated.
     * @return The trimmed input value if valid.
     * @throws InvalidInputException if the input is empty.
     */
    public static String validateInput(String value, String fieldName) throws InvalidInputException {
        switch (fieldName) {
            case LiteralsConfig.FIELD_NAME:
                return requireNonEmpty(value, () -> new InvalidInputException(LiteralsConfig.ERROR_USER_NO_NAME));
            case LiteralsConfig.FIELD_USERNAME:
                return requireNonEmpty(value, () -> new InvalidInputException(LiteralsConfig.ERROR_USER_NO_USERNAME));
            case LiteralsConfig.FIELD_PASSWORD:
                return requireNonEmpty(value, () -> new InvalidInputException(LiteralsConfig.ERROR_USER_NO_PASSWORD));
            default:
                return requireNonEmpty(value, () -> new InvalidInputException(LiteralsConfig.ERROR_INPUT_FIELD_PREFIX + fieldName));
        }
    }


    /**
     * Validates a password according to configured criteria.
     * @param value The password to validate.
     * @return The trimmed password if valid.
     * @throws InvalidInputException if the password does not meet the criteria.
     */
    public static String validatePassword(String value) throws InvalidInputException {
        // First, ensure the password is not empty.
        String trimmed = requireNonEmpty(value, () -> new InvalidInputException(LiteralsConfig.ERROR_USER_NO_PASSWORD));

        if (trimmed.length() < LiteralsConfig.MIN_PASSWORD_LENGTH || trimmed.length() > LiteralsConfig.MAX_PASSWORD_LENGTH) {
            throw new InvalidInputException(LiteralsConfig.ERROR_PASSWORD_LENGTH);
        }
        if (!trimmed.matches(LiteralsConfig.PASSWORD_LOWERCASE_REGEX)) {
            throw new InvalidInputException(LiteralsConfig.ERROR_PASSWORD_LOWERCASE);
        }
        if (!trimmed.matches(LiteralsConfig.PASSWORD_UPPERCASE_REGEX)) {
            throw new InvalidInputException(LiteralsConfig.ERROR_PASSWORD_UPPERCASE);
        }
        if (!trimmed.matches(LiteralsConfig.PASSWORD_NUMBER_REGEX)) {
            throw new InvalidInputException(LiteralsConfig.ERROR_PASSWORD_NUMBER);
        }
        if (!trimmed.matches(LiteralsConfig.PASSWORD_SPECIAL_REGEX)) {
            throw new InvalidInputException(LiteralsConfig.ERROR_PASSWORD_SPECIAL);
        }
        return trimmed;
    }

    /**
     * Checks whether an integer is within a specified range.
     * @param num The number to check.
     * @param hi The upper bound of the range (inclusive).
     * @param lo The lower bound of the range (inclusive).
     * @return True if the number is within the range.
     * @throws InvalidInputException if the number is outside the range.
     */
    public static boolean numInRange(int num, int hi, int lo) throws InvalidInputException {
        if (num <= hi && num >= lo) {
            return true;
        } else {
            throw new InvalidInputException(String.format(LiteralsConfig.ERROR_RANGE_VALUE, hi, lo));
        }
    }

    /**
     * Validates the food details.
     * @param name The food name.
     * @param calories The calorie count.
     * @throws InvalidFoodNameException if the name is empty or too long.
     * @throws InvalidFoodException if the calorie count is negative.
     */
    public static void validateFood(String name, int calories) {
        // Validate food name using the helper method.
        requireNonEmpty(name, () -> new InvalidFoodNameException(LiteralsConfig.ERROR_FOOD_NAME_EMPTY));
        if (name.length() > LiteralsConfig.MAX_FOOD_NAME_LENGTH) {
            throw new InvalidFoodNameException(LiteralsConfig.ERROR_FOOD_NAME_LENGTH);
        }
        // Validate calories
        if (calories < 0) {
            throw new InvalidFoodException(LiteralsConfig.ERROR_FOOD_CALORIES_NEGATIVE);
        }
    }

    /**
     * Validates the exercise parameters.
     * @param name The exercise name.
     * @param calories The calorie count for the exercise.
     * @throws InvalidExerciseException if the name is empty or calories are not positive.
     */
    public static void validateExerciseParams(String name, int calories) throws InvalidExerciseException {
        requireNonEmpty(name, () -> new InvalidExerciseException(LiteralsConfig.ERROR_EXERCISE_NAME_EMPTY));
        if (calories < LiteralsConfig.MIN_EXERCISE_CALORIES) {
            throw new InvalidExerciseException(LiteralsConfig.ERROR_EXERCISE_CALORIES_POSITIVE);
        }
    }

    /**
     * Validates that required inputs are provided.
     * @param name The name input.
     * @param caloriesStr The calories input as a string.
     * @throws IllegalArgumentException if any required field is missing.
     */
    public static void validateInputs(String name, String caloriesStr) {
        if (name.isEmpty() || caloriesStr.isEmpty()) {
            throw new IllegalArgumentException(LiteralsConfig.ERROR_ALL_FIELDS_REQUIRED);
        }
    }

    /**
     * A private helper method to ensure a string is non-null and non-empty after trimming.
     * @param value The string to check.
     * @param exceptionSupplier A supplier that returns the exception to be thrown if the check fails.
     * @param <T> The type of RuntimeException to be thrown.
     * @return The trimmed string if valid.
     * @throws T The exception provided by exceptionSupplier if the string is null or empty.
     */
    private static <T extends Exception> String requireNonEmpty(String value, Supplier<T> exceptionSupplier) throws T {
        if (value == null || value.trim().isEmpty()) {
            throw exceptionSupplier.get();
        }
        return value.trim();
    }

}
