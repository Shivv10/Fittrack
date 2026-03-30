package comp3350.fittrack.persistence;

import java.util.ArrayList;
import comp3350.fittrack.objects.Food;

/** **************************************************
 CLASS NAME: IFoodPersistence.java

 Interface for handling persistence of Food objects.

 ************************************************** */
public interface IFoodPersistence {
    // Get food by ID
    Food getFoodByID(final int foodID);

    // Get food by name
    Food getFoodByFoodName(final String foodName);

    // Get all food items
    ArrayList<Food> getFoodList();

    // Add a new food item
    int addFood(Food food);

    // Delete a food item
    void deleteFood(int foodID);

    // Update an existing food item
    void updateFood(Food food);
}
