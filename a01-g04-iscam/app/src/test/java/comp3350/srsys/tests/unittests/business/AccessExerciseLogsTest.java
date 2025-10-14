package comp3350.srsys.tests.unittests.business;
import comp3350.fittrack.business.exercise.AccessExerciseLogs;
import comp3350.fittrack.business.exercise.exceptions.ExerciseNotFoundException;
import comp3350.fittrack.business.exercise.exceptions.InvalidExerciseException;
import comp3350.fittrack.objects.Exercise;
import comp3350.fittrack.objects.ExerciseLog;
import comp3350.fittrack.persistence.IExerciseLogPersistence;
import comp3350.fittrack.persistence.IExercisePersistence;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AccessExerciseLogsTest {

    private AccessExerciseLogs accessExerciseLogs;
    private IExerciseLogPersistence mockLogPersistence;
    private IExercisePersistence mockExercisePersistence;

    @Before
    public void setUp() {
        mockLogPersistence = mock(IExerciseLogPersistence.class);
        mockExercisePersistence = mock(IExercisePersistence.class);
        accessExerciseLogs = new AccessExerciseLogs(mockLogPersistence, mockExercisePersistence);
    }

    @Test
    public void testGetExerciseLogFound() {
        ExerciseLog mockLog = new ExerciseLog(1, 2, 30, "2025-03-19");
        when(mockLogPersistence.getExerciseLogsByUserId(1)).thenReturn(List.of(mockLog));

        ExerciseLog result = accessExerciseLogs.getExerciseLog(1, 2, "2025-03-19");
        assertNotNull(result);
        assertEquals(30, result.getDurationMinutes());
    }

    @Test
    public void testGetExerciseLogNotFound() {
        when(mockLogPersistence.getExerciseLogsByUserId(1)).thenReturn(Collections.emptyList());
        assertNull(accessExerciseLogs.getExerciseLog(1, 2, "2025-03-19"));
    }

    @Test
    public void testInsertExerciseLogValid() throws InvalidExerciseException {
        ExerciseLog newLog = new ExerciseLog(1, 2, 30, "2025-03-19");

        // Mock the expected behavior of addExerciseLog()
        when(mockLogPersistence.addExerciseLog(any(ExerciseLog.class))).thenReturn(true); // Assuming it returns an int

        accessExerciseLogs.insertExerciseLog(newLog);

        verify(mockLogPersistence, times(1)).addExerciseLog(newLog);
    }

    @Test
    public void testInsertExerciseLogNullThrowsException() {
        assertThrows(InvalidExerciseException.class, () -> accessExerciseLogs.insertExerciseLog(null));
    }

    @Test
    public void testUpdateExerciseLogValid() throws InvalidExerciseException, ExerciseNotFoundException {
        ExerciseLog updatedLog = new ExerciseLog(1, 2, 45, "2025-03-19");
        when(mockLogPersistence.getExerciseLogsByUserId(1)).thenReturn(List.of(updatedLog));

        accessExerciseLogs.updateExerciseLog(1, 2, "2025-03-19", updatedLog);
        verify(mockLogPersistence, times(1)).updateExerciseLog(1, 2, "2025-03-19", updatedLog);
    }

    @Test
    public void testUpdateExerciseLogNotFoundThrowsException() {
        when(mockLogPersistence.getExerciseLogsByUserId(1)).thenReturn(Collections.emptyList());

        ExerciseLog updatedLog = new ExerciseLog(1, 2, 45, "2025-03-19");
        assertThrows(ExerciseNotFoundException.class, () -> accessExerciseLogs.updateExerciseLog(1, 2, "2025-03-19", updatedLog));
    }

    @Test
    public void testDeleteExerciseLogValid() throws ExerciseNotFoundException {
        ExerciseLog existingLog = new ExerciseLog(1, 2, 30, "2025-03-19");
        when(mockLogPersistence.getExerciseLogsByUserId(1)).thenReturn(List.of(existingLog));

        accessExerciseLogs.deleteExerciseLog(1, 2, "2025-03-19");
        verify(mockLogPersistence, times(1)).deleteExerciseLog(1, 2, "2025-03-19");
    }

    @Test
    public void testDeleteExerciseLogNotFoundThrowsException() {
        when(mockLogPersistence.getExerciseLogsByUserId(1)).thenReturn(Collections.emptyList());

        assertThrows(ExerciseNotFoundException.class, () -> accessExerciseLogs.deleteExerciseLog(1, 2, "2025-03-19"));
    }

    @Test
    public void testGetUserTotalDailyBurned() {
        ExerciseLog log1 = new ExerciseLog(1, 2, 30, "2025-03-19");
        ExerciseLog log2 = new ExerciseLog(1, 3, 20, "2025-03-19");
        when(mockLogPersistence.getExerciseLogsByUserId(1)).thenReturn(Arrays.asList(log1, log2));

        when(mockExercisePersistence.getExerciseById(2)).thenReturn(new Exercise(2, "Running", 10));
        when(mockExercisePersistence.getExerciseById(3)).thenReturn(new Exercise(3, "Swimming", 8));

        int totalBurned = accessExerciseLogs.getUserTotalDailyBurned(1, "2025-03-19");
        assertEquals(30 * 10 + 20 * 8, totalBurned); // (30 min * 10) + (20 min * 8)
    }

    @Test
    public void testGetUserTotalDailyBurnedNoExercises() {
        when(mockLogPersistence.getExerciseLogsByUserId(1)).thenReturn(Collections.emptyList());
        assertEquals(0, accessExerciseLogs.getUserTotalDailyBurned(1, "2025-03-19"));
    }
}
