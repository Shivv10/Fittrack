package comp3350.fittrack.business.food.exceptions;

public class FoodNotFoundException extends RuntimeException {

    public FoodNotFoundException(String error) {
        super("The food is not found:\n" + error);
    }
}
