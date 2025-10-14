package comp3350.fittrack.business.exercise;

import comp3350.fittrack.application.Services;
import comp3350.fittrack.business.config.LiteralsConfig;
import comp3350.fittrack.business.exercise.exceptions.ExerciseNotFoundException;
import comp3350.fittrack.business.exercise.exceptions.InvalidExerciseException;
import comp3350.fittrack.objects.Exercise;
import comp3350.fittrack.objects.ExerciseLog;
import comp3350.fittrack.persistence.IExerciseLogPersistence;
import comp3350.fittrack.persistence.IExercisePersistence;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/** **************************************************
 CLASS NAME: AccessExerciseLogs.java

 Handles logic for logging and managing user exercise data.

 Interfaces with persistence and provides calorie tracking logic.

 ************************************************** */

public class AccessExerciseLogs {

    private static final Logger logger = Logger.getLogger(AccessExerciseLogs.class.getName());
    private final IExerciseLogPersistence exerciseLogDAO;
    private final IExercisePersistence exercisePersistence;

    public AccessExerciseLogs() {
        this.exerciseLogDAO = Services.getExerciseLogPersistence(); // No context needed
        this.exercisePersistence = Services.getExercisePersistence(); // No context needed
    }

    //constructor for testing
    public AccessExerciseLogs(IExerciseLogPersistence exerciseLogDAO, IExercisePersistence exercisePersistence) {
        this.exerciseLogDAO = exerciseLogDAO;
        this.exercisePersistence = exercisePersistence;
    }

    /* **************************************************
     FUNCTION PURPOSE: return the exerciseLog for a given user, exercise and date
     ************************************************** */
    public ExerciseLog getExerciseLog(int userID, int exerciseID, String date) {
        return exerciseLogDAO.getExerciseLogsByUserId(userID)
                .stream()
                .filter(log -> log.getExerciseId() == exerciseID && log.getDatePerformed().equals(date))
                .findFirst()
                .orElse(null);
    }

    //returns a UserID's exerciseLog list
    public List<ExerciseLog> getExerciseLogByUser(int userID) {
        return exerciseLogDAO.getExerciseLogsByUserId(userID);
    }

    //inserts an exerciseLog into the current persistence
    public void insertExerciseLog(ExerciseLog exerciseLog) throws InvalidExerciseException {
        if (exerciseLog == null) {
            throw new InvalidExerciseException(LiteralsConfig.ERROR_EXERCISE_LOG_NULL);
        }
        exerciseLogDAO.addExerciseLog(exerciseLog);
    }

    /* **************************************************
     FUNCTION PURPOSE: Update the persistence with the data given
     ************************************************** */
    public void updateExerciseLog(int userID, int exerciseID, String date, ExerciseLog updatedLog)
            throws InvalidExerciseException, ExerciseNotFoundException {
        if (updatedLog == null) {
            throw new InvalidExerciseException(LiteralsConfig.ERROR_EXERCISE_LOG_UPDATED_NULL);
        }
        if (getExerciseLog(userID, exerciseID, date) == null) {
            throw new ExerciseNotFoundException(LiteralsConfig.ERROR_EXERCISE_LOG_NOT_FOUND_UPDATE);
        }
        exerciseLogDAO.updateExerciseLog(userID, exerciseID, date, updatedLog);
    }

    public void deleteExerciseLog(int userID, int exerciseID, String date) throws ExerciseNotFoundException {
        if (getExerciseLog(userID, exerciseID, date) == null) {
            throw new ExerciseNotFoundException(LiteralsConfig.ERROR_EXERCISE_LOG_NOT_FOUND_DELETE);
        }
        exerciseLogDAO.deleteExerciseLog(userID, exerciseID, date);
    }

    /* *************************************************
     FUNCTION PURPOSE: gets how many calories a burned ate on a given date

     OUTPUT: returns an int with how many calories the user burned
     ************************************************** */
    public int getUserTotalDailyBurned(int userID, String date) {
        List<ExerciseLog> logs = getExerciseLogByUser(userID);
        logs = logs.stream().filter(log -> log.getDatePerformed().equals(date)).collect(Collectors.toList());

        logger.info("Retrieved " + logs.size() + " exercise logs for userId: " + userID + ", date: " + date);

        int total = 0;
        for (ExerciseLog log : logs) {
            int caloriesPerMinute = getCaloriesPerMinute(log.getExerciseId());
            int caloriesBurned = log.getDurationMinutes() * caloriesPerMinute;
            logger.info("Exercise: " + log.getExerciseId() + ", Duration: " + log.getDurationMinutes() +
                    ", Calories per min: " + caloriesPerMinute + ", Total burned: " + caloriesBurned);
            total += caloriesBurned;
        }
        return total;
    }

    private int getCaloriesPerMinute(int exerciseID) {
        Exercise exercise = exercisePersistence.getExerciseById(exerciseID); // No context needed
        return (exercise != null) ? exercise.getCaloriesBurned() : 0;
    }

    public void deleteAllLogsForExercise(int exerciseId) {
        exerciseLogDAO.deleteLogsByExerciseId(exerciseId);
    }
}
