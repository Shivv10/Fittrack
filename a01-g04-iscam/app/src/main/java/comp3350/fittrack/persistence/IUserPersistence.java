package comp3350.fittrack.persistence;

import comp3350.fittrack.objects.User;

/** **************************************************
 CLASS NAME: IUserPersistence.java

 Interface for handling persistence of User objects.

 ************************************************** */
public interface IUserPersistence {
    // Register a new user
    int register(User user);

    // Retrieve user by ID
    User getUserById(int id);

    // Update existing user
    boolean updateUser(int userID, User user);

    // Remove a user
    boolean removeUser(int id);

    // Log in a user (returns user ID or -1)
    int logIn(String username, String password);
}
