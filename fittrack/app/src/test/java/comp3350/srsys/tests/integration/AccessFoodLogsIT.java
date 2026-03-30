package comp3350.srsys.tests.integration;

import comp3350.fittrack.objects.FoodLog;
import comp3350.fittrack.persistence.hsqldb.food.FoodLogPersistenceHSQLDB;
import comp3350.srsys.tests.utils.TestUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class AccessFoodLogsIT {
    private FoodLogPersistenceHSQLDB foodLogPersistence;
    private String dbPath;

    @Before
    public void setUp() throws IOException, SQLException {
        // Copy the production database script to a temporary file.
        File tempDB = TestUtils.copyDB();
        // Remove the ".script" extension to obtain the DB path.
        dbPath = tempDB.getAbsolutePath().replace(".script", "");

        // Insert dummy parent records for the foreign key constraints.
        // For FOODLOGS, we need a dummy user and a dummy food.
        String dbUrl = "jdbc:hsqldb:file:" + dbPath + ";shutdown=true;hsqldb.lock_file=false";
        try (Connection c = DriverManager.getConnection(dbUrl, "SA", "");
             Statement st = c.createStatement()) {
            // Insert a dummy user.
            st.executeUpdate("INSERT INTO USERS(age, weight, height, password, username, name) " +
                    "VALUES(30, 150, 140, 'pass', 'dummyUser', 'Dummy User')");
            // Insert a dummy food.
            st.executeUpdate("INSERT INTO FOODS(name, calories) VALUES('DummyFood', 100)");
        }

        // Create the persistence layer using the temporary database.
        foodLogPersistence = new FoodLogPersistenceHSQLDB(dbPath);
    }

    @Test
    public void testAddAndGetFoodLog() {
        // Create a food log for user id 0, food id 0, date "2025-03-22", quantity 2.
        FoodLog log = new FoodLog(0, 0, "2025-03-22", 2);
        boolean added = foodLogPersistence.addFoodLog(log);
        assertTrue("Food log should be added", added);
        // Retrieve the log using the combination of user, food, and date.
        FoodLog retrieved = foodLogPersistence.getFoodLog(0, 0, "2025-03-22");
        assertNotNull("Retrieved food log should not be null", retrieved);
        assertEquals("Quantity should be 2", 2, retrieved.getQuantity());
    }

    @Test
    public void testUpdateFoodLog() {
        // Insert a food log.
        FoodLog log = new FoodLog(0, 0, "2025-03-22", 2);
        foodLogPersistence.addFoodLog(log);
        // Update the log to change quantity to 5.
        FoodLog updatedLog = new FoodLog(0, 0, "2025-03-22", 5);
        boolean updated = foodLogPersistence.updateFoodLog(0, 0, "2025-03-22", updatedLog);
        assertTrue("Food log should be updated", updated);
        FoodLog retrieved = foodLogPersistence.getFoodLog(0, 0, "2025-03-22");
        assertNotNull("Retrieved log should not be null", retrieved);
        assertEquals("Quantity should be updated to 5", 5, retrieved.getQuantity());
    }

    @Test
    public void testDeleteFoodLog() {
        // Insert a food log.
        FoodLog log = new FoodLog(0, 0, "2025-03-22", 2);
        foodLogPersistence.addFoodLog(log);
        FoodLog beforeDelete = foodLogPersistence.getFoodLog(0, 0, "2025-03-22");
        assertNotNull("Food log should exist before deletion", beforeDelete);
        // Delete the food log.
        foodLogPersistence.deleteFoodLog(0, 0, "2025-03-22");
        FoodLog afterDelete = foodLogPersistence.getFoodLog(0, 0, "2025-03-22");
        assertNull("Food log should be null after deletion", afterDelete);
    }
}
