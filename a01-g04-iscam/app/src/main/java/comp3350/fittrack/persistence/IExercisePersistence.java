package comp3350.fittrack.persistence;

import java.util.List;
import comp3350.fittrack.objects.Exercise;

/** **************************************************
 CLASS NAME: IExercisePersistence.java

 Interface for handling persistence of Exercise objects.

 ************************************************** */
public interface IExercisePersistence {
    // Get list of all exercises
    List<Exercise> getExerciseList();

    // Get exercise by ID
    Exercise getExerciseById(final int exerciseID);

    // Get exercise by name
    Exercise getExerciseByName(final String exerciseName);

    // Insert a new exercise
    Exercise insertExercise(Exercise exercise);

    // Update an exercise
    void updateExercise(int exerciseID, Exercise updatedExercise);

    // Delete an exercise
    void deleteExercise(final int exerciseID);
}
