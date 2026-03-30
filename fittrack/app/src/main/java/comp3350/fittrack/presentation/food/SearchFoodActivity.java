package comp3350.fittrack.presentation.food;

import comp3350.fittrack.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;
import java.util.ArrayList;

import comp3350.fittrack.business.food.AccessFood;
import comp3350.fittrack.business.food.AccessFoodLogs;
import comp3350.fittrack.objects.Food;
import comp3350.fittrack.objects.FoodLog;

/** **************************************************
 CLASS NAME: SearchFoodActivity.java

 CLASS FUNCTION: this is the presentation layer used for the food selection screen

 ************************************************** */

public class SearchFoodActivity extends AppCompatActivity {
    private FoodListAdapter adapter;
    private AccessFood foodManager;
    private AccessFoodLogs foodLogManager;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_food);

        try {
            // Initialize managers
            foodManager = new AccessFood();
            foodLogManager = new AccessFoodLogs();
            // Get the user ID and meal type from the intent
            userId = getIntent().getIntExtra(getString(R.string.user_id), -1);
            String mealType = getIntent().getStringExtra(getString(R.string.meal_type));

            if (userId == -1) {
                throw new IllegalStateException(getString(R.string.error_no_user_id));
            }

            // Update the title to show which meal is being added
            TextView titleText = findViewById(R.id.title_text);
            titleText.setText(getString(R.string.select_food, mealType));

            ListView foodListView = findViewById(R.id.food_list);
            updateFoodList();

            foodListView.setOnItemClickListener((parent, view, position, id) -> {
                Food selectedFood = (Food) parent.getItemAtPosition(position);
                addFoodToLog(selectedFood);
            });
        } catch (Exception e) {
            Toast.makeText(this, getString(R.string.error_initializing, e.getMessage()), Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateFoodList();
    }

    //updates the view with the current data on Foods
    private void updateFoodList() {
        try {
            ArrayList<Food> foods = foodManager.getFoodList().getFoodList();
            adapter = new FoodListAdapter(this, foods);
            ListView listView = findViewById(R.id.food_list);
            listView.setAdapter(adapter);
        } catch (Exception e) {
            Toast.makeText(this, getString(R.string.error_loading_food, e.getMessage()), Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    //inserts the given Food object into persistence
    private void addFoodToLog(Food selectedFood) {
        try {
            // Create a new food log entry with default quantity of 1 serving
            FoodLog newLog = new FoodLog(userId, selectedFood.getFoodID(), LocalDate.now().toString(), 1);
            foodLogManager.insertFoodLog(newLog);
            Toast.makeText(this, getString(R.string.food_log_added, selectedFood.getName()), Toast.LENGTH_SHORT).show();
            finish();
        } catch (Exception e) {
            Toast.makeText(this, getString(R.string.error_adding_food, e.getMessage()), Toast.LENGTH_SHORT).show();
        }
    }

    public void btnAddNewFoodOnClick(View view) {
        Intent intent = new Intent(this, AddFoodActivity.class);
        startActivity(intent);
    }
}