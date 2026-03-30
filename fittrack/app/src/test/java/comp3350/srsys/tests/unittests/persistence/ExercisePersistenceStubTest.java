package comp3350.srsys.tests.unittests.persistence;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import comp3350.fittrack.objects.Exercise;
import comp3350.fittrack.persistence.stubs.exercise.ExercisePersistenceStub;

public class ExercisePersistenceStubTest {

    private ExercisePersistenceStub exercisePersistence;

    @Before
    public void setUp() {
        exercisePersistence = new ExercisePersistenceStub();
    }

    @Test
    public void testGetExerciseByName() {
        Exercise exercise = exercisePersistence.getExerciseByName("Running");

        // Assert that the exercise with the name "Running" is returned correctly
        assertNotNull(exercise);
        assertEquals("Running", exercise.getName());
        assertEquals(100, exercise.getCaloriesBurned());
    }

    @Test
    public void testGetExerciseByNameNotFound() {
        Exercise exercise = exercisePersistence.getExerciseByName("NonExistentExercise");

        // Assert that no exercise is found for a non-existent exercise name
        assertNull(exercise);
    }

    @Test
    public void testGetExerciseById() {
        Exercise exercise = exercisePersistence.getExerciseById(2);

        // Assert that the exercise with ID 2 is returned correctly
        assertNotNull(exercise);
        assertEquals(2, exercise.getExerciseID());
        assertEquals("Swimming", exercise.getName());
    }

    @Test
    public void testInsertExercise() {
        Exercise newExercise = new Exercise(0, "Yoga", 120);

        // Insert the new exercise and verify that it is added to the list
        Exercise insertedExercise = exercisePersistence.insertExercise(newExercise);

        // Assert that the inserted exercise has a valid ID and matches the input
        assertNotNull(insertedExercise);
        assertEquals("Yoga", insertedExercise.getName());
        assertEquals(120, insertedExercise.getCaloriesBurned());
        assertEquals(5, insertedExercise.getExerciseID());  // Assuming this is the 5th item
    }

    @Test
    public void testInsertExerciseWithNull() {
        // Try to insert a null exercise
        Exercise result = exercisePersistence.insertExercise(null);

        // Assert that the result is null
        assertNull(result);
    }

    @Test
    public void testUpdateExercise() {
        Exercise updatedExercise = new Exercise(2, "Updated Swimming", 250);

        // Update the exercise with ID 2
        exercisePersistence.updateExercise(2, updatedExercise);

        // Assert that the exercise with ID 2 has been updated
        Exercise exercise = exercisePersistence.getExerciseById(2);
        assertNotNull(exercise);
        assertEquals("Updated Swimming", exercise.getName());
        assertEquals(250, exercise.getCaloriesBurned());
    }

    @Test
    public void testUpdateExerciseNotFound() {
        Exercise updatedExercise = new Exercise(99, "NonExistent Exercise", 300);

        // Attempt to update an exercise that doesn't exist (ID 99)
        exercisePersistence.updateExercise(99, updatedExercise);

        // Assert that no exercise is found with ID 99
        Exercise exercise = exercisePersistence.getExerciseById(99);
        assertNull(exercise);
    }

    @Test
    public void testDeleteExercise() {
        // Delete the exercise with ID 1
        exercisePersistence.deleteExercise(1);

        // Assert that the exercise with ID 1 no longer exists
        assertNull(exercisePersistence.getExerciseById(1));
    }

    @Test
    public void testDeleteExerciseNotFound() {
        // Attempt to delete an exercise that doesn't exist (ID 99)
        exercisePersistence.deleteExercise(99);

        // Assert that the exercise with ID 99 was not deleted, and the list is unaffected
        Exercise exercise = exercisePersistence.getExerciseById(99);
        assertNull(exercise);
    }

    @Test
    public void testGetExerciseList() {
        // Get the list of exercises
        ArrayList<Exercise> exerciseList = exercisePersistence.getExerciseList();

        // Assert that the list is not empty and contains the expected number of exercises
        assertFalse(exerciseList.isEmpty());
        assertEquals(4, exerciseList.size());  // Assuming there are initially 4 exercises
    }
}

