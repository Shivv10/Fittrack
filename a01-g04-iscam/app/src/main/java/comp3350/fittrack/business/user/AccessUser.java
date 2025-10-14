package comp3350.fittrack.business.user;

import comp3350.fittrack.application.Services;
import comp3350.fittrack.business.utils.ValidationUtils;
import comp3350.fittrack.business.user.exceptions.InvalidInputException;
import comp3350.fittrack.objects.User;
import comp3350.fittrack.persistence.IUserPersistence;

/** **************************************************
 CLASS NAME: AccessUser.java

 CLASS FUNCTION: the class that interacts with Users in the persistence layer

 ************************************************** */

public class AccessUser {

    private IUserPersistence userPersistence;

    /**
     * Constructor that allows dependency injection of a specific user persistence implementation.
     * @param userPersistence The persistence layer to be used for user data operations.
     */
    public AccessUser(IUserPersistence userPersistence) {
        this.userPersistence = userPersistence;
    }

    /**
     * Default constructor that initializes the user persistence with a database connection.
     */
    public AccessUser() {
        this.userPersistence = Services.getUserPersistence();
    }

    /**
     * Registers a new user by validating input data and storing the user in the database.
     * @param name The name of the user.
     * @param username The username for login.
     * @param password The user's password.
     * @param height_in The user's height as a string.
     * @param weight_in The user's weight as a string.
     * @param age The user's age.
     * @return The user ID assigned by the database.
     * @throws InvalidInputException if any input fails validation.
     */
    public int registerUser(String name, String username, String password, String height_in, String weight_in, int age) throws InvalidInputException {
        name = ValidationUtils.validateInput(name, "name");
        username = ValidationUtils.validateInput(username, "username");
        // Password validation is more complex, so we use a separate method
        password = ValidationUtils.validatePassword(password);
        double height = ValidationUtils.parseDouble(height_in, "height", 50, 272);
        double weight = ValidationUtils.parseDouble(weight_in, "weight", 2, 635);
        User user = new User(age, weight, 0, // ID set to 0, assigned by database
                password, height, username, name);

        return userPersistence.register(user);
    }

    /**
     * Attempts to log in a user using the provided credentials.
     * @param username The username entered by the user.
     * @param password The password entered by the user.
     * @return The user ID if login is successful, otherwise -1.
     */
    public int logIn(String username, String password) {
        return userPersistence.logIn(username, password);
    }

    /**
     * Updates an existing user's information in the database.
     * @param user The user object containing updated information.
     * @return True if the update is successful, otherwise false.
     */
    public boolean updateUser(User user) {
        return userPersistence.updateUser(user.getID(), user);
    }

    /**
     * Retrieves a user from the database by their ID.
     * @param id The unique ID of the user.
     * @return The User object if found, otherwise null.
     */
    public User getUserById(int id) {
        return userPersistence.getUserById(id);
    }

    public boolean deleteUser(int userID) {
        return userPersistence.removeUser(userID);
    }

}
