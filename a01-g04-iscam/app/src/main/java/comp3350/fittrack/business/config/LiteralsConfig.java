package comp3350.fittrack.business.config;

public class LiteralsConfig {
    // General messages
    public static final String ERROR_UNEXPECTED = "Unexpected error occurred";

    // Database related
    public static final String DB_ERROR_INIT = "Error initializing HSQLDB JDBC driver: ";

    // User related
    public static final String ERROR_USER_NO_NAME = "Please enter your name";
    public static final String ERROR_USER_NO_USERNAME = "Please enter your username";
    public static final String ERROR_USER_NO_PASSWORD = "Please enter your password";
    public static final String ERROR_PASSWORD_LENGTH = "Password must be between 8 and 20 characters long";
    public static final String ERROR_PASSWORD_LOWERCASE = "Password must contain at least one lowercase letter";
    public static final String ERROR_PASSWORD_UPPERCASE = "Password must contain at least one uppercase letter";
    public static final String ERROR_PASSWORD_NUMBER = "Password must contain at least one number";
    public static final String ERROR_PASSWORD_SPECIAL = "Password must contain at least one special character";
    public static final String ERROR_RANGE_VALUE = "Please select a value in range [%d-%d]";
    public static final String ERROR_FIELD_RANGE = "%s must be between %s and %s.";
    public static final String ERROR_FIELD_NUMBER = "Please enter a valid number for %s.";

    // Food related
    public static final String ERROR_FOOD_NAME_EMPTY = "Food name cannot be empty.";
    public static final String ERROR_FOOD_NAME_LENGTH = "The name should be no more than 20 characters.";
    public static final String ERROR_FOOD_CALORIES_NEGATIVE = "Calories cannot be negative.";
    public static final String ERROR_FOOD_DUPLICATE = "There exists duplicate food.";
    public static final String ERROR_FOOD_LOG_INVALID = "The food log has invalid userID or foodID or grams.";

    // Exercise related
    public static final String ERROR_EXERCISE_NAME_EMPTY = "Exercise name cannot be empty.";
    public static final String ERROR_EXERCISE_CALORIES_POSITIVE = "Calories must be positive.";
    public static final String ERROR_EXERCISE_ID_INVALID = "The exercise ID is not valid.";
    public static final String ERROR_EXERCISE_LOG_NULL = "Exercise log cannot be null.";
    public static final String ERROR_EXERCISE_LOG_UPDATED_NULL = "Updated exercise log cannot be null.";
    public static final String ERROR_EXERCISE_LOG_NOT_FOUND_UPDATE = "No exercise log found to update.";
    public static final String ERROR_EXERCISE_LOG_NOT_FOUND_DELETE = "No exercise log found to delete.";

    // Calorie related
    public static final String ERROR_CALORIE_GOAL_POSITIVE = "Calorie goal must be positive.";

    // Input validation
    public static final String ERROR_ALL_FIELDS_REQUIRED = "All fields are required.";
    public static final String ERROR_INPUT_FIELD_PREFIX = "Please enter your ";

    // Welcome message
    public static final String WELCOME_MESSAGE = "Welcome to FitTrack!";

    // Field identifiers (for use in validation)
    public static final String FIELD_NAME = "name";
    public static final String FIELD_USERNAME = "username";
    public static final String FIELD_PASSWORD = "password";

    // Validation constants (moved from ValidationUtils)
    public static final int MIN_PASSWORD_LENGTH = 8;
    public static final int MAX_PASSWORD_LENGTH = 20;
    public static final int MAX_FOOD_NAME_LENGTH = 20;
    // For exercises, calories must be positive; here we define the minimum as 1.
    public static final int MIN_EXERCISE_CALORIES = 1;

    // Password regex patterns
    public static final String PASSWORD_LOWERCASE_REGEX = ".*[a-z].*";
    public static final String PASSWORD_UPPERCASE_REGEX = ".*[A-Z].*";
    public static final String PASSWORD_NUMBER_REGEX = ".*[0-9].*";
    public static final String PASSWORD_SPECIAL_REGEX = ".*[!@#$%^&*].*";
}
