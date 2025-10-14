package comp3350.fittrack.business.food.exceptions;

public class InvalidFoodNameException extends InvalidFoodException {

    public InvalidFoodNameException(String error) {
        super("The food name is invalid: \n" + error);
    }

}