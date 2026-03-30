package comp3350.srsys.tests.unittests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import comp3350.srsys.tests.unittests.business.*;
import comp3350.srsys.tests.unittests.persistence.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AccessExerciseTest.class,
        AccessFoodTest.class,
        AccessExerciseLogsTest.class,
        AccessFoodLogsTest.class,
        UIHelperTest.class,
        ValidationUtilsTest.class,
        AccessUserTest.class,

        UserPersistenceStubTest.class,
        FoodPersistenceStubTest.class,
        ExercisePersistenceStubTest.class,
        FoodLogPersistenceStubTest.class,
        ExerciseLogPersistenceStubTest.class,
})
public class AllUnitTests {
    // This class remains empty; it is used only as a holder for the above annotations.
}
