package comp3350.srsys.tests.unittests.persistence;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import comp3350.fittrack.objects.User;
import comp3350.fittrack.persistence.stubs.user.UserPersistenceStub;

public class UserPersistenceStubTest {

    private UserPersistenceStub userPersistence;

    @Before
    public void setUp() {
        userPersistence = new UserPersistenceStub();
    }

    @Test
    public void testRegister() {
        User newUser = new User(20, 140.0, 23, "password", 140.0, "John2017", "John");
        int userId = userPersistence.register(newUser);

        // Assert that the new user is successfully added and has the correct ID
        assertEquals(23, userId);
        assertNotNull(userPersistence.getUserById(userId));
    }

    @Test
    public void testLogInSuccess() {
        // User with username "John2012" and password "password" should be able to log in
        int userId = userPersistence.logIn("John2012", "password");

        // Assert that the user ID returned is correct
        assertEquals(13, userId);
    }

    @Test
    public void testLogInFailure() {
        // Attempting to log in with incorrect credentials
        int userId = userPersistence.logIn("InvalidUsername", "wrongPassword");

        // Assert that the login fails and returns -1
        assertEquals(-1, userId);
    }

    @Test
    public void testGetUserById() {
        User user = userPersistence.getUserById(12);

        // Assert that the user with ID 19 is returned correctly
        assertNotNull(user);
        assertEquals(12, user.getID());
        assertEquals("John2015", user.getUsername());
    }

    @Test
    public void testUpdateUser() {
        User updatedUser = new User(20, 140.0, 15, "newpassword", 140.0, "John2013", "John");

        // Update the user with ID 19
        boolean updated = userPersistence.updateUser(15, updatedUser);

        // Assert that the update was successful
        assertTrue(updated);

        // Assert that the user with ID 19 has been updated with the new data
        User user = userPersistence.getUserById(15);
        assertEquals("newpassword", user.getPassword());
    }

    @Test
    public void testUpdateUserFailure() {
        // Attempting to update a non-existing user (ID 99)
        User updatedUser = new User(20, 140.0,  99, "newpassword", 140.0, "John2013", "John");
        boolean updated = userPersistence.updateUser(99, updatedUser);

        // Assert that the update fails because user with ID 99 doesn't exist
        assertFalse(updated);
    }

    @Test
    public void testRemoveUser() {
        // Remove user with ID 21
        boolean removed = userPersistence.removeUser(15);

        // Assert that the user was successfully removed
        assertTrue(removed);

        // Assert that the user with ID 21 no longer exists
        assertNull(userPersistence.getUserById(21));
    }

    @Test
    public void testRemoveUserFailure() {
        // Attempting to remove a non-existing user (ID 99)
        boolean removed = userPersistence.removeUser(99);

        // Assert that the removal fails because the user doesn't exist
        assertFalse(removed);
    }
}


