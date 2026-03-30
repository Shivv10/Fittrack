package comp3350.srsys.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import comp3350.srsys.tests.integration.AllIntegrationTests;
import comp3350.srsys.tests.unittests.AllUnitTests;


@RunWith(Suite.class)
@Suite.SuiteClasses({
        AllUnitTests.class,
        AllIntegrationTests.class,
})

public class AllTests {
    // This class remains empty; it is used only as a holder for the above annotations.
}
