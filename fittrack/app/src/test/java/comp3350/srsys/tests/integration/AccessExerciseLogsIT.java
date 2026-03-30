package comp3350.srsys.tests.integration;

import comp3350.fittrack.objects.ExerciseLog;
import comp3350.fittrack.persistence.hsqldb.exercise.ExerciseLogPersistenceHSQLDB;
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
public class AccessExerciseLogsIT {
    private ExerciseLogPersistenceHSQLDB exerciseLogPersistence;
    private String dbPath;

    @Before
    public void setUp() throws IOException, SQLException {
        // Copy the production database script to a temporary file.
        File tempDB = TestUtils.copyDB();
        // Remove the ".script" extension to obtain the DB path.
        dbPath = tempDB.getAbsolutePath().replace(".script", "");

        // Insert dummy parent records to satisfy the foreign key constraints.
        // For EXERCISELOGS, we need a dummy user and a dummy exercise.
        String dbUrl = "jdbc:hsqldb:file:" + dbPath + ";shutdown=true;hsqldb.lock_file=false";
        try (Connection c = DriverManager.getConnection(dbUrl, "SA", "");
             Statement st = c.createStatement()) {
            // Insert a dummy user.
            st.executeUpdate("INSERT INTO USERS(age, weight, height, password, username, name) " +
                    "VALUES(30, 150, 140, 'pass', 'dummyUser', 'Dummy User')");
            // Insert a dummy exercise.
            // Adjust the column names and values if your EXERCISES table is defined differently.
            st.executeUpdate("INSERT INTO EXERCISES(name, caloriesBurned) VALUES('DummyExercise', 50)");
        }

        // Create the persistence layer using the temporary database.
        exerciseLogPersistence = new ExerciseLogPersistenceHSQLDB(dbPath);
    }

    @Test
    public void testAddAndGetExerciseLog() {
        // Create an exercise log for user id 0, exercise id 0, duration 30 minutes, and date "2025-03-22".
        ExerciseLog log = new ExerciseLog(0, 0, 30, "2025-03-22");
        boolean added = exerciseLogPersistence.addExerciseLog(log);
        assertTrue("Exercise log should be added", added);
        // Retrieve logs for the user.
        assertFalse("Exercise logs list should not be empty", exerciseLogPersistence.getExerciseLogsByUserId(0).isEmpty());
    }

    @Test
    public void testUpdateExerciseLog() {
        ExerciseLog log = new ExerciseLog(0, 0, 30, "2025-03-22");
        exerciseLogPersistence.addExerciseLog(log);
        ExerciseLog updatedLog = new ExerciseLog(0, 0, 45, "2025-03-22");
        boolean updated = exerciseLogPersistence.updateExerciseLog(0, 0, "2025-03-22", updatedLog);
        assertTrue("Exercise log should be updated", updated);
        ExerciseLog retrieved = exerciseLogPersistence.getExerciseLogsByUserId(0).get(0);
        assertEquals("Duration should be updated to 45", 45, retrieved.getDurationMinutes());
    }

    @Test
    public void testDeleteExerciseLog() {
        ExerciseLog log = new ExerciseLog(0, 0, 30, "2025-03-22");
        exerciseLogPersistence.addExerciseLog(log);
        assertFalse("Exercise logs list should not be empty", exerciseLogPersistence.getExerciseLogsByUserId(0).isEmpty());
        boolean deleted = exerciseLogPersistence.deleteExerciseLog(0, 0, "2025-03-22");
        assertTrue("Exercise log should be deleted", deleted);
        assertTrue("Exercise logs list should be empty after deletion", exerciseLogPersistence.getExerciseLogsByUserId(0).isEmpty());
    }
}
