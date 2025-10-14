package comp3350.srsys.tests.integration;

import comp3350.fittrack.objects.Exercise;
import comp3350.fittrack.persistence.hsqldb.exercise.ExercisePersistenceHSQLDB;
import comp3350.srsys.tests.utils.TestUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class AccessExercisesIT {
    private ExercisePersistenceHSQLDB exercisePersistence;

    @Before
    public void setUp() throws IOException {
        File tempDB = TestUtils.copyDB();
        String dbPath = tempDB.getAbsolutePath().replace(".script", "");
        exercisePersistence = new ExercisePersistenceHSQLDB(dbPath);
    }

    @Test
    public void testInsertAndGetExercise() {
        Exercise exercise = new Exercise(-1, "Running", 300);
        Exercise inserted = exercisePersistence.insertExercise(exercise);
        assertNotNull("Inserted exercise should not be null", inserted);
        assertTrue("Inserted exercise ID should be positive", inserted.getExerciseID() >= 0);
        Exercise retrieved = exercisePersistence.getExerciseById(inserted.getExerciseID());
        assertNotNull("Retrieved exercise should not be null", retrieved);
        assertEquals("Exercise name should be 'Running'", "Running", retrieved.getName());
        assertEquals("Calories burned should be 300", 300, retrieved.getCaloriesBurned());
    }

    @Test
    public void testUpdateExercise() {
        Exercise exercise = new Exercise(-1, "Cycling", 250);
        Exercise inserted = exercisePersistence.insertExercise(exercise);
        int id = inserted.getExerciseID();
        // Update the exercise calories.
        Exercise updated = new Exercise(id, "Cycling", 275);
        exercisePersistence.updateExercise(id, updated);
        Exercise retrieved = exercisePersistence.getExerciseById(id);
        assertNotNull("Retrieved exercise should not be null", retrieved);
        assertEquals("Updated calories should be 275", 275, retrieved.getCaloriesBurned());
    }

    @Test
    public void testDeleteExercise() {
        Exercise exercise = new Exercise(-1, "Swimming", 350);
        Exercise inserted = exercisePersistence.insertExercise(exercise);
        int id = inserted.getExerciseID();
        Exercise retrieved = exercisePersistence.getExerciseById(id);
        assertNotNull("Exercise should exist before deletion", retrieved);
        exercisePersistence.deleteExercise(id);
        Exercise deleted = exercisePersistence.getExerciseById(id);
        assertNull("Exercise should be null after deletion", deleted);
    }
}
