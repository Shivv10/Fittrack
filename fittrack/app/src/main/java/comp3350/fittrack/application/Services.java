package comp3350.fittrack.application;


import comp3350.fittrack.persistence.IExercisePersistence;
import comp3350.fittrack.persistence.IUserPersistence;
import comp3350.fittrack.persistence.hsqldb.exercise.ExercisePersistenceHSQLDB;
import comp3350.fittrack.persistence.IFoodPersistence;
import comp3350.fittrack.persistence.hsqldb.food.FoodPersistenceHSQLDB;
import comp3350.fittrack.persistence.IFoodLogPersistence;
import comp3350.fittrack.persistence.IExerciseLogPersistence;
import comp3350.fittrack.persistence.hsqldb.exercise.ExerciseLogPersistenceHSQLDB;
import comp3350.fittrack.persistence.hsqldb.food.FoodLogPersistenceHSQLDB;
import comp3350.fittrack.persistence.hsqldb.user.UserPersistenceHSQLDB;

/**
 **************************************************
 CLASS NAME: Services.java

 Centralized service locator for persistence dependencies.
 Lazily initializes DAOs for HSQLDB

 **************************************************
 */


public class Services {
    private static boolean initialized = false;
    private static IFoodLogPersistence foodLogPersistence = null;
    private static IExerciseLogPersistence exerciseLogPersistence = null;
    private static IFoodPersistence foodPersistence = null;
    private static IExercisePersistence exercisePersistence = null;
    private static IUserPersistence userPersistence = null;

    public static IUserPersistence getUserPersistence() {
        if (!initialized)
            buildPersistence();
        return userPersistence;
    }

    public static IFoodLogPersistence getFoodLogPersistence() {
        if (!initialized)
            buildPersistence();
        return foodLogPersistence;
    }

    public static IExerciseLogPersistence getExerciseLogPersistence() {
        if (!initialized)
            buildPersistence();
        return exerciseLogPersistence;
    }

    public static IExercisePersistence getExercisePersistence() {
        if (!initialized)
            buildPersistence();
        return exercisePersistence;
    }

    public static IFoodPersistence getFoodPersistence() {
        if (!initialized)
            buildPersistence();
        return foodPersistence;
    }

    private static void buildPersistence() {
        if (!initialized) {
            userPersistence = new UserPersistenceHSQLDB(Main.getDBPathName());
            foodPersistence = new FoodPersistenceHSQLDB(Main.getDBPathName());
            exercisePersistence = new ExercisePersistenceHSQLDB(Main.getDBPathName());
            exerciseLogPersistence = new ExerciseLogPersistenceHSQLDB(Main.getDBPathName());
            foodLogPersistence = new FoodLogPersistenceHSQLDB(Main.getDBPathName());

            initialized = true;
        }
    }
}
