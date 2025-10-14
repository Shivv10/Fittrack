package comp3350.srsys.tests.integration;

import comp3350.fittrack.business.user.AccessUser;
import comp3350.fittrack.business.user.exceptions.InvalidInputException;
import comp3350.fittrack.objects.User;
import comp3350.fittrack.persistence.hsqldb.user.UserPersistenceHSQLDB;
import comp3350.srsys.tests.utils.TestUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class AccessUserIT {
    private AccessUser accessUser;
    private String dbPath;

    @Before
    public void setUp() throws IOException {
        // Copy the production database script to a temporary file.
        File tempDB = TestUtils.copyDB();
        // Remove the ".script" extension to get the actual database path.
        dbPath = tempDB.getAbsolutePath().replace(".script", "");
        // Create the persistence layer using the temporary database.
        accessUser = new AccessUser(new UserPersistenceHSQLDB(dbPath));
    }

    @Test
    public void testRegisterAndGetUser() throws InvalidInputException {
        System.out.println("\nStarting testRegisterAndGetUser");
        // Register a new user using valid inputs.
        // Using password "Password123!" which contains lowercase, uppercase, digit, and a special character.
        int userId = accessUser.registerUser("John Doe", "johnd", "Password123!", "175",  "65", 30);
        assertTrue("User ID should be positive", userId >= 0);
        // Retrieve the user and check key fields.
        User user = accessUser.getUserById(userId);
        assertNotNull("Retrieved user should not be null", user);
        assertEquals("Username should be 'johnd'", "johnd", user.getUsername());
        assertEquals("Name should be 'John Doe'", "John Doe", user.getName());
        System.out.println("Finished testRegisterAndGetUser");
    }

    @Test
    public void testLogIn() throws InvalidInputException {
        System.out.println("\nStarting testLogIn");
        // First, register a user.
        // Using password "MyPassword1!" which satisfies all password requirements.
        int userId = accessUser.registerUser("Jane Doe", "janed", "MyPassword1!", "165", "60", 28);
        assertTrue("User ID should be positive", userId >= 0);
        // Attempt to log in with correct credentials.
        int loginId = accessUser.logIn("janed", "MyPassword1!");
        assertEquals("Login should return the registered user ID", userId, loginId);
        // Attempt to log in with an incorrect password.
        int wrongLogin = accessUser.logIn("janed", "WrongPassword1!");
        assertEquals("Login with wrong password should return -1", -1, wrongLogin);
        System.out.println("Finished testLogIn");
    }

    @Test
    public void testUpdateUser() throws InvalidInputException {
        System.out.println("\nStarting testUpdateUser");
        // Register a new user.
        // Using password "Secret123@" which satisfies all requirements.
        int userId = accessUser.registerUser("Bob Smith", "bobsmith", "Secret123@", "180", "80", 35);
        User user = accessUser.getUserById(userId);
        assertNotNull("User should be registered", user);
        // Update the user's name.
        User updatedUser = new User(user.getAge(), user.getWeight(), user.getID(), user.getPassword(), user.getHeight(), user.getUsername(), "Robert Smith");
        boolean updated = accessUser.updateUser(updatedUser);
        assertTrue("User update should succeed", updated);
        User checkUser = accessUser.getUserById(userId);
        assertEquals("User name should be updated to 'Robert Smith'", "Robert Smith", checkUser.getName());
        System.out.println("Finished testUpdateUser");
    }

    @Test
    public void testDeleteUser() throws InvalidInputException {
        System.out.println("\nStarting testDeleteUser");
        // Register a new user.
        // Using password "AlicePass1#" which satisfies all requirements.
        int userId = accessUser.registerUser("Alice Johnson", "alicej", "AlicePass1#", "160", "50", 27);
        User user = accessUser.getUserById(userId);
        assertNotNull("User should exist before deletion", user);
        // Delete the user.
        boolean deleted = accessUser.deleteUser(userId);
        assertTrue("User deletion should return true", deleted);
        User deletedUser = accessUser.getUserById(userId);
        assertNull("User should be null after deletion", deletedUser);
        System.out.println("Finished testDeleteUser");
    }
}
