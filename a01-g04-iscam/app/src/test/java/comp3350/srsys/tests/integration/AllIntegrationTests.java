package comp3350.srsys.tests.integration;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AccessFoodsIT.class,
        AccessExercisesIT.class,
        AccessFoodLogsIT.class,
        AccessExerciseLogsIT.class,
        AccessUserIT.class

})
public class AllIntegrationTests {
    // This class remains empty; it only serves as a holder for the annotations.
}
