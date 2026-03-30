package comp3350.srsys.tests.unittests.business;

import comp3350.fittrack.business.user.AccessUser;
import comp3350.fittrack.business.user.exceptions.InvalidInputException;
import comp3350.fittrack.objects.User;
import comp3350.fittrack.persistence.IUserPersistence;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


public class AccessUserTest {

    @Mock
    private IUserPersistence mockUserPersistence;

    private AccessUser accessUser;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);  // Initialize the mocks
        accessUser = new AccessUser(mockUserPersistence);  // Inject the mocked persistence
    }

    @Test
    public void testRegisterUserValid() throws InvalidInputException {
        // Set up the data
        String name = "John Doe";
        String username = "johndoe";
        String password = "Password123!";
        String height_in = "180";
        String weight_in = "75";
        int age = 30;
        // Mock the database call (registering a user returns user ID 1)
        when(mockUserPersistence.register(any(User.class))).thenReturn(1);

        // Call the register method
        int userId = accessUser.registerUser(name, username, password, height_in, weight_in, age);

        // Verify the result
        assertEquals(1, userId);
        verify(mockUserPersistence, times(1)).register(any(User.class));  // Ensure the register method was called once
    }

    @Test
    public void testRegisterUserInvalidInput() {
        // Set up invalid data (e.g., empty username)
        String name = "John Doe";
        String username = "";
        String password = "Password123!";
        String height_in = "180";
        String weight_in = "75";
        int age = 30;

        // Mock that the validation will throw an exception
        assertThrows(InvalidInputException.class, () -> {
            accessUser.registerUser(name, username, password, height_in, weight_in, age);
        });
    }

    @Test
    public void testLogInSuccessful() {
        // Set up mock login behavior
        when(mockUserPersistence.logIn("johndoe", "Password123!")).thenReturn(1);

        // Call the logIn method
        int userId = accessUser.logIn("johndoe", "Password123!");

        // Verify the result
        assertEquals(1, userId);
        verify(mockUserPersistence, times(1)).logIn("johndoe", "Password123!");
    }

    @Test
    public void testLogInFailed() {
        // Set up mock failed login behavior (returns -1 for invalid credentials)
        when(mockUserPersistence.logIn("johndoe", "WrongPassword")).thenReturn(-1);

        // Call the logIn method
        int userId = accessUser.logIn("johndoe", "WrongPassword");

        // Verify the result
        assertEquals(-1, userId);
        verify(mockUserPersistence, times(1)).logIn("johndoe", "WrongPassword");
    }

    @Test
    public void testUpdateUserValid() {
        User user = new User(30, 75, 1, "Password123!", 180, "johndoe", "John Doe");
        // Mock update behavior
        when(mockUserPersistence.updateUser(user.getID(), user)).thenReturn(true);

        // Call the update method
        boolean result = accessUser.updateUser(user);

        // Verify the result
        assertTrue(result);
        verify(mockUserPersistence, times(1)).updateUser(user.getID(), user);
    }

    @Test
    public void testUpdateUserFailed() {
        User user = new User(30, 75, 1, "Password123!", 180, "johndoe", "John Doe");

        // Mock failed update behavior
        when(mockUserPersistence.updateUser(user.getID(), user)).thenReturn(false);

        // Call the update method
        boolean result = accessUser.updateUser(user);

        // Verify the result
        assertFalse(result);
        verify(mockUserPersistence, times(1)).updateUser(user.getID(), user);
    }

    @Test
    public void testGetUserById() {
        User mockUser = new User(30, 75, 1, "Password123!", 180, "johndoe", "John Doe");
        // Mock the getUserById method to return a mock user
        when(mockUserPersistence.getUserById(1)).thenReturn(mockUser);

        // Call the method
        User user = accessUser.getUserById(1);

        // Verify the result
        assertNotNull(user);
        assertEquals(1, user.getID());
        verify(mockUserPersistence, times(1)).getUserById(1);
    }

    @Test
    public void testGetUserByIdNotFound() {
        // Mock the getUserById method to return null
        when(mockUserPersistence.getUserById(1)).thenReturn(null);

        // Call the method
        User user = accessUser.getUserById(1);

        // Verify the result
        assertNull(user);
        verify(mockUserPersistence, times(1)).getUserById(1);
    }
}

