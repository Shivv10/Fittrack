package comp3350.fittrack.presentation.food;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import comp3350.fittrack.R;
import comp3350.fittrack.business.food.AccessFood;
import comp3350.fittrack.objects.Food;

/** **************************************************
 CLASS NAME: FoodListAdapter.java

 Adapter for showing a list of Food items in the UI.
 Allows in-place edit and delete actions via icons.

 ************************************************** */

public class FoodListAdapter extends BaseAdapter {
    private Context context;
    private List<Food> foodList;
    private AccessFood foodManager;

    public FoodListAdapter(Context context, List<Food> foodList) {
        this.context = context;
        this.foodList = foodList;
        this.foodManager = new AccessFood();
    }

    @Override
    public int getCount() {
        return foodList.size();
    }

    @Override
    public Object getItem(int position) {
        return foodList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return foodList.get(position).getFoodID();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Food food = foodList.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_food, parent, false);
        }

        TextView nameText = convertView.findViewById(R.id.food_name);
        TextView calorieText = convertView.findViewById(R.id.food_calories);
        ImageView editIcon = convertView.findViewById(R.id.edit_icon);
        ImageView deleteIcon = convertView.findViewById(R.id.delete_icon);

        nameText.setText(food.getName());
        calorieText.setText(food.getCalories() + " cal");

        editIcon.setOnClickListener(v -> {
            showEditDialog(context, food, this::notifyDataSetChanged);
        });


        deleteIcon.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Confirm Deletion")
                    .setMessage("Are you sure you want to delete \"" + food.getName() + "\"?")
                    .setPositiveButton("Delete", (dialog, which) -> {
                        // Perform delete AFTER user confirmation
                        foodManager.getFoodList().deleteFood(food.getFoodID());
                        foodList.remove(position);
                        notifyDataSetChanged();
                        Toast.makeText(context, food.getName() + " deleted", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });


        return convertView;
    }

    public void showEditDialog(Context context, Food food, Runnable onFoodUpdated) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_edit_food, null);

        EditText editName = dialogView.findViewById(R.id.edit_food_name);
        EditText editCalories = dialogView.findViewById(R.id.edit_food_calories);

        editName.setText(food.getName());
        editCalories.setText(String.valueOf(food.getCalories()));

        builder.setView(dialogView);
        builder.setTitle("Edit Food Item");
        builder.setPositiveButton("Save", (dialog, which) -> {
            String name = editName.getText().toString().trim();
            int calories = Integer.parseInt(editCalories.getText().toString().trim());

            Food updatedFood = new Food(food.getFoodID(), name, calories);
            AccessFood accessFood = new AccessFood();
            accessFood.getFoodList().updateFood(updatedFood);

            Toast.makeText(context, "Food updated!", Toast.LENGTH_SHORT).show();
            onFoodUpdated.run();
        });
        builder.setNegativeButton("Cancel", null);

        builder.create().show();
    }

}
