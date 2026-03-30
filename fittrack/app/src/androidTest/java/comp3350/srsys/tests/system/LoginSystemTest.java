package comp3350.srsys.tests.system;

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
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import comp3350.fittrack.R;
import comp3350.fittrack.presentation.user.LoginActivity;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginSystemTest {
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
    public void testSuccessfulLogin() {
        //type username
        onView(withId(R.id.inputUsername)).perform(typeText(testUsername), closeSoftKeyboard());
        //type password
        onView(withId(R.id.inputPassword)).perform(typeText(testPassword), closeSoftKeyboard());
        //click login button
        onView(withId(R.id.btnLogin)).perform(click());

        //check that we progressed past the login screen
        onView(withId(R.id.btn_open_drawer)).check(matches(isDisplayed())); //user profile icon
    }

    @Test
    public void testInvalidLogin() {
        testPassword = "wrongpassword";

        // Type username
        onView(withId(R.id.inputUsername)).perform(typeText(testUsername), closeSoftKeyboard());

        // Type password
        onView(withId(R.id.inputPassword)).perform(typeText(testPassword), closeSoftKeyboard());

        // Click login button
        onView(withId(R.id.btnLogin)).perform(click());

        // Check that user profile icon does not exist in the hierarchy (it should not be there after a failed login)
        onView(withId(R.id.btn_open_drawer)).check(doesNotExist());
    }

    @Test
    public void testEmptyFields() {
        // Click login button
        onView(withId(R.id.btnLogin)).perform(click());

        // Check that user profile icon does not exist in the hierarchy (it should not be there after a failed login)
        onView(withId(R.id.btn_open_drawer)).check(doesNotExist());
    }

    @Test
    public void testLogout() {
        //First login a user
        //type username
        onView(withId(R.id.inputUsername)).perform(typeText(testUsername), closeSoftKeyboard());
        //type password
        onView(withId(R.id.inputPassword)).perform(typeText(testPassword), closeSoftKeyboard());
        //click login button
        onView(withId(R.id.btnLogin)).perform(click());

        //click logout button
        onView(withId(R.id.btn_logout)).perform(click());

        // Click the "Yes" button in the dialog
        onView(withText(R.string.yes)).perform(click());

        //check that we are back at the login screen
        onView(withId(R.id.btnLogin)).check(matches(isDisplayed())); //login button
    }

}
