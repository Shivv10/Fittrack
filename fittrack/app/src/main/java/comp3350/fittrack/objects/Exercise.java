package comp3350.fittrack.objects;

/** **************************************************
 CLASS NAME: Exercise.java

 CLASS FUNCTION: Represents an exercise activity.

 Stores unique exercise ID, name, and calories burned per session.

 ************************************************** */

public class Exercise {
    private final int exerciseID;
    private final String name;
    private final int caloriesBurned;

    public Exercise(int exerciseID, String name, int caloriesBurned) {
        this.exerciseID = exerciseID;
        this.name = name;
        this.caloriesBurned = caloriesBurned;
    }

    public int getExerciseID() {
        return exerciseID;
    }

    public String getName() {
        return name;
    }

    public int getCaloriesBurned() {
        return caloriesBurned;
    }

    @Override
    public String toString() {
        return name + " (" + caloriesBurned + " cal)";
    }
}
