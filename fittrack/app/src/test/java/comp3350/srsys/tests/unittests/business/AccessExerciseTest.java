package comp3350.srsys.tests.unittests.business;

import comp3350.fittrack.business.exercise.AccessExercise;
import comp3350.fittrack.business.exercise.exceptions.ExerciseNotFoundException;
import comp3350.fittrack.business.exercise.exceptions.InvalidExerciseException;
import comp3350.fittrack.objects.Exercise;
import comp3350.fittrack.persistence.IExercisePersistence;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

public class AccessExerciseTest {

    private AccessExercise accessExercise;
    private IExercisePersistence mockPersistence;

    @Before
    public void setUp() {
        mockPersistence = mock(IExercisePersistence.class);
        accessExercise = new AccessExercise(mockPersistence);
    }

    @Test
    public void testInsertExerciseValid() throws InvalidExerciseException {
        accessExercise.insertExercise("Running", 300);
        verify(mockPersistence, times(1)).insertExercise(any(Exercise.class));
    }

    @Test
    public void testInsertExerciseInvalidThrowsException() {
        assertThrows(InvalidExerciseException.class, () -> {
            accessExercise.insertExercise("", -100);
        });
    }

    @Test
    public void testGetAllExercises() {
        List<Exercise> mockExercises = Arrays.asList(
                new Exercise(1, "Running", 300),
                new Exercise(2, "Swimming", 250)
        );
        when(mockPersistence.getExerciseList()).thenReturn(mockExercises);

        List<Exercise> result = accessExercise.getAllExercises();
        assertEquals(2, result.size());
        assertEquals("Running", result.get(0).getName());
    }

    @Test
    public void testGetExerciseById() {
        Exercise mockExercise = new Exercise(1, "Cycling", 400);
        when(mockPersistence.getExerciseById(1)).thenReturn(mockExercise);

        Exercise result = accessExercise.getExerciseById(1);
        assertNotNull(result);
        assertEquals("Cycling", result.getName());
    }

    @Test
    public void testGetExerciseByIdInvalid() {
        when(mockPersistence.getExerciseById(-1)).thenReturn(null);
        assertNull(accessExercise.getExerciseById(-1));
    }

    @Test
    public void testGetExerciseByName() {
        Exercise mockExercise = new Exercise(3, "Jump Rope", 500);
        when(mockPersistence.getExerciseByName("Jump Rope")).thenReturn(mockExercise);

        Exercise result = accessExercise.getExerciseByName("Jump Rope");
        assertNotNull(result);
        assertEquals(500, result.getCaloriesBurned());
    }

    @Test
    public void testDeleteExerciseByIdValid() throws InvalidExerciseException, ExerciseNotFoundException {
        doNothing().when(mockPersistence).deleteExercise(1);
        accessExercise.deleteExerciseById(1);
        verify(mockPersistence, times(1)).deleteExercise(1);
    }

    @Test
    public void testDeleteExerciseByIdInvalidThrowsException() {
        assertThrows(InvalidExerciseException.class, () -> accessExercise.deleteExerciseById(-1));
    }


    @Test
    public void testUpdateExerciseValid() throws InvalidExerciseException {
        Exercise updatedExercise = new Exercise(1, "Updated", 350);
        doNothing().when(mockPersistence).updateExercise(anyInt(), any(Exercise.class));

        accessExercise.updateExercise(updatedExercise);
        verify(mockPersistence, times(1)).updateExercise(eq(1), any(Exercise.class));
    }

    @Test
    public void testUpdateExerciseInvalidThrowsException() {
        assertThrows(InvalidExerciseException.class, () -> {
            accessExercise.updateExercise(new Exercise(1, "", -10));
        });
    }
}
