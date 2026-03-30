package comp3350.srsys.tests.system;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    LoginSystemTest.class,
    RegistrationSystemTest.class,
    CalorieGoalTest.class,
    ExerciseSystemTest.class,
    FoodSystemTest.class,
})
public class AllSystemTests {
    // This class remains empty; it only serves as a holder for the annotations.
}
