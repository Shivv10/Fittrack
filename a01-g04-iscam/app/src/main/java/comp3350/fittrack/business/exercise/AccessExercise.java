package comp3350.fittrack.business.exercise;

import comp3350.fittrack.application.Services;
import comp3350.fittrack.business.utils.ValidationUtils;
import comp3350.fittrack.business.config.LiteralsConfig;
import comp3350.fittrack.business.exercise.exceptions.ExerciseNotFoundException;
import comp3350.fittrack.persistence.hsqldb.exceptions.HSQLDBException;
import comp3350.fittrack.business.exercise.exceptions.InvalidExerciseException;
import comp3350.fittrack.objects.Exercise;
import comp3350.fittrack.persistence.IExercisePersistence;

import java.util.ArrayList;
import java.util.List;

/** **************************************************
 CLASS NAME: AccessExercise.java

 Handles logic related to exercises (add, retrieve, update, delete).

 Connects to IExercisePersistence via the Services factory for loose coupling.

 ************************************************** */

public class AccessExercise {
    private final IExercisePersistence exercisePersistence;
    private String searchPhrase;
    private List<String> filter;

    public AccessExercise() {
        try {
            this.exercisePersistence = Services.getExercisePersistence();
        } catch (HSQLDBException e) {
            throw new RuntimeException(e);
        }
        this.filter = new ArrayList<>();
        this.searchPhrase = null;
    }

    public AccessExercise(IExercisePersistence exercisePersistence) {
        this.exercisePersistence = exercisePersistence;
        this.filter = new ArrayList<>();
        this.searchPhrase = null;
    }

    public Exercise insertExercise(String name, int calories) throws InvalidExerciseException {
        ValidationUtils.validateExerciseParams(name, calories);
        Exercise newExercise = new Exercise(-1, name.trim(), calories);
        return exercisePersistence.insertExercise(newExercise);
    }

    public List<Exercise> getAllExercises() {
        return exercisePersistence.getExerciseList();
    }

    public Exercise getExerciseById(int exerciseId) {
        if (exerciseId > 0) {
            return exercisePersistence.getExerciseById(exerciseId);
        }
        return null;
    }

    public Exercise getExerciseByName(String exerciseTitle) {
        return exercisePersistence.getExerciseByName(exerciseTitle);
    }

    public void deleteExerciseById(int exerciseId) throws InvalidExerciseException, ExerciseNotFoundException {
        if (exerciseId < 0) {
            throw new InvalidExerciseException(LiteralsConfig.ERROR_EXERCISE_ID_INVALID);
        }
        exercisePersistence.deleteExercise(exerciseId);
    }

    public void deleteExerciseAndLogs(int exerciseId) throws InvalidExerciseException, ExerciseNotFoundException {
        if (exerciseId < 0) {
            throw new InvalidExerciseException(LiteralsConfig.ERROR_EXERCISE_ID_INVALID);
        }

        AccessExerciseLogs accessExerciseLogs = new AccessExerciseLogs();
        // Delete all logs linked to this exercise before removing the exercise itself
        accessExerciseLogs.deleteAllLogsForExercise(exerciseId);

        exercisePersistence.deleteExercise(exerciseId);
    }


    public void updateExercise(Exercise updatedExercise) throws InvalidExerciseException {
        ValidationUtils.validateExerciseParams(updatedExercise.getName(), updatedExercise.getCaloriesBurned());
        exercisePersistence.updateExercise(updatedExercise.getExerciseID(), updatedExercise);
    }
}
