package comp3350.fittrack.business.food.exceptions;

public class InvalidFoodException extends RuntimeException {

    public InvalidFoodException(String error) {
        super("Unable to access Food data:\n" + error);
    }
}

