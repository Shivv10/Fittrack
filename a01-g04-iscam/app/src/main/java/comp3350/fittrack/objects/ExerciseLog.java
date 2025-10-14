package comp3350.fittrack.objects;

/** **************************************************
 CLASS NAME: ExerciseLog.java

 CLASS FUNCTION: Represents a log entry of an exercise performed by a user.

 Stores user ID, exercise ID, duration in minutes, and the date performed.

 ************************************************** */

public class ExerciseLog {
    private final int userId;
    private final int exerciseId;
    private final int durationMinutes;
    private final String datePerformed;

    public ExerciseLog(int userId, int exerciseId, int durationMinutes, String datePerformed) {
        this.userId = userId;
        this.exerciseId = exerciseId;
        this.durationMinutes = durationMinutes;
        this.datePerformed = datePerformed;
    }

    public int getUserId() { return userId; }

    public int getExerciseId() { return exerciseId; }

    public int getDurationMinutes() { return durationMinutes; }

    public String getDatePerformed() { return datePerformed; }

}
