package comp3350.fittrack.business.food.exceptions;

public class FoodLogNotFoundException extends RuntimeException {

    public FoodLogNotFoundException(String error) {
        super("The food log is not found:\n" + error);
    }
}
