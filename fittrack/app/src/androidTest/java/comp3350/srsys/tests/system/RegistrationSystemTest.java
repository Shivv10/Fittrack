package comp3350.srsys.tests.system;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static org.hamcrest.CoreMatchers.is;

import comp3350.fittrack.R;
import comp3350.fittrack.presentation.user.LoginActivity;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class RegistrationSystemTest {
    private String testUsername;
    private String testPassword;
    private String testName;
    private String testHeight;
    private String testWeight;
    private int testAge;

    @Rule
    public ActivityScenarioRule<LoginActivity> activityScenarioRule =
            new ActivityScenarioRule<>(LoginActivity.class);

    @Before
    public void setUp() {
        testUsername = "newuser";
        testPassword = "$ecurep@55worD";
        testName = "Newuser";
        testAge = 25;
        testHeight = "180";
        testWeight = "80";
    }

    @Test
    public void testSuccessfulRegistration() {
        //click register button (from login page)
        onView(withId(R.id.txtRegister)).perform(click());

        //type username
        onView(withId(R.id.setUsername)).perform(typeText(testUsername), closeSoftKeyboard());
        //type password
        onView(withId(R.id.setPassword)).perform(typeText(testPassword), closeSoftKeyboard());
        //type name
        onView(withId(R.id.setName)).perform(typeText(testName), closeSoftKeyboard());
        //set age spinner
        onView(withId(R.id.setAge)).perform(click());
        onData(is(testAge)).perform(click()); // click the desired age
        //type height
        onView(withId(R.id.setHeight)).perform(typeText(testHeight), closeSoftKeyboard());
        //type weight
        onView(withId(R.id.setWeight)).perform(typeText(testWeight), closeSoftKeyboard());

        //click register button
        onView(withId(R.id.btnRegister)).perform(click());

        //check that we got kicked back to the login screen to login
        onView(withId(R.id.btnLogin)).check(matches(isDisplayed())); //login button

        //log in with the new account
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
    public void testInvalidRegistration() {
        testPassword = "securepassword";

        //click register button (from login page)
        onView(withId(R.id.txtRegister)).perform(click());

        //type username
        onView(withId(R.id.setUsername)).perform(typeText(testUsername), closeSoftKeyboard());
        //type INVALID PASSWORD
        onView(withId(R.id.setPassword)).perform(typeText(testPassword), closeSoftKeyboard());
        //type name
        onView(withId(R.id.setName)).perform(typeText(testName), closeSoftKeyboard());
        //set age spinner
        onView(withId(R.id.setAge)).perform(click());
        onData(is(testAge)).perform(click()); // click the desired age
        //type height
        onView(withId(R.id.setHeight)).perform(typeText(testHeight), closeSoftKeyboard());
        //type weight
        onView(withId(R.id.setWeight)).perform(typeText(testWeight), closeSoftKeyboard());

        //click register button
        onView(withId(R.id.btnRegister)).perform(click());

        // Check that user profile icon does not exist in the hierarchy (it should not be there after a failed login)
        onView(withId(R.id.btn_open_drawer)).check(doesNotExist());
    }

    @Test
    public void testEmptyFields() {
        //click register button (from login page)
        onView(withId(R.id.txtRegister)).perform(click());

        // Click register button
        onView(withId(R.id.btnRegister)).perform(click());

        // Check that user profile icon does not exist in the hierarchy (it should not be there after a failed login)
        onView(withId(R.id.btn_open_drawer)).check(doesNotExist());
    }

}
