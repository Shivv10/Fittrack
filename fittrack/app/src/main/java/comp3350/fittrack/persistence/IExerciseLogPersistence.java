package comp3350.fittrack.persistence;

import comp3350.fittrack.objects.ExerciseLog;

import java.util.List;

/** **************************************************
 CLASS NAME: IExerciseLogPersistence.java

 Interface for handling persistence of ExerciseLog objects.

 ************************************************** */
public interface IExerciseLogPersistence {
    // Add a new exercise log entry
    boolean addExerciseLog(ExerciseLog exerciseLog);

    // Update an existing exercise log
    boolean updateExerciseLog(int userID, int exerciseID, String date, ExerciseLog updatedLog);

    // Get all logs for a given user
    List<ExerciseLog> getExerciseLogsByUserId(int userId);

    // Delete a specific log
    boolean deleteExerciseLog(int userID, int exerciseID, String date);

    // Delete all logs tied to a specific exercise
    boolean deleteLogsByExerciseId(int exerciseId);
}
