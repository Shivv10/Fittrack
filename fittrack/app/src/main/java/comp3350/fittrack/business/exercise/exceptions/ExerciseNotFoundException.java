package comp3350.fittrack.business.exercise.exceptions;
public class ExerciseNotFoundException extends RuntimeException {

    public ExerciseNotFoundException(String error) {
        super("The exercise is not found:\n" + error);
    }
}
