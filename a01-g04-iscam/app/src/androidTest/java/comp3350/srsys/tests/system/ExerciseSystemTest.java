package comp3350.srsys.tests.system;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;

import comp3350.fittrack.R;
import comp3350.fittrack.objects.Exercise;
import comp3350.fittrack.presentation.user.LoginActivity;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ExerciseSystemTest {

    private String testUsername;
    private String testPassword;
    private String testExerciseName;
    private String testCaloriesBurned;

    @Rule
    public ActivityScenarioRule<LoginActivity> activityScenarioRule =
            new ActivityScenarioRule<>(LoginActivity.class);

    @Before
    public void setUp() {
        testUsername = "bob";
        testPassword = "password123";
        testExerciseName = "Volleyball";
        testCaloriesBurned = "200";
    }

    @Test
    //Select an exercise from the default list
    public void testSelectExercise() {
        //Log in User - have to log in a user because HomeActivity checks that a user is logged in
        //type username
        onView(withId(R.id.inputUsername)).perform(typeText(testUsername), closeSoftKeyboard());
        //type password
        onView(withId(R.id.inputPassword)).perform(typeText(testPassword), closeSoftKeyboard());
        //click login button
        onView(withId(R.id.btnLogin)).perform(click());
        //check that we progressed past the login screen
        onView(withId(R.id.btn_open_drawer)).check(matches(isDisplayed())); //user profile icon

        //click add exercise button
        onView(withId(R.id.btn_exercise)).perform(click());
        //click first item in list
        onData(allOf(instanceOf(Exercise.class))).atPosition(0).perform(ViewActions.click());

        //exercise added successfully
    }

    @Test
    public void testCreateExercise() {
        //Log in User - have to log in a user because HomeActivity checks that a user is logged in
        //type username
        onView(withId(R.id.inputUsername)).perform(typeText(testUsername), closeSoftKeyboard());
        //type password
        onView(withId(R.id.inputPassword)).perform(typeText(testPassword), closeSoftKeyboard());
        //click login button
        onView(withId(R.id.btnLogin)).perform(click());
        //check that we progressed past the login screen
        onView(withId(R.id.btn_open_drawer)).check(matches(isDisplayed())); //user profile icon

        //click add exercise button
        onView(withId(R.id.btn_exercise)).perform(click());
        //click add NEW exercise button
        onView(withId(R.id.btn_add_exercise)).perform(click());

        //Ensure the Exercise Name field is displayed
        onView(withId(R.id.edit_exercise_name))
                .check(matches(isDisplayed()));

        // Type into the Exercise Name field
        onView(withId(R.id.edit_exercise_name))
                .perform(typeText(testExerciseName), closeSoftKeyboard());

        // Ensure the Calories burned field is displayed
        onView(withId(R.id.edit_exercise_calories))
                .check(matches(isDisplayed()));

        // Type into the Calories burned field
        onView(withId(R.id.edit_exercise_calories))
                .perform(typeText(testCaloriesBurned), closeSoftKeyboard());

        //click save exercise
        onView(withId(R.id.btn_save_exercise)).perform(click());

        // Ensure we are back in the search exercise activity
        onView(withId(R.id.exercise_list)).check(matches(anything()));

        // check that the new exercise appears in the list
        onData(anything())  //check name
                .inAdapterView(withId(R.id.exercise_list))
                .atPosition(6)  //should be the 6th item in the list
                .onChildView(withId(R.id.exercise_name))
                .check(matches(withText(testExerciseName)));

        String expectedText = InstrumentationRegistry.getInstrumentation()
                .getTargetContext()
                .getString(R.string.exercise_burns, Integer.parseInt(testCaloriesBurned));
        onData(anything())
                .inAdapterView(withId(R.id.exercise_list))
                .atPosition(6)  // Should be the 6th item in the list
                .onChildView(withId(R.id.exercise_calories))
                .check(matches(withText(expectedText)));
    }


}
