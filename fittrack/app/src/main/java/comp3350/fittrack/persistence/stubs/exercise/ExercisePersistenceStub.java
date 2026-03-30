package comp3350.fittrack.persistence.stubs.exercise;

import comp3350.fittrack.objects.Exercise;
import comp3350.fittrack.persistence.IExercisePersistence;

import java.util.ArrayList;
import java.util.Iterator;

/** **************************************************
 CLASS NAME: ExercisePersistenceStub.java

 Stub for managing exercise data in-memory for testing.

 ************************************************** */

public class ExercisePersistenceStub implements IExercisePersistence {

    private ArrayList<Exercise> exerciseList;

    public ExercisePersistenceStub(){
        this.exerciseList = new ArrayList<Exercise>();
        exerciseList.add(new Exercise( 1,"Running", 100));
        exerciseList.add(new Exercise( 2,"Swimming", 200));
        exerciseList.add(new Exercise( 3,"Cycling", 150));
        exerciseList.add(new Exercise( 4,"Walking", 50));
    }
    public Exercise getExerciseByName(String exerciseName) {
        for (Exercise exercise : exerciseList) {
            if (exercise.getName().equals(exerciseName)) {
                return exercise;
            }
        }
        return null;
    }

    @Override
    public Exercise getExerciseById(int exerciseID) {
        for (Exercise exercise : exerciseList) {
            if (exercise.getExerciseID() == exerciseID) {
                return exercise;
            }
        }
        return null;
    }

    @Override
    public ArrayList<Exercise> getExerciseList() {
        return exerciseList;
    }

    @Override
    public Exercise insertExercise(Exercise exercise) {
        if (exercise != null) {
            int nextId = exerciseList.size() + 1; // Simulate auto-increment ID
            Exercise newExercise = new Exercise(nextId, exercise.getName(), exercise.getCaloriesBurned());
            exerciseList.add(newExercise);
            return newExercise;
        }
        return null;
    }

    @Override
    public void updateExercise(int exerciseID, Exercise updatedExercise) {
        for (int i = 0; i < exerciseList.size(); i++) {
            if (exerciseList.get(i).getExerciseID() == exerciseID) {
                exerciseList.set(i, updatedExercise);  // Replace the old exercise with the updated one
                return;
            }
        }
    }

    @Override
    public void deleteExercise(int exerciseID) {
        Iterator<Exercise> iterator = exerciseList.iterator();  // Create an iterator
        while (iterator.hasNext()) {
            Exercise exercise = iterator.next();
            if (exercise.getExerciseID() == exerciseID) {
                iterator.remove();  // Safely remove the exercise using the iterator
                return;
            }
        }
    }
}
