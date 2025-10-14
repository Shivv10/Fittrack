package comp3350.srsys.tests.system;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

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
import comp3350.fittrack.objects.Food;
import comp3350.fittrack.presentation.user.LoginActivity;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class FoodSystemTest {

    private String testUsername;
    private String testPassword;
    private String testFoodName;
    private String testCalories;

    @Rule
    public ActivityScenarioRule<LoginActivity> activityScenarioRule =
            new ActivityScenarioRule<>(LoginActivity.class);

    @Before
    public void setUp() {
        testUsername = "bob";
        testPassword = "password123";
        testFoodName = "Keylime Square";
        testCalories = "120";
    }

    @Test
    //Select a food from the default list
    public void testSelectFood() {
        //Log in User - have to log in a user because HomeActivity checks that a user is logged in
        //type username
        onView(withId(R.id.inputUsername)).perform(typeText(testUsername), closeSoftKeyboard());
        //type password
        onView(withId(R.id.inputPassword)).perform(typeText(testPassword), closeSoftKeyboard());
        //click login button
        onView(withId(R.id.btnLogin)).perform(click());
        //check that we progressed past the login screen
        onView(withId(R.id.btn_open_drawer)).check(matches(isDisplayed())); //user profile icon

        //click add breakfast button
        onView(withId(R.id.btn_breakfast)).perform(click());
        //click first item in list
        onData(allOf(instanceOf(Food.class))).atPosition(0).perform(ViewActions.click());

        // now check that the calorie display updated
        //onView(withId(R.id.calories_eaten)).check(matches(withText("233")));    //bread (the first item) has 233 calories

        //food added successfully
    }

    @Test
    public void testCreateFood() {
        //Log in User - have to log in a user because HomeActivity checks that a user is logged in
        //type username
        onView(withId(R.id.inputUsername)).perform(typeText(testUsername), closeSoftKeyboard());
        //type password
        onView(withId(R.id.inputPassword)).perform(typeText(testPassword), closeSoftKeyboard());
        //click login button
        onView(withId(R.id.btnLogin)).perform(click());
        //check that we progressed past the login screen
        onView(withId(R.id.btn_open_drawer)).check(matches(isDisplayed())); //user profile icon

        //click add food button
        onView(withId(R.id.btn_breakfast)).perform(click());
        //click add NEW food button
        onView(withId(R.id.btn_add_food)).perform(click());

        //Ensure the Food Name field is displayed
        onView(withId(R.id.edit_food_name))
                .check(matches(isDisplayed()));

        // Type into the Food Name field
        onView(withId(R.id.edit_food_name))
                .perform(typeText(testFoodName), closeSoftKeyboard());

        // Ensure the Calories field is displayed
        onView(withId(R.id.edit_food_calories))
                .check(matches(isDisplayed()));

        // Type into the Calories  field
        onView(withId(R.id.edit_food_calories))
                .perform(typeText(testCalories), closeSoftKeyboard());

        //click save food
        onView(withId(R.id.btn_save_food)).perform(click());

        // Ensure we are back in the search food activity
        onView(withId(R.id.food_list)).check(matches(anything()));

        // check that the new food appears in the list
        onData(anything())  //check name
                .inAdapterView(withId(R.id.food_list))
                .atPosition(6)  //should be the 6th item in the list
                .onChildView(withId(R.id.food_name))
                .check(matches(withText(testFoodName)));

        onData(anything())  //check calories
                .inAdapterView(withId(R.id.food_list))
                .atPosition(6)  //should be the 6th item in the list
                .onChildView(withId(R.id.food_calories))
                .check(matches(withText(testCalories + " cal")));
    }

}

