package comp3350.srsys.tests.unittests.business;

import comp3350.fittrack.business.food.AccessFoodLogs;
import comp3350.fittrack.business.food.exceptions.InvalidFoodLogException;
import comp3350.fittrack.objects.FoodLog;
import comp3350.fittrack.persistence.IFoodLogPersistence;
import comp3350.fittrack.persistence.IFoodPersistence;
import comp3350.fittrack.objects.Food;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AccessFoodLogsTest {

    private AccessFoodLogs accessFoodLogs;
    private IFoodLogPersistence mockFoodLogPersistence;
    private IFoodPersistence mockFoodPersistence;

    @Before
    public void setUp() {
        mockFoodLogPersistence = mock(IFoodLogPersistence.class);
        mockFoodPersistence = mock(IFoodPersistence.class);
        accessFoodLogs = new AccessFoodLogs(mockFoodLogPersistence, mockFoodPersistence);
    }

    @Test
    public void testGetFoodLogByUserDate() {
        FoodLog log1 = new FoodLog(1, 2, "2025-03-19", 100);
        FoodLog log2 = new FoodLog(1, 3, "2025-03-19", 200);

        when(mockFoodLogPersistence.getFoodLogByUserDate(1, "2025-03-19"))
                .thenReturn(Arrays.asList(log1, log2));

        List<FoodLog> result = accessFoodLogs.getFoodLogByUserDate(1, "2025-03-19");

        assertEquals(2, result.size());
        assertEquals(2, result.get(0).getFoodId());
        assertEquals(3, result.get(1).getFoodId());
    }

    @Test
    public void testGetFoodLogByUserDateEmpty() {
        when(mockFoodLogPersistence.getFoodLogByUserDate(1, "2025-03-19")).thenReturn(Collections.emptyList());

        List<FoodLog> result = accessFoodLogs.getFoodLogByUserDate(1, "2025-03-19");

        assertTrue(result.isEmpty());
    }

    @Test
    public void testInsertFoodLogValid() throws InvalidFoodLogException {
        FoodLog newLog = new FoodLog(1, 2, "2025-03-19", 150);

        accessFoodLogs.insertFoodLog(newLog);

        verify(mockFoodLogPersistence, times(1)).addFoodLog(newLog);
    }

    @Test
    public void testInsertFoodLogInvalidThrowsException() {
        FoodLog invalidLog = new FoodLog(1, -1, "2025-03-19", 150); // Invalid food ID

        assertThrows(InvalidFoodLogException.class, () -> accessFoodLogs.insertFoodLog(invalidLog));
    }

    @Test
    public void testGetUserTotalDailyIntake() {
        FoodLog log1 = new FoodLog(1, 2, "2025-03-19", 100);
        FoodLog log2 = new FoodLog(1, 3, "2025-03-19", 200);

        when(mockFoodLogPersistence.getFoodLogByUserDate(1, "2025-03-19"))
                .thenReturn(Arrays.asList(log1, log2));

        when(mockFoodPersistence.getFoodByID(2)).thenReturn(new Food(2, "Apple", 2));
        when(mockFoodPersistence.getFoodByID(3)).thenReturn(new Food(3, "Banana", 3));

        int totalCalories = accessFoodLogs.getUserTotalDailyIntake(1, "2025-03-19");

        assertEquals((100 * 2) + (200 * 3), totalCalories); // (100g * 2 cal) + (200g * 3 cal)
    }

    @Test
    public void testGetUserTotalDailyIntakeNoLogs() {
        when(mockFoodLogPersistence.getFoodLogByUserDate(1, "2025-03-19")).thenReturn(Collections.emptyList());

        int totalCalories = accessFoodLogs.getUserTotalDailyIntake(1, "2025-03-19");

        assertEquals(0, totalCalories);
    }
}