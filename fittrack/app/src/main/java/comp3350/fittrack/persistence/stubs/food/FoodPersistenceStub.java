package comp3350.fittrack.persistence.stubs.food;

import comp3350.fittrack.objects.Food;
import comp3350.fittrack.persistence.IFoodPersistence;

import java.util.ArrayList;

/** **************************************************
 CLASS NAME: FoodPersistenceStub.java

 In-memory stub of FoodPersistence for testing purposes.

 ************************************************** */

public class FoodPersistenceStub implements IFoodPersistence {
    //list of exercises
    private ArrayList<Food> foodList;


    //The basic food options stored in the Stub
    public FoodPersistenceStub(){
        this.foodList = new ArrayList<Food>();
        foodList.add(new Food( 1,"Apple", 95));
        foodList.add(new Food( 2,"Banana", 105));
        foodList.add(new Food( 3,"Orange", 62));
        foodList.add(new Food( 4,"Grapes", 69));
        foodList.add(new Food( 5,"Strawberries", 33));
        foodList.add(new Food( 6,"Blueberries", 57));
        foodList.add(new Food( 7,"Raspberries", 64));
        foodList.add(new Food( 8,"Blackberries", 43));
        foodList.add(new Food( 9,"Pineapple", 83));
        foodList.add(new Food( 10,"Watermelon", 30));
        foodList.add(new Food( 11,"Cantaloupe", 53));
        foodList.add(new Food( 12,"Honeydew", 64));
        foodList.add(new Food( 13,"Peach", 59));
        foodList.add(new Food( 14,"Pear", 102));
        foodList.add(new Food( 15,"Plum", 30));
        foodList.add(new Food( 16,"Cherry", 77));
        foodList.add(new Food( 17,"Kiwi", 42));
        foodList.add(new Food( 18,"Mango", 99));
        foodList.add(new Food( 19,"Papaya", 119));
        foodList.add(new Food( 20,"Pomegranate", 234));
        foodList.add(new Food( 21,"Avocado", 322));
        foodList.add(new Food( 22,"Tomato", 22));
        foodList.add(new Food( 23,"Cucumber", 45));
        foodList.add(new Food( 24,"Carrot", 41));
        foodList.add(new Food( 25,"Celery", 6));
        foodList.add(new Food( 26,"Broccoli", 55));
        foodList.add(new Food( 27,"Cauliflower", 25));
        foodList.add(new Food( 28,"Spinach", 23));
        foodList.add(new Food( 29,"Kale", 33));
        foodList.add(new Food( 30,"Lettuce", 5));
    }

    //send the arraylist of food items
    public ArrayList<Food> getFoodList(){
        return this.foodList;
    }

    public int addFood(Food food) {
        foodList.add(food);
        return food.getFoodID();
    }
    public Food getFoodByFoodName(String foodName) {
        for (Food food : foodList) {
            if (food.getName().equals(foodName)) {
                return food;
            }
        }
        return null;
    }

    public Food getFoodByID(int foodID) {
        for (Food food : foodList) {
            if (food.getFoodID() == foodID) {
                return food;
            }
        }
        return null;
    }

    public void updateFood(Food updatedFood) {
        for (int i = 0; i < foodList.size(); i++) {
            if (foodList.get(i).getFoodID() == updatedFood.getFoodID()) {
                foodList.set(i, updatedFood);
                return;
            }
        }
    }

    public void deleteFood(int foodID) {
        foodList.removeIf(food -> food.getFoodID() == foodID);
    }
}