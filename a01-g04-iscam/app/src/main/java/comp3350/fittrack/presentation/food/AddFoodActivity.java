package comp3350.fittrack.presentation.food;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import comp3350.fittrack.R;
import comp3350.fittrack.business.food.AccessFood;
import comp3350.fittrack.business.utils.ValidationUtils;
import comp3350.fittrack.business.food.exceptions.InvalidFoodException;
import comp3350.fittrack.business.food.exceptions.InvalidFoodNameException;
import comp3350.fittrack.persistence.hsqldb.exceptions.HSQLDBException;

/** **************************************************
 CLASS NAME: AddFoodActivity.java

 CLASS FUNCTION: this class is the presentation layer used for adding foods, called from the SearchFoodActivity

 ************************************************** */

public class AddFoodActivity extends AppCompatActivity {
    private EditText nameInput;
    private EditText caloriesInput;
    private AccessFood foodManager;

    //onCreate method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);

        try {
            // Initialize the AccessFood instance
            foodManager = new AccessFood();

            // Initialize views
            nameInput = findViewById(R.id.edit_food_name);
            caloriesInput = findViewById(R.id.edit_food_calories);
            Button saveButton = findViewById(R.id.btn_save_food);

            saveButton.setOnClickListener(v -> saveFood());
        } catch (Exception e) {
            Toast.makeText(this, getString(R.string.error_initializing, e.getMessage()), Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    /** **************************************************
    FUNCTION NAME: saveFood
    FUNCTION PURPOSE: Send the inputted Food data into persistence
    ************************************************** */
    private void saveFood() {
        String name = nameInput.getText().toString().trim();
        String caloriesStr = caloriesInput.getText().toString().trim();

        ValidationUtils.validateInputs(name, caloriesStr);

        try {
            int calories = Integer.parseInt(caloriesStr);

            // Use -1 as temporary ID, the database will assign the real ID
            int newFoodId = foodManager.addFood(-1, name, calories);

            if (newFoodId >= 0) {
                Toast.makeText(this, getString(R.string.food_added, name), Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, getString(R.string.food_add_failed), Toast.LENGTH_SHORT).show();
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, getString(R.string.invalid_calories), Toast.LENGTH_SHORT).show();
        } catch (InvalidFoodNameException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        } catch (InvalidFoodException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        } catch (HSQLDBException e) {
            Toast.makeText(this, getString(R.string.error_database, e.getMessage()), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, getString(R.string.error_unexpected, e.getMessage()), Toast.LENGTH_SHORT).show();
        }
    }
}