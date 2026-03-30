package comp3350.fittrack.presentation.menu;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.text.InputType;
import android.widget.EditText;

import comp3350.fittrack.R;
import comp3350.fittrack.business.user.AccessUser;
import comp3350.fittrack.business.user.CalorieManager;
import comp3350.fittrack.presentation.exercise.SearchExerciseActivity;
import comp3350.fittrack.presentation.food.SearchFoodActivity;
import comp3350.fittrack.presentation.user.EditProfileActivity;
import comp3350.fittrack.presentation.user.LoginActivity;

/** **************************************************
 CLASS NAME: HomeActivity.java

 CLASS FUNCTION: this is the presentation layer used for the Home

 ************************************************** */

public class HomeActivity extends AppCompatActivity {
    private TextView caloriesEatenText;
    private TextView calorieTargetText;
    private CircleProgressBar progressView;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // 1. Get the user ID from intent
        userId = getIntent().getIntExtra(getString(R.string.user_id), -1);
        if (userId == -1) {
            Toast.makeText(this, getString(R.string.error_no_user_id), Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        // 2. Initialize views (existing ones)
        progressView = findViewById(R.id.arcProgressView);
        Button btn_changeCalorieTarget = findViewById(R.id.btn_changeCalorieTarget);
        caloriesEatenText = findViewById(R.id.calories_eaten);
        calorieTargetText = findViewById(R.id.calorieTargetText);

        // 3. Initialize your drawer and its buttons (ADD THIS SECTION HERE 👇)
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        ImageView openDrawer = findViewById(R.id.btn_open_drawer);
        ImageView closeDrawer = findViewById(R.id.btn_close_drawer);

        openDrawer.setOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));
        closeDrawer.setOnClickListener(v -> drawerLayout.closeDrawer(GravityCompat.START));

        ImageButton btnLogout = findViewById(R.id.btn_logout);
        btnLogout.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle(getString(R.string.logout))
                    .setMessage(getString(R.string.logout_confirmation))
                    .setPositiveButton(getString(R.string.yes), (dialog, which) -> {
                        Intent intent = new Intent(this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    })
                    .setNegativeButton(getString(R.string.cancel), null)
                    .show();
        });


        findViewById(R.id.btn_edit_profile).setOnClickListener(v -> {
            Intent intent = new Intent(this, EditProfileActivity.class);
            intent.putExtra(getString(R.string.user_id), userId);
            startActivity(intent);
            drawerLayout.closeDrawer(GravityCompat.START);
        });

        findViewById(R.id.btn_delete_profile).setOnClickListener(v -> {
            drawerLayout.closeDrawer(GravityCompat.START);
            showDeleteConfirmationDialog();
        });

        // 4. Your existing button listeners for goal change
        View.OnClickListener showGoalDialogListener = v -> showCalorieGoalDialog();
        calorieTargetText.setOnClickListener(showGoalDialogListener);
        btn_changeCalorieTarget.setOnClickListener(showGoalDialogListener);

        // 5. Update progress view
        updateCalorieDisplays();

    }


    /** **************************************************
    FUNCTION NAME: showCalorieGoalDialog
    FUNCTION PURPOSE: Click handler for calorie goal
    ************************************************** */
    private void showCalorieGoalDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.change_calorie_goal));

        final EditText input = new EditText(this);
        input.setHint(getString(R.string.enter_new_goal));
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);

        builder.setPositiveButton(getString(R.string.ok), (dialog, which) -> {
            String newGoalStr = input.getText().toString().trim();
            if (newGoalStr.isEmpty()) {
                Toast.makeText(this, getString(R.string.enter_value), Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                int newGoal = Integer.parseInt(newGoalStr);
                CalorieManager.setCalorieGoal(newGoal);
                updateCalorieDisplays();
                Toast.makeText(this, getString(R.string.goal_updated), Toast.LENGTH_SHORT).show();
            } catch (NumberFormatException e) {
                Toast.makeText(this, getString(R.string.enter_valid_number), Toast.LENGTH_SHORT).show();
            } catch (IllegalArgumentException e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton(getString(R.string.cancel), (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    /* **************************************************
    FUNCTION NAME: updateCalorieDisplays
    FUNCTION PURPOSE: updates the calorie manager

    ************************************************** */
    private void updateCalorieDisplays() {
        try {
            int caloriesEaten = CalorieManager.getCaloriesEatenToday(userId);
            int caloriesBurned = CalorieManager.getTotalCaloriesBurned(userId);
            int calorieGoal = CalorieManager.getCalorieGoal();

            android.util.Log.d("CalorieDebug", "caloriesEaten: " + caloriesEaten + ", caloriesBurned: " + caloriesBurned);

            int netCalories = Math.max(0, caloriesEaten - caloriesBurned);
            caloriesEatenText.setText(String.valueOf(netCalories));
            calorieTargetText.setText("/" + calorieGoal);
            progressView.setMaxValue(calorieGoal);
            progressView.setProgress(netCalories);
        } catch (IllegalStateException e) {
            Toast.makeText(this, getString(R.string.error_general, e.getMessage()), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateCalorieDisplays();
    }

    /* **************************************************
    FUNCTION NAME: btnSearchFoodOnClick
    FUNCTION PURPOSE: Click handler for all food-related buttons (Breakfast, Lunch, Dinner)

    INPUT: view - current view
    ************************************************** */
    public void btnSearchFoodOnClick(View view) {
        Intent intent = new Intent(this, SearchFoodActivity.class);
        String mealType = "";

        //Leaving as it is more button presses than it is logic
        if (view.getId() == R.id.btn_breakfast) {
            mealType = getString(R.string.breakfast);
        } else if (view.getId() == R.id.btn_lunch) {
            mealType = getString(R.string.lunch);
        } else if (view.getId() == R.id.btn_dinner) {
            mealType = getString(R.string.dinner);
        }

        intent.putExtra(getString(R.string.meal_type), mealType);
        intent.putExtra(getString(R.string.user_id), userId);
        startActivity(intent);
    }

    // Click handler for exercise button
    public void btnSearchExerciseOnClick(View view) {
        Intent intent = new Intent(this, SearchExerciseActivity.class);
        intent.putExtra(getString(R.string.user_id), userId);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }


    private void showDeleteConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.delete_profile))
                .setMessage(getString(R.string.confirm_delete_profile))
                .setPositiveButton(getString(R.string.delete), (dialog, which) -> {
                    // Call AccessUser to delete the profile
                    AccessUser accessUser = new AccessUser();
                    boolean success = accessUser.deleteUser(userId);

                    if (success) {
                        Toast.makeText(this, getString(R.string.profile_deleted), Toast.LENGTH_SHORT).show();
                        // Redirect to Login screen
                        Intent intent = new Intent(this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(this, getString(R.string.delete_error), Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(getString(R.string.cancel), (dialog, which) -> dialog.dismiss())
                .show();
    }


}