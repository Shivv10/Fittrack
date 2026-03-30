package comp3350.srsys.tests.system;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.widget.EditText;

import comp3350.fittrack.R;
import comp3350.fittrack.presentation.user.LoginActivity;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class CalorieGoalTest {
    private String testUsername;
    private String testPassword;

    @Rule
    public ActivityScenarioRule<LoginActivity> activityScenarioRule =
            new ActivityScenarioRule<>(LoginActivity.class);

    @Before
    public void setUp() {
        testUsername = "bob";
        testPassword = "password123";
    }

    @Test
    public void testChangeCalorieGoal() {
        //First login a user
        //type username
        onView(withId(R.id.inputUsername)).perform(typeText(testUsername), closeSoftKeyboard());
        //type password
        onView(withId(R.id.inputPassword)).perform(typeText(testPassword), closeSoftKeyboard());
        //click login button
        onView(withId(R.id.btnLogin)).perform(click());

        //click change goal button
        onView(withId(R.id.btn_changeCalorieTarget)).perform(click());

        // Type a new calorie goal
        onView(withClassName(Matchers.equalTo(EditText.class.getName())))
                .perform(typeText("2200"), closeSoftKeyboard());

        // Click the "OK" button
        onView(withText(R.string.ok)).perform(click());

        // Verify the update (assuming a TextView displays the new calorie goal)
        onView(withId(R.id.calorieTargetText)).check(matches(withText("/2200")));
    }

}

