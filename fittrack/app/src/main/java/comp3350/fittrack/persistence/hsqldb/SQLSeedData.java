package comp3350.fittrack.persistence.hsqldb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import comp3350.fittrack.application.Main;
import comp3350.fittrack.application.Services;
import comp3350.fittrack.objects.Exercise;
import comp3350.fittrack.objects.Food;
import comp3350.fittrack.objects.User;
import comp3350.fittrack.persistence.IExercisePersistence;
import comp3350.fittrack.persistence.IFoodPersistence;
import comp3350.fittrack.persistence.IUserPersistence;

/** **************************************************
 CLASS NAME: SQLSeedData.java

 Populates default values (Users, Foods, Exercises) if not already inserted.

 ************************************************** */

public class SQLSeedData {

    public static void insertDefaults() {
        IUserPersistence userDB = Services.getUserPersistence();
        IFoodPersistence foodDB = Services.getFoodPersistence();
        IExercisePersistence exerciseDB = Services.getExercisePersistence();
        Connection conn = null;
        boolean defaultsAlreadyInserted = false;

        try {
            conn = DriverManager.getConnection("jdbc:hsqldb:file:" + Main.getDBPathName() + ";shutdown=true;hsqldb.lock_file=false", "SA", "");
            Statement stmt = conn.createStatement();

            // Ensure META table exists
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS META (defaultsInserted BOOLEAN DEFAULT FALSE)");

            // Check if row exists
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS rowcount FROM META");
            rs.next();
            if (rs.getInt("rowcount") == 0) {
                stmt.executeUpdate("INSERT INTO META (defaultsInserted) VALUES (FALSE)");
            }
            rs.close();

            // Check flag
            rs = stmt.executeQuery("SELECT defaultsInserted FROM META");
            if (rs.next()) {
                defaultsAlreadyInserted = rs.getBoolean("defaultsInserted");
            }
            rs.close();

            if (!defaultsAlreadyInserted) {
                // Insert Users
                userDB.register(new User(25, 70.0, -1, "password123", 175.0, "bob", "Bob Builder"));
                userDB.register(new User(30, 65.0, -1, "pass456", 160.0, "alice", "Alice Wonderland"));
                userDB.register(new User(28, 80.0,  -1, "securepass", 180.0, "charlie", "Charlie Brown"));
                userDB.register(new User(35, 62.0,  -1, "fitpass2025", 165.0, "diana", "Diana Prince"));
                userDB.register(new User(40, 90.0,  -1, "strongpwd", 185.0, "eric", "Eric Cartman"));

                // Insert Foods
                foodDB.addFood(new Food(-1, "Bread", 233));
                foodDB.addFood(new Food(-1, "Nonfat Greek Yogurt", 53));
                foodDB.addFood(new Food(-1, "Fried Egg", 184));
                foodDB.addFood(new Food(-1, "Banana", 89));
                foodDB.addFood(new Food(-1, "Steamed Rice", 93));
                foodDB.addFood(new Food(-1, "Grilled Chicken Breast", 65));

                // Insert Exercises
                exerciseDB.insertExercise(new Exercise(-1, "Axe Hold", 60));
                exerciseDB.insertExercise(new Exercise(-1, "Flutter Kicks", 180));
                exerciseDB.insertExercise(new Exercise(-1, "Bench Press", 140));
                exerciseDB.insertExercise(new Exercise(-1, "Chin-Ups", 120));
                exerciseDB.insertExercise(new Exercise(-1, "Shrugs, Dumbbells", 100));
                exerciseDB.insertExercise(new Exercise(-1, "Calf Raises", 100));

                // Set the flag to TRUE
                stmt.executeUpdate("UPDATE META SET defaultsInserted = TRUE");
            }

            stmt.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting default values: " + e.getMessage(), e);
        }
    }
}
