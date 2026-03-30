package comp3350.srsys.tests.unittests.business;

import comp3350.fittrack.business.food.AccessFood;
import comp3350.fittrack.business.food.exceptions.InvalidFoodException;
import comp3350.fittrack.objects.Food;
import comp3350.fittrack.persistence.IFoodPersistence;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class AccessFoodTest {

    private AccessFood accessFood;
    private IFoodPersistence mockPersistence;

    @Before
    public void setUp() {
        mockPersistence = mock(IFoodPersistence.class);
        accessFood = new AccessFood(mockPersistence); // Constructor injection for easy testing
    }

    @Test
    public void testAddFoodValid() {
        when(mockPersistence.getFoodByFoodName("Apple")).thenReturn(null); // No duplicate exists
        when(mockPersistence.addFood(any(Food.class))).thenReturn(1); // Simulate DB assigning ID 1

        int newId = accessFood.addFood(-1, "Apple", 95);
        assertEquals(1, newId);

        verify(mockPersistence, times(1)).addFood(any(Food.class));
    }

    @Test
    public void testAddFoodDuplicateThrowsException() {
        when(mockPersistence.getFoodByFoodName("Banana")).thenReturn(new Food(2, "Banana", 110));

        assertThrows(InvalidFoodException.class, () -> accessFood.addFood(-1, "Banana", 110));
    }

    @Test
    public void testSearchByFoodIDExists() {
        Food mockFood = new Food(3, "Carrot", 25);
        when(mockPersistence.getFoodByID(3)).thenReturn(mockFood);

        Food result = accessFood.searchByFoodID(3);
        assertNotNull(result);
        assertEquals("Carrot", result.getName());
        assertEquals(25, result.getCalories());
    }

    @Test
    public void testSearchByFoodIDNotFound() {
        when(mockPersistence.getFoodByID(999)).thenReturn(null);
        assertNull(accessFood.searchByFoodID(999));
    }
}
