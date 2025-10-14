package comp3350.fittrack.business.user;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import comp3350.fittrack.application.Services;
import comp3350.fittrack.business.config.LiteralsConfig;
import comp3350.fittrack.business.exercise.AccessExerciseLogs;
import comp3350.fittrack.business.food.AccessFoodLogs;
import comp3350.fittrack.objects.ExerciseLog;

/** **************************************************
 CLASS NAME: CalorieManager.java

 CLASS FUNCTION: the class that manages how calories are displayed

 ************************************************** */

public class CalorieManager {
    private static int calorieGoal = 2500; // Default value
    private static final AccessExerciseLogs accessExerciseLogs = new AccessExerciseLogs(); // No context needed

    /* **************************************************
        FUNCTION NAME: addCaloriesBurned
        FUNCTION PURPOSE: add performed exercise to the persistence

        INPUT: userID - current user ID to get persistence
                exerciseID - ID of the exercise
                durationMinutes - how long the exercise has been performed
    ************************************************** */
    public static void addCaloriesBurned(int userId, int exerciseId, int durationMinutes) {
        // Ensure date format is correct
        String datePerformed = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        ExerciseLog exerciseLog = new ExerciseLog(userId, exerciseId, durationMinutes, datePerformed);
        accessExerciseLogs.insertExerciseLog(exerciseLog);  // Use new class
    }

    //return the total calories burned by the given userID
    public static int getTotalCaloriesBurned(int userId) {
        String today = LocalDate.now().toString();
        return accessExerciseLogs.getUserTotalDailyBurned(userId, today)/30;
    }

    //return the total calories eaten today by userID
    public static int getCaloriesEatenToday(int userId) {
        String today = LocalDate.now().toString();
        return new AccessFoodLogs(Services.getFoodLogPersistence(), Services.getFoodPersistence())
                .getUserTotalDailyIntake(userId, today);
    }

    public static int getCalorieGoal() {
        return calorieGoal;
    }

    public static void setCalorieGoal(int newGoal) {
        if (newGoal <= 0) {
            throw new IllegalArgumentException(LiteralsConfig.ERROR_CALORIE_GOAL_POSITIVE);
        }
        calorieGoal = newGoal;
    }
}
