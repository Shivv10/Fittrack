package comp3350.fittrack.presentation.user;

import android.app.AlertDialog;
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
import comp3350.fittrack.objects.User;
import comp3350.fittrack.presentation.menu.HomeActivity;

/** **************************************************
 CLASS NAME: EditProfileActivity.java

 Allows user to view and update their profile (name, age, height, weight).

 ************************************************** */

public class EditProfileActivity extends AppCompatActivity {

    private AccessUser accessUser;
    private EditText setName, setHeight, setWeight;
    private Spinner setAge;
    private Button btnSave;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        accessUser = new AccessUser();
        userId = getIntent().getIntExtra("USER_ID", -1);

        if (userId == -1) {
            Toast.makeText(this, "Error: No user ID provided", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        setupViews();
        setupSpinners();
        loadUserData();
        setupListeners();
    }

    private void loadUserData() {
        User user = accessUser.getUserById(userId);
        if (user != null) {
            setName.setText(user.getName());
            setHeight.setText(String.valueOf(user.getHeight()));
            setWeight.setText(String.valueOf(user.getWeight()));
            setAge.setSelection(UIHelper.getAgeListAsList().indexOf(user.getAge()));
        } else {
            Toast.makeText(this, "Error loading user info", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void saveUserChanges() {
        try {
            User oldUser = accessUser.getUserById(userId);
            User updatedUser = new User(
                    (int) setAge.getSelectedItem(),
                    Double.parseDouble(setWeight.getText().toString().trim()),
                    userId,
                    oldUser.getPassword(),
                    Double.parseDouble(setHeight.getText().toString().trim()),
                    oldUser.getUsername(), // keep original username
                    setName.getText().toString().trim()
            );


            boolean success = accessUser.updateUser(updatedUser);
            if (success) {
                Toast.makeText(this, "Profile updated!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, HomeActivity.class).putExtra("USER_ID", userId));
                finish();
            } else {
                Toast.makeText(this, "Update failed", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void setupViews() {
        setName = findViewById(R.id.edit_name);
        setHeight = findViewById(R.id.edit_height);
        setWeight = findViewById(R.id.edit_weight);
        setAge = findViewById(R.id.setAge);
        btnSave = findViewById(R.id.btn_save_changes);
    }


    private void setupSpinners() {
        ArrayAdapter<Integer> ageAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, UIHelper.getAgeList());
        ageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        setAge.setAdapter(ageAdapter);
       }

    private void setupListeners() {

        btnSave.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Confirm Save")
                    .setMessage("Are you sure you want to save these changes?")
                    .setPositiveButton("Yes", (dialog, which) -> saveUserChanges())
                    .setNegativeButton("Cancel", null)
                    .show();
        });

    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
