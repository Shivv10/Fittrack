package comp3350.fittrack.presentation.exercise;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import comp3350.fittrack.R;
import comp3350.fittrack.business.exercise.AccessExercise;
import comp3350.fittrack.business.utils.ValidationUtils;
import comp3350.fittrack.business.exercise.exceptions.ExerciseNotFoundException;
import comp3350.fittrack.business.exercise.exceptions.InvalidExerciseException;

/** **************************************************
 CLASS NAME: AddExerciseActivity.java

 CLASS FUNCTION: this class is the presentation layer used for adding exercises,
 called from the SearchExerciseActivity

 ************************************************** */

public class AddExerciseActivity extends AppCompatActivity {
    private EditText nameInput, caloriesInput;
    private AccessExercise accessExercise;

    //onCreate method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercise);

        accessExercise = new AccessExercise();
        initializeUI();
    }


    //makes the UI show current data
    private void initializeUI() {
        nameInput = findViewById(R.id.edit_exercise_name);
        caloriesInput = findViewById(R.id.edit_exercise_calories);
        Button saveButton = findViewById(R.id.btn_save_exercise);

        saveButton.setOnClickListener(v -> saveExercise());
    }

    /* **************************************************
    FUNCTION NAME: saveExercise
    FUNCTION PURPOSE: Send the inputted exercise data into persistence
    ************************************************** */
    private void saveExercise() {
        String name = nameInput.getText().toString().trim();
        String caloriesStr = caloriesInput.getText().toString().trim();

        try {
            ValidationUtils.validateInputs(name, caloriesStr);
            int calories = Integer.parseInt(caloriesStr);
            accessExercise.insertExercise(name, calories);
            showToast(getString(R.string.exercise_added));
            finish();
        } catch (NumberFormatException e) {
            showToast(getString(R.string.invalid_exercise_calories));
        } catch (InvalidExerciseException | ExerciseNotFoundException e) {
            showToast(e.getMessage());
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
