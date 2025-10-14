package comp3350.fittrack.presentation.user;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import comp3350.fittrack.R;
import comp3350.fittrack.business.user.AccessUser;
import comp3350.fittrack.business.utils.UIHelper;
import comp3350.fittrack.business.user.exceptions.InvalidInputException;

/** **************************************************
 CLASS NAME: UserRegistrationActivity.java

 CLASS FUNCTION: this is the presentation layer used for user registration

 ************************************************** */

public class UserRegistrationActivity extends AppCompatActivity {

    private AccessUser accessUser;
    private EditText setName, setHeight, setWeight, setUsername, setPassword;
    private Spinner setAge;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);
        accessUser = new AccessUser();

        setupViews();
        setupSpinners();
        setupListeners();
    }

    /**
     * Attempts to register a new user with the provided input fields. If registration is successful,
     * the user is navigated to the home page. If an error occurs, an appropriate message is displayed.
     */
    private void registerUser() {
        try {
            // Capture the auto-generated userID returned by the persistence layer.
            int newUserID = accessUser.registerUser(
                    setName.getText().toString().trim(),
                    setUsername.getText().toString().trim(),
                    setPassword.getText().toString().trim(),
                    setHeight.getText().toString().trim(),
                    setWeight.getText().toString().trim(),
                    (int) setAge.getSelectedItem()
            );

            // Pass the generated user ID to the login activity for authentication
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        } catch (InvalidInputException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * Initializes the views by linking UI components to their corresponding
     * XML layout elements using findViewById.
     */
    private void setupViews() {
        setName = findViewById(R.id.setName);
        setHeight = findViewById(R.id.setHeight);
        setWeight = findViewById(R.id.setWeight);
        setUsername = findViewById(R.id.setUsername);
        setPassword = findViewById(R.id.setPassword);
        setAge = findViewById(R.id.setAge);
        btnRegister = findViewById(R.id.btnRegister);
    }

    /**
     * Configures the spinners for selecting age and activity level by
     * populating them with predefined data from helper methods.
     */
    private void setupSpinners() {
        // Age Spinner
        ArrayAdapter<Integer> ageAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, UIHelper.getAgeList());
        ageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        setAge.setAdapter(ageAdapter);
    }

    /**
     * Sets up event listeners for UI components, including handling button clicks
     * for user registration.
     */
    private void setupListeners() {
        btnRegister.setOnClickListener(view -> registerUser());
    }

    /**
     * Handles the back button press to properly close the registration activity.
     */
    @Override
    public void onBackPressed() {
        finish();
    }
}
