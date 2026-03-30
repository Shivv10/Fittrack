package comp3350.fittrack.objects;
import comp3350.fittrack.business.food.AccessFood;

/** **************************************************
 CLASS NAME: FoodLog.java

 CLASS FUNCTION: Represents a food log entry tied to a specific user.

 Holds user ID, food ID, quantity, and the date of consumption.

 ************************************************** */

public class FoodLog {
    private final int userId;
    private final int foodId;
    private final int quantity;
    private final String date;

    public FoodLog(int userId, int foodId, String date, int quantity) {
        this.userId = userId;
        this.foodId = foodId;
        this.quantity = quantity;
        this.date = date;
    }

    public int getUserId() { return userId; }

    public String getDate() { return date; }
    public int getFoodId() { return foodId; }
    public int getQuantity() { return quantity; }

    @Override
    public String toString(){
        AccessFood search = new AccessFood();
        Food food = search.searchByFoodID(foodId);
        return food.getName() + ", " + quantity;
    }
}
