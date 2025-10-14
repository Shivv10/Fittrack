package comp3350.srsys.tests.unittests.persistence;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import comp3350.fittrack.objects.ExerciseLog;
import comp3350.fittrack.persistence.stubs.exercise.ExerciseLogPersistenceStub;

import java.util.List;

public class ExerciseLogPersistenceStubTest {

    private ExerciseLogPersistenceStub exerciseLogPersistence;

    @Before
    public void setUp() {
        exerciseLogPersistence = new ExerciseLogPersistenceStub();
    }

    @Test
    public void testAddExerciseLog() {
        ExerciseLog newLog = new ExerciseLog(1, 1, 30, "2025-03-01");

        // Add the new exercise log
        boolean added = exerciseLogPersistence.addExerciseLog(newLog);

        // Assert that the log was successfully added
        assertTrue(added);

        // Verify that the log can be retrieved
        List<ExerciseLog> logs = exerciseLogPersistence.getExerciseLogsByUserId(1);
        assertEquals(1, logs.size());
        assertEquals("2025-03-01", logs.get(0).getDatePerformed());
    }

    @Test
    public void testAddExerciseLogWithNull() {
        // Try to add a null exercise log
        boolean added = exerciseLogPersistence.addExerciseLog(null);

        // Assert that the result is false since null cannot be added
        assertFalse(added);
    }

    @Test
    public void testUpdateExerciseLog() {
        // Add an exercise log to update
        ExerciseLog newLog = new ExerciseLog(1, 1, 30, "2025-03-01");
        exerciseLogPersistence.addExerciseLog(newLog);

        // Create the updated log
        ExerciseLog updatedLog = new ExerciseLog(1, 1, 40, "2025-03-01");

        // Update the log
        boolean updated = exerciseLogPersistence.updateExerciseLog(1, 1, "2025-03-01", updatedLog);

        // Assert that the log was updated
        assertTrue(updated);

        // Retrieve the updated log and verify its contents
        List<ExerciseLog> logs = exerciseLogPersistence.getExerciseLogsByUserId(1);
        assertEquals(1, logs.size());
        assertEquals(40, logs.get(0).getDurationMinutes());
    }

    @Test
    public void testUpdateExerciseLogNotFound() {
        // Try to update a log that does not exist
        ExerciseLog updatedLog = new ExerciseLog(1, 1, 40, "2025-03-02");

        // Assert that the update returns false
        boolean updated = exerciseLogPersistence.updateExerciseLog(1, 1, "2025-03-02", updatedLog);
        assertFalse(updated);
    }

    @Test
    public void testGetExerciseLogsByUserId() {
        // Add some logs
        exerciseLogPersistence.addExerciseLog(new ExerciseLog(1, 1, 30, "2025-03-01"));
        exerciseLogPersistence.addExerciseLog(new ExerciseLog(1, 2, 45, "2025-03-02"));

        // Retrieve logs by user ID
        List<ExerciseLog> logs = exerciseLogPersistence.getExerciseLogsByUserId(1);

        // Assert that the correct number of logs are returned
        assertEquals(2, logs.size());
    }

    @Test
    public void testDeleteExerciseLog() {
        // Add a log to delete
        ExerciseLog log = new ExerciseLog(1, 1, 30, "2025-03-01");
        exerciseLogPersistence.addExerciseLog(log);

        // Delete the log
        boolean deleted = exerciseLogPersistence.deleteExerciseLog(1, 1, "2025-03-01");

        // Assert that the log was deleted
        assertTrue(deleted);

        // Verify that no logs exist for the given user and exercise
        List<ExerciseLog> logs = exerciseLogPersistence.getExerciseLogsByUserId(1);
        assertTrue(logs.isEmpty());
    }

    @Test
    public void testDeleteExerciseLogNotFound() {
        // Try to delete a log that doesn't exist
        boolean deleted = exerciseLogPersistence.deleteExerciseLog(1, 1, "2025-03-01");

        // Assert that the result is false, as the log doesn't exist
        assertFalse(deleted);
    }

    @Test
    public void testDeleteLogsByExerciseId() {
        // Add some logs
        exerciseLogPersistence.addExerciseLog(new ExerciseLog(1, 1, 30, "2025-03-01"));
        exerciseLogPersistence.addExerciseLog(new ExerciseLog(1, 1, 45, "2025-03-02"));

        // Delete logs by exercise ID
        boolean deleted = exerciseLogPersistence.deleteLogsByExerciseId(1);

        // Assert that logs were deleted
        assertTrue(deleted);

        // Verify that no logs with exercise ID 1 remain
        List<ExerciseLog> logs = exerciseLogPersistence.getExerciseLogsByUserId(1);
        assertTrue(logs.isEmpty());
    }

    @Test
    public void testDeleteLogsByExerciseIdNotFound() {
        // Try to delete logs by exercise ID that doesn't exist
        boolean deleted = exerciseLogPersistence.deleteLogsByExerciseId(99);

        // Assert that the result is false, as no logs were found for the exercise ID
        assertFalse(deleted);
    }
}
