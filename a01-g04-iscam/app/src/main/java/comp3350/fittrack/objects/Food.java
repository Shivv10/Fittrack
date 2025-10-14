package comp3350.fittrack.objects;

/** **************************************************
 CLASS NAME: Food.java

 CLASS FUNCTION: Represents a food item that can be logged by a user.

 Stores a unique food ID, name, and its calorie value.

 ************************************************** */

public class Food {
    private final int foodID;
    private final String name;
    private final int calories;

    public Food(int foodID, String name, int calories) {
        this.foodID = foodID;
        this.name = name;
        this.calories = calories;
    }

    public String getName() {
        return name;
    }

    public int getCalories() {
        return calories;
    }

    public int getFoodID() {
        return foodID;
    }

    @Override
    public String toString() {
        return name + " (" + calories + " cal)";
    }
}
