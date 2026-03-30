package comp3350.srsys.tests.unittests.persistence;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import comp3350.fittrack.objects.Food;
import comp3350.fittrack.persistence.stubs.food.FoodPersistenceStub;

public class FoodPersistenceStubTest {

    private FoodPersistenceStub foodPersistence;

    @Before
    public void setUp() {
        foodPersistence = new FoodPersistenceStub();
    }

    @Test
    public void testAddFood() {
        Food newFood = new Food(31, "Mango", 150);
        int foodId = foodPersistence.addFood(newFood);

        // Assert that the new food is successfully added and has the correct ID
        assertEquals(31, foodId);
        assertNotNull(foodPersistence.getFoodByID(foodId));
    }

    @Test
    public void testGetFoodByFoodName() {
        Food food = foodPersistence.getFoodByFoodName("Apple");

        // Assert that the food with the name "Apple" is found
        assertNotNull(food);
        assertEquals("Apple", food.getName());
        assertEquals(95, food.getCalories());
    }

    @Test
    public void testGetFoodByFoodNameNotFound() {
        Food food = foodPersistence.getFoodByFoodName("NonExistentFood");

        // Assert that no food is found for a non-existent food name
        assertNull(food);
    }

    @Test
    public void testGetFoodByID() {
        Food food = foodPersistence.getFoodByID(1);

        // Assert that the food with ID 1 is returned correctly
        assertNotNull(food);
        assertEquals(1, food.getFoodID());
        assertEquals("Apple", food.getName());
    }

    @Test
    public void testUpdateFood() {
        Food updatedFood = new Food(1, "Updated Apple", 100);

        // Update the food with ID 1
        foodPersistence.updateFood(updatedFood);

        // Assert that the food with ID 1 has been updated
        Food food = foodPersistence.getFoodByID(1);
        assertNotNull(food);
        assertEquals("Updated Apple", food.getName());
        assertEquals(100, food.getCalories());
    }

    @Test
    public void testUpdateFoodNotFound() {
        Food updatedFood = new Food(99, "Non Existent Food", 200);

        // Attempt to update a food that doesn't exist
        foodPersistence.updateFood(updatedFood);

        // Assert that the food with ID 99 is not found, so nothing changes
        Food food = foodPersistence.getFoodByID(99);
        assertNull(food);
    }

    @Test
    public void testDeleteFood() {
        // Delete the food with ID 1
        foodPersistence.deleteFood(1);

        // Assert that the food with ID 1 no longer exists
        assertNull(foodPersistence.getFoodByID(1));
    }

    @Test
    public void testDeleteFoodNotFound() {
        // Attempt to delete a food that doesn't exist (ID 99)
        foodPersistence.deleteFood(99);

        // Assert that no food was deleted and the list size remains unchanged
        assertNotNull(foodPersistence.getFoodByID(1));
    }

    @Test
    public void testGetFoodList() {
        // Get the list of food items
        ArrayList<Food> foodList = foodPersistence.getFoodList();

        // Assert that the list is not empty and contains food items
        assertFalse(foodList.isEmpty());
        assertEquals(30, foodList.size());  // Assuming there are 30 food items in the list
    }
}

