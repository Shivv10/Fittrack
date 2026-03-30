package comp3350.fittrack.persistence.hsqldb.user;

import comp3350.fittrack.objects.User;
import comp3350.fittrack.persistence.IUserPersistence;
import comp3350.fittrack.persistence.hsqldb.exceptions.HSQLDBException;

import java.sql.*;

/** **************************************************
 CLASS NAME: UserPersistenceHSQLDB.java

 Manages persistence of User accounts using HSQLDB.

 ************************************************** */

public class UserPersistenceHSQLDB implements IUserPersistence {

    private final String dbPath;

    /**
     * Constructor that initializes the database path.
     * @param dbPath The path to the HSQLDB database file.
     */
    public UserPersistenceHSQLDB(final String dbPath) {
        this.dbPath = dbPath;
    }

    private Connection connect() throws SQLException {
        String dbUrl = "jdbc:hsqldb:file:" + dbPath + ";shutdown=true;hsqldb.lock_file=false";
        return DriverManager.getConnection(dbUrl, "SA", "");
    }

    @Override
    public int logIn(String username, String password) {
        String trimmedUsername = username.trim().toLowerCase();
        String trimmedPassword = password.trim().toLowerCase();
        try (Connection c = connect();
             PreparedStatement pstmt = c.prepareStatement("SELECT id FROM USERS WHERE LOWER(username) = ? AND LOWER(password) = ?")) {
            pstmt.setString(1, trimmedUsername);
            pstmt.setString(2, trimmedPassword);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        } catch (SQLException e) {
        }
        return -1;
    }

    @Override
    public User getUserById(int userID) {
        try (Connection c = connect()) {
            final PreparedStatement st = c.prepareStatement("SELECT * FROM USERS WHERE id = ?");
            st.setInt(1, userID);
            final ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return fromResultSet(rs);
            }
        } catch (final SQLException e) {
            throw new HSQLDBException(e);
        }
        return null;
    }

    @Override
    public int register(User user) {
        try (final Connection c = connect()) {
            final PreparedStatement st = c.prepareStatement(
                    "INSERT INTO USERS (age, weight, height, password, username, name) VALUES (?, ?, ?, ?, ?, ?)",
                    PreparedStatement.RETURN_GENERATED_KEYS
            );
            st.setInt(1, user.getAge());
            st.setDouble(2, user.getWeight());
            st.setDouble(3, user.getHeight());
            st.setString(4, user.getPassword());
            st.setString(5, user.getUsername());
            st.setString(6, user.getName());
            st.executeUpdate();

            ResultSet rs = st.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            } else {
                throw new SQLException("Failed to retrieve generated ID.");
            }
        } catch (final SQLException e) {
            throw new HSQLDBException(e);
        }
    }

    private User fromResultSet(final ResultSet rs) throws SQLException {
        int userID = rs.getInt("id");
        String username = rs.getString("username");
        String password = rs.getString("password");
        String name = rs.getString("name");
        int age = rs.getInt("age");
        double weight = rs.getDouble("weight");
        double height = rs.getDouble("height");
        return new User(age, weight,  userID, password, height, username, name);
    }

    @Override
    public boolean updateUser(int userID, User user) {
        try (final Connection c = connect()) {
            final PreparedStatement st = c.prepareStatement(
                    "UPDATE USERS SET age = ?, weight = ?, height = ?, password = ?, username = ?, name = ? WHERE id = ?"
            );
            st.setInt(1, user.getAge());
            st.setDouble(2, user.getWeight());
            st.setDouble(3, user.getHeight());
            st.setString(4, user.getPassword());
            st.setString(5, user.getUsername());
            st.setString(6, user.getName());
            st.setInt(7, userID);
            st.executeUpdate();
            return true;
        } catch (final SQLException e) {
            throw new HSQLDBException(e);
        }
    }

    @Override
    public boolean removeUser(int userID) {
        try (final Connection c = connect()) {
            PreparedStatement deleteLogs = c.prepareStatement("DELETE FROM FOODLOGS WHERE uid = ?");
            deleteLogs.setInt(1, userID);
            deleteLogs.executeUpdate();

            PreparedStatement deleteExLogs = c.prepareStatement("DELETE FROM EXERCISELOGS WHERE uid = ?");
            deleteExLogs.setInt(1, userID);
            deleteExLogs.executeUpdate();

            PreparedStatement deleteUser = c.prepareStatement("DELETE FROM USERS WHERE id = ?");
            deleteUser.setInt(1, userID);
            deleteUser.executeUpdate();

            return true;
        } catch (final SQLException e) {
            throw new HSQLDBException(e);
        }
    }
}
