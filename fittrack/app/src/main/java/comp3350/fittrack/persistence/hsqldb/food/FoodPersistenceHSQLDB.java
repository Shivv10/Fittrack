package comp3350.fittrack.persistence.hsqldb.food;

import comp3350.fittrack.objects.Food;
import comp3350.fittrack.persistence.IFoodPersistence;
import comp3350.fittrack.persistence.hsqldb.exceptions.HSQLDBException;

import java.sql.*;
import java.util.ArrayList;

/** **************************************************
 CLASS NAME: FoodPersistenceHSQLDB.java

 Handles food object storage and retrieval via HSQLDB.

 ************************************************** */

public class FoodPersistenceHSQLDB implements IFoodPersistence {
    private final String dbPath;

    public FoodPersistenceHSQLDB(final String dbPath) {
        this.dbPath = dbPath;
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true;hsqldb.lock_file=false", "SA", "");
    }

    private Food fromResultSet(ResultSet rs) throws SQLException {
        int foodId = rs.getInt("id");
        String name = rs.getString("name");
        int calories = rs.getInt("calories");
        return new Food(foodId, name, calories);
    }

    @Override
    public Food getFoodByID(int foodID) {
        try (Connection c = connect()) {
            final PreparedStatement st = c.prepareStatement("SELECT * FROM FOODS WHERE id = ?");
            st.setInt(1, foodID);
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
    public Food getFoodByFoodName(String foodName) {
        try (Connection c = connect()) {
            final PreparedStatement st = c.prepareStatement("SELECT * FROM FOODS WHERE name = ?");
            st.setString(1, foodName);
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
    public ArrayList<Food> getFoodList() {
        ArrayList<Food> foodList = new ArrayList<>();
        try (final Connection c = connect()) {
            final Statement st = c.createStatement();
            final ResultSet rs = st.executeQuery("SELECT * FROM FOODS");
            while (rs.next()) {
                final Food food = fromResultSet(rs);
                foodList.add(food);
            }
            rs.close();
            st.close();
        } catch (final SQLException e) {
            throw new HSQLDBException(e);
        }
        return foodList;
    }

    @Override
    public int addFood(Food food) {
        int generatedId = -1;
        try (final Connection c = connect()) {
            // Use IDENTITY to let database generate ID
            final PreparedStatement st = c.prepareStatement(
                    "INSERT INTO FOODS (name, calories) VALUES (?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            st.setString(1, food.getName());
            st.setInt(2, food.getCalories());
            st.executeUpdate();

            // Get the auto-generated ID
            ResultSet generatedKeys = st.getGeneratedKeys();
            if (generatedKeys.next()) {
                generatedId = generatedKeys.getInt(1);
            }

            st.close();
        } catch (final SQLException e) {
            throw new HSQLDBException(e);
        }
        return generatedId;
    }

    @Override
    public void deleteFood(int foodID) {
        try (final Connection c = connect()) {
            final PreparedStatement st = c.prepareStatement("DELETE FROM FOODS WHERE id = ?");
            st.setInt(1, foodID);
            st.executeUpdate();
            st.close();
        } catch (final SQLException e) {
            throw new HSQLDBException(e);
        }
    }

    @Override
    public void updateFood(Food food) {
        try (final Connection c = connect()) {
            final PreparedStatement st = c.prepareStatement("UPDATE FOODS SET name = ?, calories = ? WHERE id = ?");
            st.setString(1, food.getName());
            st.setInt(2, food.getCalories());
            st.setInt(3, food.getFoodID());
            st.executeUpdate();
            st.close();
        } catch (final SQLException e) {
            throw new HSQLDBException(e);
        }
    }

}
