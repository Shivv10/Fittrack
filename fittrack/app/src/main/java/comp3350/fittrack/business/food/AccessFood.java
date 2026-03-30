package comp3350.fittrack.business.food;

import comp3350.fittrack.application.Services;
import comp3350.fittrack.business.utils.ValidationUtils;
import comp3350.fittrack.business.config.LiteralsConfig;
import comp3350.fittrack.business.food.exceptions.InvalidFoodException;
import comp3350.fittrack.objects.Food;
import comp3350.fittrack.persistence.IFoodPersistence;

/** **************************************************
 CLASS NAME: AccessFood.java

 Handles logic related to food items (search, add).

 Connects to IFoodPersistence through Services.

 ************************************************** */

public class AccessFood {
    private IFoodPersistence foodPersistence;

    public AccessFood() {
        foodPersistence = Services.getFoodPersistence();
    }

    //constructor for testing
    public AccessFood(IFoodPersistence foodPersistence) {
        this.foodPersistence = foodPersistence;
    }

    public IFoodPersistence getFoodList() {
        return foodPersistence;
    }

    /* **************************************************
        FUNCTION PURPOSE: Add an food to the current foodPersistence

        OUTPUT: boolean
                returns a false if the input is invalid
                returns the output of foodPersistence.addFood() otherwise
     ************************************************** */
    public int addFood(int id, String name, int calories) {
        ValidationUtils.validateFood(name, calories);
        if (foodPersistence.getFoodByFoodName(name) != null) {
            throw new InvalidFoodException(LiteralsConfig.ERROR_FOOD_DUPLICATE);
        }

        // Temporary object
        Food newFood = new Food(-1, name, calories);

        // Add to DB and get real ID
        int newId = foodPersistence.addFood(newFood);

        return newId;
    }


    public Food searchByFoodID(int foodID) {
        return foodPersistence.getFoodByID(foodID);
    }
}