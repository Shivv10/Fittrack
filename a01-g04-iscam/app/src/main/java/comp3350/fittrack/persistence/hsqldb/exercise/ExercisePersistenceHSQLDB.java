package comp3350.fittrack.persistence.hsqldb.exercise;

import comp3350.fittrack.persistence.hsqldb.exceptions.HSQLDBException;
import comp3350.fittrack.objects.Exercise;
import comp3350.fittrack.persistence.IExercisePersistence;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/** **************************************************
 CLASS NAME: ExercisePersistenceHSQLDB.java

 Handles persistence of Exercise objects using HSQLDB.

 ************************************************** */

public class ExercisePersistenceHSQLDB implements IExercisePersistence {
    private final String dbPath;

    public ExercisePersistenceHSQLDB(final String dbPath) {
        this.dbPath = dbPath;
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true;hsqldb.lock_file=false", "SA", "");
    }

    private Exercise fromResultSet(ResultSet rs) throws SQLException {
        int exerciseID = rs.getInt("id");
        String name = rs.getString("name");
        int caloriesBurned = rs.getInt("caloriesBurned");
        return new Exercise(exerciseID, name, caloriesBurned);
    }

    @Override
    public List<Exercise> getExerciseList() {
        List<Exercise> exerciseList = new ArrayList<>();
        try (final Connection c = connect()) {
            final Statement st = c.createStatement();
            final ResultSet rs = st.executeQuery("SELECT * FROM EXERCISES");
            while (rs.next()) {
                exerciseList.add(fromResultSet(rs));
            }
            rs.close();
            st.close();
        } catch (final SQLException e) {
            throw new HSQLDBException(e);
        }
        return exerciseList;
    }

    @Override
    public Exercise getExerciseById(int exerciseID) {
        try (Connection c = connect()) {
            final PreparedStatement st = c.prepareStatement("SELECT * FROM EXERCISES WHERE id = ?");
            st.setInt(1, exerciseID);
            final ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return fromResultSet(rs);
            }
            rs.close();
            st.close();
        } catch (final SQLException e) {
            throw new HSQLDBException(e);
        }
        return null;
    }

    @Override
    public Exercise getExerciseByName(String exerciseName) {
        try (Connection c = connect()) {
            final PreparedStatement st = c.prepareStatement("SELECT * FROM EXERCISES WHERE name = ?");
            st.setString(1, exerciseName);
            final ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return fromResultSet(rs);
            }
            rs.close();
            st.close();
        } catch (final SQLException e) {
            throw new HSQLDBException(e);
        }
        return null;
    }

    @Override
    public Exercise insertExercise(Exercise newExercise) {
        try (final Connection c = connect()) {
            final PreparedStatement st = c.prepareStatement(
                    "INSERT INTO EXERCISES (name, caloriesBurned) VALUES (?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            st.setString(1, newExercise.getName());
            st.setInt(2, newExercise.getCaloriesBurned());
            st.executeUpdate();

            final ResultSet rs = st.getGeneratedKeys();
            if (rs.next()) {
                int generatedID = rs.getInt(1);
                rs.close();
                st.close();
                return new Exercise(generatedID, newExercise.getName(), newExercise.getCaloriesBurned());
            } else {
                throw new HSQLDBException(new SQLException("Failed to retrieve generated ID."));
            }

        } catch (final SQLException e) {
            throw new HSQLDBException(e);
        }
    }


    @Override
    public void updateExercise(int exerciseID, Exercise updatedExercise) {
        try (final Connection c = connect()) {
            final PreparedStatement st = c.prepareStatement("UPDATE EXERCISES SET name = ?, caloriesBurned = ? WHERE id = ?");
            st.setString(1, updatedExercise.getName());
            st.setInt(2, updatedExercise.getCaloriesBurned());
            st.setInt(3, exerciseID);
            st.executeUpdate();
            st.close();
        } catch (final SQLException e) {
            throw new HSQLDBException(e);
        }
    }

    @Override
    public void deleteExercise(int exerciseID) {
        try (final Connection c = connect()) {
            final PreparedStatement st = c.prepareStatement("DELETE FROM EXERCISES WHERE id = ?");
            st.setInt(1, exerciseID);
            st.executeUpdate();
            st.close();
        } catch (final SQLException e) {
            throw new HSQLDBException(e);
        }
    }
}