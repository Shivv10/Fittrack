package comp3350.fittrack.persistence.hsqldb.exercise;

import comp3350.fittrack.objects.ExerciseLog;
import comp3350.fittrack.persistence.IExerciseLogPersistence;
import comp3350.fittrack.persistence.hsqldb.exceptions.HSQLDBException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/** **************************************************
 CLASS NAME: ExerciseLogPersistenceHSQLDB.java

 Handles persistence of ExerciseLog objects using HSQLDB.

 ************************************************** */


public class ExerciseLogPersistenceHSQLDB implements IExerciseLogPersistence {

    private final String dbPath;

    public ExerciseLogPersistenceHSQLDB(final String dbPath) {
        this.dbPath = dbPath;
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true;hsqldb.lock_file=false", "SA", "");
    }
    private ExerciseLog fromResultSet(ResultSet rs) throws SQLException {
        int userId = rs.getInt("uid");
        int exerciseID = rs.getInt("eid");
        int durationMinutes = rs.getInt("minutes");
        String datePerformed = rs.getString("date");
        return new ExerciseLog(userId, exerciseID, durationMinutes, datePerformed);
    }

    @Override
    public boolean addExerciseLog(ExerciseLog exerciseLog) {
        boolean result = false;
        try (final Connection c = connect()) {
            final PreparedStatement st = c.prepareStatement("INSERT INTO EXERCISELOGS VALUES(?, ?, ?, ?)");
            st.setInt(1, exerciseLog.getUserId());
            st.setInt(2, exerciseLog.getExerciseId());
            st.setString(3, exerciseLog.getDatePerformed());
            st.setInt(4, exerciseLog.getDurationMinutes());
            st.executeUpdate();
            st.close();
            result = true;
        } catch (final SQLException e) {
            throw new HSQLDBException(e);
        }
        return result;
    }

    @Override
    public boolean updateExerciseLog(int userID, int exerciseID, String date, ExerciseLog updatedLog) {
            boolean result = false;
        try (final Connection c = connect()) {
            final PreparedStatement st = c.prepareStatement("UPDATE EXERCISELOGS SET uid=?, eid=?, date=?, minutes=? where uid=? AND eid=? AND date=?");
            st.setInt(1, updatedLog.getUserId());
            st.setInt(2, updatedLog.getExerciseId());
            st.setString(3, updatedLog.getDatePerformed());
            st.setInt(4, updatedLog.getDurationMinutes());
            st.setInt(5, userID);
            st.setInt(6, exerciseID);
            st.setString(7, date);
            st.executeUpdate();
            st.close();
            result = true;
        } catch (final SQLException e) {
            throw new HSQLDBException(e);
        }
        return result;
    }

    @Override
    public List<ExerciseLog> getExerciseLogsByUserId(int userId) {
        List<ExerciseLog> logs = new ArrayList<>();
        try (Connection c = connect()) {
            final PreparedStatement st = c.prepareStatement("SELECT * FROM EXERCISELOGS WHERE uid = ?");
            st.setString(1, Integer.toString(userId));
            final ResultSet rs = st.executeQuery();
            while (rs.next()) {
                final ExerciseLog exerciseLog = fromResultSet(rs);
                logs.add(exerciseLog);
            }
            rs.close();
            st.close();

        } catch (final SQLException e) {
            throw new HSQLDBException(e);
        }

        return logs;
    }
    @Override
    public boolean deleteExerciseLog(int userID, int exerciseID, String date) {
        boolean result = false;
        try (final Connection c = connect()) {
            final PreparedStatement st = c.prepareStatement("DELETE FROM EXERCISELOGS WHERE uid = ? AND eid = ? AND date = ?");
            st.setString(1, Integer.toString(userID));
            st.setString(2, Integer.toString(exerciseID));
            st.setString(3, date);
            st.executeUpdate();
            st.close();
            result = true;
        } catch (final SQLException e) {
            throw new HSQLDBException(e);
        }
        return result;
    }

    public boolean deleteLogsByExerciseId(int exerciseId) {
        boolean result = false;
        try (final Connection c = connect()) {
            final PreparedStatement st = c.prepareStatement("DELETE FROM EXERCISELOGS WHERE eid = ?");
            st.setInt(1, exerciseId);
            st.executeUpdate();
            st.close();
            result = true;
        } catch (final SQLException e) {
            throw new HSQLDBException(e);
        }
        return result;
    }

}
