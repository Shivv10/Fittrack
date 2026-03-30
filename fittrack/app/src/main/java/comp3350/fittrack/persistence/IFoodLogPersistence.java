package comp3350.fittrack.persistence;

import comp3350.fittrack.objects.FoodLog;

import java.util.List;

/** **************************************************
 CLASS NAME: IFoodLogPersistence.java

 Interface for handling persistence of FoodLog objects.

 ************************************************** */
public interface IFoodLogPersistence {
    // Add a new food log
    boolean addFoodLog(FoodLog foodLog);

    // Update a specific food log
    boolean updateFoodLog(final int userID, final int foodID, final String date, final FoodLog updatedLog);

    // Get all food logs for a user
    List<FoodLog> getFoodLogsByUserId(int userId);

    // Get food logs by user and date
    List<FoodLog> getFoodLogByUserDate(final int userID, final String date);

    // Get a specific food log entry
    FoodLog getFoodLog(final int userID, final int foodID, final String date);

    // Delete a specific food log
    void deleteFoodLog(final int userID, final int foodID, final String date);

    // Delete all logs for a specific food
    boolean deleteLogsByFoodId(int foodID);
}
