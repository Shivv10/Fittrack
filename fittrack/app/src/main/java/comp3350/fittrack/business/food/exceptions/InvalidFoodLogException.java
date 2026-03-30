package comp3350.fittrack.business.food.exceptions;

public class InvalidFoodLogException extends RuntimeException {

    public InvalidFoodLogException(String error) {
        super("Invalid FoodLog:\n" + error);
    }
}
