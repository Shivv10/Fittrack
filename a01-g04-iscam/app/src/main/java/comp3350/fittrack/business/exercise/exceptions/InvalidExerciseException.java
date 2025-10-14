package comp3350.fittrack.business.exercise.exceptions;
public class InvalidExerciseException extends RuntimeException {

    public InvalidExerciseException(String error) {
        super("Unable to access Food data:\n" + error);
    }
}
