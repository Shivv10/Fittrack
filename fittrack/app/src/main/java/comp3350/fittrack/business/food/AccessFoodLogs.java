package comp3350.fittrack.business.food;

import comp3350.fittrack.application.Services;
import comp3350.fittrack.business.config.LiteralsConfig;
import comp3350.fittrack.business.food.exceptions.InvalidFoodLogException;
import comp3350.fittrack.objects.FoodLog;
import comp3350.fittrack.persistence.IFoodLogPersistence;
import comp3350.fittrack.persistence.IFoodPersistence;

import java.util.List;

/** **************************************************
 CLASS NAME: AccessFoodLogs.java

 Manages logic for food logging and calorie intake tracking.

 Interfaces with persistence layer for FoodLog and Food.

 ************************************************** */

public class AccessFoodLogs {
    private IFoodLogPersistence foodLogPersistence;
    private IFoodPersistence foodPersistence;

    //constructor
    public AccessFoodLogs(IFoodLogPersistence foodLogPersistence, IFoodPersistence foodPersistence) {
        this.foodLogPersistence = foodLogPersistence;
        this.foodPersistence = foodPersistence;
    }

    //default constructor
    public AccessFoodLogs() {
        foodLogPersistence = Services.getFoodLogPersistence();
        foodPersistence = Services.getFoodPersistence();
    }

    public List<FoodLog> getFoodLogByUserDate(int userID, String date) {
        return foodLogPersistence.getFoodLogByUserDate(userID, date);
    }

    /* **************************************************
     FUNCTION PURPOSE: inserts a FoodLog into the persistence
     ************************************************** */
    public void insertFoodLog(FoodLog foodLog) throws InvalidFoodLogException {
        if (!checkInvariant(foodLog))
            throw new InvalidFoodLogException(LiteralsConfig.ERROR_FOOD_LOG_INVALID);

        foodLogPersistence.addFoodLog(foodLog);
    }

    /* **************************************************
     FUNCTION PURPOSE: gets how many calories a user ate on a given date

     OUTPUT: returns an int with how many calories the user ate
     ************************************************** */
    public int getUserTotalDailyIntake(int userID, String date) {
        List<FoodLog> logs = getFoodLogByUserDate(userID, date);
        int result = 0;
        if (logs.size() > 0)
            result = getUserTotalDailyIntake(logs, foodPersistence);
        return result;
    }

    /* **************************************************
     FUNCTION PURPOSE: gets how many calories a user ate on a given date

     INPUT: IFoodPersistence the list of foods
     FoodLog list

     OUTPUT: returns an int with how many calories the user ate
     ************************************************** */
    private static int getUserTotalDailyIntake(List<FoodLog> logs, IFoodPersistence foods) {
        int total = 0;
        for (int i = 0; i < logs.size(); i++) {
            int gram = logs.get(i).getQuantity();
            // Multiply quantity (in grams) by calorie content to compute total
            double caloriesPerQty = foods.getFoodByID(logs.get(i).getFoodId()).getCalories();
            total += gram * caloriesPerQty;
        }
        return total;
    }

    private boolean checkInvariant(FoodLog foodLog) {
        return foodLog != null && foodLog.getUserId() >= 0 && foodLog.getFoodId() >= 0 && foodLog.getQuantity() > 0;
    }
}
