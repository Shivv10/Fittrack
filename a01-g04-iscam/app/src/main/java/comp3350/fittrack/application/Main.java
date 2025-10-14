package comp3350.fittrack.application;
import comp3350.fittrack.business.config.LiteralsConfig;

/**
 - Entry point for the application.
 - Instantiates services and DAOs.
 - Passes control to the presentation layer.
 */

public class Main {
	private static String dbName = "FitTrackDB";
	private static boolean dbInitialized = false;

	public static void setDBPathName(final String name) {
		dbName = name;
	}

	public static String getDBPathName() {
		return dbName;
	}

	public static void initializeDB() {
		if (!dbInitialized) {
			try {
				Class.forName("org.hsqldb.jdbcDriver").newInstance();
				dbInitialized = true;
			} catch (Exception e) {
				System.err.println(LiteralsConfig.DB_ERROR_INIT + e.getMessage());
				e.printStackTrace();
			}
		}
	}
}
