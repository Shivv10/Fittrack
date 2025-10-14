package comp3350.srsys.tests.integration;

import comp3350.fittrack.business.food.AccessFood;
import comp3350.fittrack.business.food.exceptions.InvalidFoodException;
import comp3350.fittrack.objects.Food;
import comp3350.fittrack.persistence.IFoodPersistence;
import comp3350.fittrack.persistence.hsqldb.food.FoodPersistenceHSQLDB;
import comp3350.srsys.tests.utils.TestUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class AccessFoodsIT {
    private AccessFood accessFood;

    @Before
    public void setUp() throws IOException {
        // Copy the production database script to a temporary file.
        File tempDB = TestUtils.copyDB();
        // Remove the ".script" extension to get the actual database path.
        String dbPath = tempDB.getAbsolutePath().replace(".script", "");
        // Create the persistence layer using the temporary database.
        IFoodPersistence foodPersistence = new FoodPersistenceHSQLDB(dbPath);
        accessFood = new AccessFood(foodPersistence);
    }

    @Test
    public void testAddAndSearchFood() {
        System.out.println("\nStarting testAddAndSearchFood");
        // Add a new food using the business method.
        int id = accessFood.addFood(0, "Apple", 95);
        // Retrieve the food by the returned ID.
        Food retrieved = accessFood.searchByFoodID(id);
        assertNotNull("Retrieved food should not be null", retrieved);
        assertEquals("Food name should be Apple", "Apple", retrieved.getName());
        assertEquals("Food calories should be 95", 95, retrieved.getCalories());
        System.out.println("Finished testAddAndSearchFood");
    }

    @Test(expected = InvalidFoodException.class)
    public void testAddDuplicateFood() {
        System.out.println("\nStarting testAddDuplicateFood");
        // Add a food.
        accessFood.addFood(0, "Banana", 105);
        // Attempt to add another food with the same name.
        accessFood.addFood(0, "Banana", 105);
        System.out.println("Finished testAddDuplicateFood");
    }

    @Test
    public void testUpdateFood() {
        System.out.println("\nStarting testUpdateFood");
        // First, add a food.
        int id = accessFood.addFood(0, "Orange", 62);
        Food original = accessFood.searchByFoodID(id);
        assertNotNull("Original food should not be null", original);
        // Create an updated Food object with new calories.
        Food updatedFood = new Food(id, "Orange", 70);
        // Use the underlying persistence (via getFoodList()) to update.
        accessFood.getFoodList().updateFood(updatedFood);
        // Retrieve and check that the update was applied.
        Food retrieved = accessFood.searchByFoodID(id);
        assertNotNull("Updated food should not be null", retrieved);
        assertEquals("Calories should be updated to 70", 70, retrieved.getCalories());
        System.out.println("Finished testUpdateFood");
    }

    @Test
    public void testDeleteFood() {
        System.out.println("\nStarting testDeleteFood");
        // Add a new food.
        int id = accessFood.addFood(0, "Grapes", 67);
        Food beforeDelete = accessFood.searchByFoodID(id);
        assertNotNull("Food should exist before deletion", beforeDelete);
        // Use the underlying persistence (via getFoodList()) to delete.
        accessFood.getFoodList().deleteFood(id);
        // Verify that the food is no longer found.
        Food deleted = accessFood.searchByFoodID(id);
        assertNull("Food should be null after deletion", deleted);
        System.out.println("Finished testDeleteFood");
    }
}
