package comp3350.fittrack.persistence.hsqldb.food;

import comp3350.fittrack.objects.FoodLog;
import comp3350.fittrack.persistence.IFoodLogPersistence;
import comp3350.fittrack.persistence.hsqldb.exceptions.HSQLDBException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/** **************************************************
 CLASS NAME: FoodLogPersistenceHSQLDB.java

 Manages persistence of FoodLog entries in HSQLDB.

 ************************************************** */

public class FoodLogPersistenceHSQLDB implements IFoodLogPersistence {

    private final String dbPath;
    private static final String TAG = "FoodLogPersistenceHSQL";

    public FoodLogPersistenceHSQLDB(final String dbPath) {
        this.dbPath = dbPath;
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true;hsqldb.lock_file=false", "SA", "");
    }

    private FoodLog fromResultSet(ResultSet rs) throws SQLException {
        int userId = rs.getInt("uid");
        int foodId = rs.getInt("fid");
        String dateConsumed = rs.getString("date");
        int quantity = rs.getInt("quantity");
        return new FoodLog(userId, foodId, dateConsumed, quantity);
    }

    @Override
    public boolean addFoodLog(FoodLog foodLog) {
        boolean result = false;
        try (final Connection c = connect()) {
            final PreparedStatement st = c.prepareStatement("INSERT INTO FOODLOGS VALUES(?, ?, ?, ?)");
            st.setInt(1, foodLog.getUserId());     // uid (INTEGER)
            st.setInt(2, foodLog.getFoodId());     // fid (INTEGER)
            st.setString(3, foodLog.getDate());    // date (VARCHAR(10))
            st.setInt(4, foodLog.getQuantity());   // quantity (INTEGER)
            st.executeUpdate();
            st.close();
            result = true;
        } catch (final SQLException e) {
            throw new HSQLDBException(e);
        }
        return result;
    }
    @Override
    public List<FoodLog> getFoodLogsByUserId(int userId) {
        List<FoodLog> logs = new ArrayList<>();
        try (Connection c = connect()) {
            final PreparedStatement st = c.prepareStatement("SELECT * FROM FOODLOGS WHERE uid = ?");
            st.setInt(1, userId);
            final ResultSet rs = st.executeQuery();
            while (rs.next()) {
                final FoodLog foodLog = fromResultSet(rs);
                logs.add(foodLog);
            }
            rs.close();
            st.close();
        } catch (final SQLException e) {
            throw new HSQLDBException(e);
        }
        return logs;
    }

    @Override
    public List<FoodLog> getFoodLogByUserDate(int userID, String date) {
        List<FoodLog> logs = new ArrayList<>();
        try (Connection c = connect()) {
            final PreparedStatement st = c.prepareStatement("SELECT * FROM FOODLOGS WHERE uid = ? AND date = ?");
            st.setInt(1, userID);
            st.setString(2, date);
            final ResultSet rs = st.executeQuery();
            while (rs.next()) {
                final FoodLog foodLog = fromResultSet(rs);
                logs.add(foodLog);
            }
            rs.close();
            st.close();
        } catch (final SQLException e) {
            throw new HSQLDBException(e);
        }
        return logs;
    }

    @Override
    public FoodLog getFoodLog(int userID, int foodID, String date) {
        try (Connection c = connect()) {
            final PreparedStatement st = c.prepareStatement("SELECT * FROM FOODLOGS WHERE uid = ? AND fid = ? AND date = ?");
            st.setInt(1, userID);
            st.setInt(2, foodID);
            st.setString(3, date);
            final ResultSet rs = st.executeQuery();
            if (rs.next()) {
                FoodLog foodLog = fromResultSet(rs);
                rs.close();
                st.close();
                return foodLog;
            }
            rs.close();
            st.close();
        } catch (final SQLException e) {
            throw new HSQLDBException(e);
        }
        return null;
    }

    public boolean updateFoodLog(int userID, int foodID, String date, FoodLog updatedLog) {
        boolean result = false;
        try (final Connection c = connect()) {
            final PreparedStatement st = c.prepareStatement("UPDATE FOODLOGS SET uid=?, fid=?, date=?, quantity=? WHERE uid=? AND fid=? AND date=?");
            st.setInt(1, updatedLog.getUserId());
            st.setInt(2, updatedLog.getFoodId());
            st.setString(3, updatedLog.getDate());
            st.setInt(4, updatedLog.getQuantity());
            st.setInt(5, userID);
            st.setInt(6, foodID);
            st.setString(7, date);
            st.executeUpdate();
            st.close();
            result = true;
        } catch (final SQLException e) {
            throw new HSQLDBException(e);
        }
        return result;
    }

    public void deleteFoodLog(int userID, int foodID, String date) {
        try (final Connection c = connect()) {
            final PreparedStatement st = c.prepareStatement("DELETE FROM FOODLOGS WHERE uid = ? AND fid = ? AND date = ?");
            st.setInt(1, userID);
            st.setInt(2, foodID);
            st.setString(3, date);
            st.executeUpdate();
            st.close();
        } catch (final SQLException e) {
            throw new HSQLDBException(e);
        }
    }

    public boolean deleteLogsByFoodId(int foodID) {
        boolean result = false;
        try (final Connection c = connect()) {
            final PreparedStatement st = c.prepareStatement("DELETE FROM FOODLOGS WHERE fid = ?");
            st.setInt(1, foodID);
            st.executeUpdate();
            st.close();
            result = true;
        } catch (final SQLException e) {
            throw new HSQLDBException(e);
        }
        return result;
    }
}