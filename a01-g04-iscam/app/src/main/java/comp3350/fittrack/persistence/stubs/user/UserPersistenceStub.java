package comp3350.fittrack.persistence.stubs.user;

import comp3350.fittrack.objects.User;
import comp3350.fittrack.persistence.IUserPersistence;

import java.util.ArrayList;
import java.util.List;

/** **************************************************
 CLASS NAME: UserPersistenceStub.java

 Stub persistence for user accounts, using in-memory list.

 ************************************************** */

public class UserPersistenceStub implements IUserPersistence {

    private List<User> users;

    public UserPersistenceStub() {
        this.users = new ArrayList<>();
        users.add(new User(18, 133.5, 13, "password", 133.5, "John2012", "John"));
        users.add(new User(19, 133.5, 15, "password", 133.5, "John2013", "John"));
        users.add(new User(20, 133.5, 11, "password", 133.5, "John2014", "John"));
        users.add(new User(21, 133.5,  12, "password", 133.5, "John2015", "John"));
        users.add(new User(22, 133.5,  14, "password", 133.5, "John2016", "John"));
    }
    @Override
    public int register(User user) {
        users.add(user);
        return user.getID();
    }

    @Override
    public int logIn(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user.getID();
            }
        }
        return -1;
    }
    @Override
    public User getUserById(int id) {
        for (User user : users) {
            if (user.getID() == id) {
                return user;
            }
        }
        return null;
    }

    @Override
    public boolean updateUser(int userID, User user) {
        boolean updated = false;
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getID() == userID) {
                users.set(i, user);  // Update the user in the list at the specific index
                updated = true;
                break;  // Exit the loop once the user is updated
            }
        }
        return updated;
    }

    @Override
    public boolean removeUser(int id) {
        for (User user : users) {
            if (user.getID() == id) {
                users.remove(user);
                return true;
            }
        }
        return false;
    }
}