package comp3350.fittrack.presentation.exercise;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
//import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import comp3350.fittrack.R;
import comp3350.fittrack.business.exercise.AccessExercise;
import comp3350.fittrack.business.user.CalorieManager;
import comp3350.fittrack.objects.Exercise;

import java.util.ArrayList;
import java.util.List;

/** **************************************************
 CLASS NAME: SearchExerciseActivity.java

 Presentation screen to search, view, add, edit, and delete exercises.

 ************************************************** */

public class SearchExerciseActivity extends AppCompatActivity {

    private AccessExercise accessExercise;
    private SearchView searchView;
    private ListView exerciseListView;
    private List<Exercise> allExercises;
    private ExerciseListAdapter adapter;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_exercise);

        userId = getIntent().getIntExtra(getString(R.string.user_id), -1);
        if (userId == -1) {
            Toast.makeText(this, getString(R.string.error_no_user_id), Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        accessExercise = new AccessExercise();
        exerciseListView = findViewById(R.id.exercise_list);
        searchView = findViewById(R.id.searchExercise);

        updateExerciseList();
        initSearchFunctionality();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateExerciseList();
    }


    private void updateExerciseList() {
        allExercises = accessExercise.getAllExercises();

        if (adapter == null) {
            setupAdapter(allExercises);
        } else {
            // Adapter already exists, just refresh
            adapter.clear();
            adapter.addAll(allExercises);
            adapter.notifyDataSetChanged();
        }
    }



    private void setupAdapter(List<Exercise> exercises) {
        adapter = new ExerciseListAdapter(this, exercises, new ExerciseListAdapter.ExerciseActionListener() {
            @Override
            public void onDeleteClick(Exercise exercise) {
                showDeleteConfirmationDialog(exercise);
            }

            @Override
            public void onEditClick(Exercise exercise) {
                showEditExerciseDialog(exercise);
            }

            @Override
            public void onRowClick(Exercise exercise) {
                handleRowClick(exercise);
            }
        });
        exerciseListView.setAdapter(adapter);
    }

    private void handleRowClick(Exercise exercise) {
        CalorieManager.addCaloriesBurned(userId, exercise.getExerciseID(), 30);
        showToast(getString(R.string.calories_burned_added, exercise.getCaloriesBurned()));
        finish();
    }

    private void initSearchFunctionality() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) { return false; }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterExerciseList(newText);
                return false;
            }
        });
    }

    private void filterExerciseList(String query) {
        List<Exercise> filtered = new ArrayList<>();
        for (Exercise exercise : allExercises) {
            if (exercise.getName().toLowerCase().contains(query.toLowerCase())) {
                filtered.add(exercise);
            }
        }
        adapter.clear();
        adapter.addAll(filtered);
        adapter.notifyDataSetChanged();
    }

    public void btnAddNewExerciseOnClick(View view) {
        startActivity(new Intent(this, AddExerciseActivity.class));
    }

    private void showDeleteConfirmationDialog(Exercise exercise) {
        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle(getString(R.string.confirm_delete))
                .setMessage("Delete \"" + exercise.getName() + "\" and all related logs?")
                .setPositiveButton(getString(R.string.delete), (dialog, which) -> {
                    cascadeDeleteExercise(exercise);
                })
                .setNegativeButton(getString(R.string.cancel), null)
                .show();
    }

    private void showEditExerciseDialog(Exercise exercise) {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_edit_exercise, null);
        EditText nameInput = dialogView.findViewById(R.id.dialog_edit_exercise_name);
        EditText caloriesInput = dialogView.findViewById(R.id.dialog_edit_exercise_calories);

        nameInput.setText(exercise.getName());
        caloriesInput.setText(String.valueOf(exercise.getCaloriesBurned()));

        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle(getString(R.string.edit_exercise))
                .setView(dialogView)
                .setPositiveButton(getString(R.string.apply), (dialog, which) -> {
                    String newName = nameInput.getText().toString().trim();
                    String caloriesStr = caloriesInput.getText().toString().trim();

                    if (newName.isEmpty() || caloriesStr.isEmpty()) {
                        showToast(getString(R.string.dialog_all_fields_required));
                        return;
                    }

                    try {
                        int newCalories = Integer.parseInt(caloriesStr);
                        Exercise updatedExercise = new Exercise(exercise.getExerciseID(), newName, newCalories);
                        accessExercise.updateExercise(updatedExercise);
                        showToast(getString(R.string.exercise_updated));
                        updateExerciseList();
                    } catch (NumberFormatException e) {
                        showToast(getString(R.string.invalid_exercise_calories));
                    } catch (Exception e) {
                        showToast(getString(R.string.error_general, e.getMessage()));
                    }
                })
                .setNegativeButton(getString(R.string.cancel), null)
                .show();
    }

    private void cascadeDeleteExercise(Exercise exercise) {
        try {
            accessExercise.deleteExerciseAndLogs(exercise.getExerciseID());
            showToast(getString(R.string.exercise_deleted, exercise.getName()));
            updateExerciseList();
        } catch (Exception e) {
            showToast(getString(R.string.exercise_delete_error, e.getMessage()));
        }
    }


    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
