package comp3350.srsys.tests.unittests.persistence;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import comp3350.fittrack.objects.FoodLog;
import comp3350.fittrack.persistence.stubs.food.FoodLogPersistenceStub;

public class FoodLogPersistenceStubTest {

    private FoodLogPersistenceStub foodLogPersistence;

    @Before
    public void setUp() {
        foodLogPersistence = new FoodLogPersistenceStub();
    }

    @Test
    public void testAddFoodLog() {
        FoodLog newLog = new FoodLog(3, 1, "2021-01-18", 100);

        // Add the new food log
        boolean added = foodLogPersistence.addFoodLog(newLog);

        // Assert that the log was successfully added
        assertTrue(added);

        // Verify that the log can be retrieved
        List<FoodLog> logs = foodLogPersistence.getFoodLogsByUserId(3);
        assertEquals(1, logs.size());
        assertEquals("2021-01-18", logs.get(0).getDate());
    }

    @Test
    public void testAddFoodLogWithNull() {
        // Try to add a null food log
        boolean added = foodLogPersistence.addFoodLog(null);

        // Assert that the result is false since null cannot be added
        assertFalse(added);
    }

    @Test
    public void testGetFoodLog() {
        // Retrieve a food log with userID = 1, foodID = 1, and date = "2021-01-01"
        FoodLog result = foodLogPersistence.getFoodLog(0, 1, "2021-01-01");

        // Assert that the result is not null and contains the correct data
        assertNotNull(result);
        assertEquals(0, result.getUserId());
        assertEquals(1, result.getFoodId());
        assertEquals("2021-01-01", result.getDate());
        assertEquals(25, result.getQuantity());
    }

    @Test
    public void testUpdateFoodLog() {
        //The log modified is:
        //  String date3 = LocalDate.of(2021, 1, 17).toString();
        //  foodLogs.add(new FoodLog(1, 3, date3, 50));

        // Create the updated log
        FoodLog updatedLog = new FoodLog(1, 3, "2021-01-17", 25);

        // Update the log
        boolean updated = foodLogPersistence.updateFoodLog(1, 3, "2021-01-17", updatedLog);

        // Assert that the log was updated
        assertTrue(updated);

        // Retrieve the updated log and verify its contents
        List<FoodLog> logs = foodLogPersistence.getFoodLogsByUserId(1);
        assertEquals(1, logs.size());   //should contain 1 as we're updating the only log
        assertEquals(25, logs.get(0).getQuantity());    //check the new quantity
    }

    @Test
    public void testUpdateFoodLogNotFound() {
        // Try to update a log that does not exist
        FoodLog updatedLog = new FoodLog(3, 1, "2021-01-01", 50);

        // Assert that the update returns false
        boolean updated = foodLogPersistence.updateFoodLog(3, 1, "2021-01-02", updatedLog);
        assertFalse(updated);
    }

    @Test
    public void testGetFoodLogsByUserId() {
        // Retrieve logs by user ID
        List<FoodLog> logs = foodLogPersistence.getFoodLogsByUserId(2);

        // Assert that the correct number of logs are returned
        assertEquals(6, logs.size());   //user 2 has 6 logs
    }

    @Test
    public void testGetFoodLogByUserDate() {
        // Retrieve logs by user ID and date
        List<FoodLog> logs = foodLogPersistence.getFoodLogByUserDate(2, "2021-01-01");

        // Assert that the correct number of logs are returned for the specific date
        assertEquals(4, logs.size());  // User 2 has 4 logs on "2021-01-01"
    }

    @Test
    public void testGetFoodLogByUserDateNotFound() {
        // Retrieve logs for a user and date that don't exist
        List<FoodLog> logs = foodLogPersistence.getFoodLogByUserDate(3, "2021-01-01");

        // Assert that no logs are returned
        assertTrue(logs.isEmpty());
    }

    @Test
    public void testDeleteFoodLog() {
        // Delete the log
        foodLogPersistence.deleteFoodLog(2, 5, "2021-01-02");

        // Verify that the log was deleted
        List<FoodLog> logs = foodLogPersistence.getFoodLogsByUserId(2);
        assertEquals(5, logs.size());  // User 2 should now have 5 logs (started with 6)
    }

    @Test
    public void testDeleteFoodLogNotFound() {
        // Try to delete a log that doesn't exist
        foodLogPersistence.deleteFoodLog(3, 1, "2021-01-01");

        // Assert that no logs were deleted (nothing should change for user 3)
        List<FoodLog> logs = foodLogPersistence.getFoodLogsByUserId(3);
        assertTrue(logs.isEmpty());
    }

    @Test
    public void testDeleteLogsByFoodId() {
        // Delete logs by food ID
        boolean deleted = foodLogPersistence.deleteLogsByFoodId(5);

        // Assert that logs were deleted
        assertTrue(deleted);

        // Verify that no logs with food ID 5 remain
        List<FoodLog> logs = foodLogPersistence.getFoodLogsByUserId(2);
        assertTrue(logs.stream().noneMatch(log -> log.getFoodId() == 5));
    }

    @Test
    public void testDeleteLogsByFoodIdNotFound() {
        // Try to delete logs by food ID that doesn't exist
        boolean deleted = foodLogPersistence.deleteLogsByFoodId(99);

        // Assert that the result is false, as no logs were found for the food ID
        assertFalse(deleted);
    }
}
