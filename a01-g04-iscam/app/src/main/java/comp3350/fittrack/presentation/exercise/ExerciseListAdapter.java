package comp3350.fittrack.presentation.exercise;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import comp3350.fittrack.R;
import comp3350.fittrack.objects.Exercise;

import java.util.List;

/** **************************************************
 CLASS NAME: ExerciseListAdapter.java

 CLASS FUNCTION: The class used in location where an exerciseList is used

 ************************************************** */

public class ExerciseListAdapter extends ArrayAdapter<Exercise> {
    private final Context context;
    private final List<Exercise> exercises;
    private final ExerciseActionListener actionListener;

    public interface ExerciseActionListener {
        void onDeleteClick(Exercise exercise);
        void onEditClick(Exercise exercise);
        void onRowClick(Exercise exercise);
    }

    public ExerciseListAdapter(Context context, List<Exercise> exercises, ExerciseActionListener listener) {
        super(context, 0, exercises);
        this.context = context;
        this.exercises = exercises;
        this.actionListener = listener;
    }

    /* **************************************************
    FUNCTION NAME: getView
    FUNCTION PURPOSE: display data in the prepared format

    INPUT: int position
            View convertView
            ViewGroup parent

    OUTPUT: returns the built View
    ************************************************** */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Exercise exercise = exercises.get(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.exercise_list_item, parent, false);
        }

        TextView exerciseName = convertView.findViewById(R.id.exercise_name);
        TextView exerciseCalories = convertView.findViewById(R.id.exercise_calories);

        exerciseName.setText(exercise.getName());
        exerciseCalories.setText(context.getString(R.string.exercise_burns, exercise.getCaloriesBurned()));

        ImageView editIcon = convertView.findViewById(R.id.icon_edit);
        ImageView deleteIcon = convertView.findViewById(R.id.icon_delete);

        exerciseName.setText(exercise.getName());

        // Handle row click (add to calories burned)
        convertView.setOnClickListener(v -> {
            v.setBackgroundColor(ContextCompat.getColor(context, R.color.teal_200));
            v.postDelayed(() -> v.setBackgroundColor(Color.TRANSPARENT), 200); // flash reset after 200ms
            actionListener.onRowClick(exercise);
        });


        editIcon.setOnClickListener(v -> {
            actionListener.onEditClick(exercise);
        });



        // Handle delete icon click
        deleteIcon.setOnClickListener(v -> actionListener.onDeleteClick(exercise));

        return convertView;
    }

    /** **************************************************
    FUNCTION NAME: showEditDialog
    FUNCTION PURPOSE: The function used when editing an exercise

    INPUT: the Exercise object
    ************************************************** */
    private void showEditDialog(Exercise exercise) {
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_edit_exercise, null);
        EditText nameInput = dialogView.findViewById(R.id.dialog_edit_exercise_name);
        EditText caloriesInput = dialogView.findViewById(R.id.dialog_edit_exercise_calories);

        // Pre-fill current values
        nameInput.setText(exercise.getName());
        caloriesInput.setText(String.valueOf(exercise.getCaloriesBurned()));

        new androidx.appcompat.app.AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.edit_exercise))
                .setView(dialogView)
                .setPositiveButton(context.getString(R.string.apply), (dialog, which) -> {
                    String newName = nameInput.getText().toString().trim();
                    String caloriesStr = caloriesInput.getText().toString().trim();

                    if (newName.isEmpty() || caloriesStr.isEmpty()) {
                        Toast.makeText(context, context.getString(R.string.dialog_all_fields_required), Toast.LENGTH_SHORT).show();
                        return;
                    }

                    try {
                        int newCalories = Integer.parseInt(caloriesStr);
                        Exercise updated = new Exercise(exercise.getExerciseID(), newName, newCalories);

                        // Call back to activity to apply update
                        actionListener.onEditClick(updated);
                    } catch (NumberFormatException e) {
                        Toast.makeText(context, context.getString(R.string.invalid_exercise_calories), Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(context.getString(R.string.cancel), null)
                .show();
    }

}
