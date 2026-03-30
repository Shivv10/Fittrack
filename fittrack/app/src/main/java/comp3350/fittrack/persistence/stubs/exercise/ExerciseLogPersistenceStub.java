package comp3350.fittrack.persistence.stubs.exercise;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import comp3350.fittrack.objects.ExerciseLog;
import comp3350.fittrack.persistence.IExerciseLogPersistence;

/** **************************************************
 CLASS NAME: ExerciseLogPersistenceStub.java

 Stub implementation of IExerciseLogPersistence using in-memory list.

 ************************************************** */

public class ExerciseLogPersistenceStub implements IExerciseLogPersistence {

    private final List<ExerciseLog> exerciseLogs;

    public ExerciseLogPersistenceStub() {
        this.exerciseLogs = new ArrayList<>();
    }

    @Override
    public boolean addExerciseLog(ExerciseLog exerciseLog) {
        if (exerciseLog != null) {
            return exerciseLogs.add(exerciseLog);
        }
        return false;
    }

    @Override
    public boolean updateExerciseLog(int userID, int exerciseID, String date, ExerciseLog updatedLog) {
        for (int i = 0; i < exerciseLogs.size(); i++) {
            ExerciseLog log = exerciseLogs.get(i);
            if (log.getUserId() == userID && log.getExerciseId() == exerciseID && log.getDatePerformed().equals(date)) {
                exerciseLogs.set(i, updatedLog);
                return true;
            }
        }
        return false;
    }

    @Override
    public List<ExerciseLog> getExerciseLogsByUserId(int userId) {
        List<ExerciseLog> userLogs = new ArrayList<>();
        for (ExerciseLog log : exerciseLogs) {
            if (log.getUserId() == userId) {
                userLogs.add(log);
            }
        }
        return userLogs;
    }

    @Override
    public boolean deleteExerciseLog(int userID, int exerciseID, String date) {
        Iterator<ExerciseLog> iterator = exerciseLogs.iterator();
        while (iterator.hasNext()) {
            ExerciseLog log = iterator.next();
            if (log.getUserId() == userID && log.getExerciseId() == exerciseID && log.getDatePerformed().equals(date)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteLogsByExerciseId(int exerciseId) {
        boolean found = false;
        Iterator<ExerciseLog> iterator = exerciseLogs.iterator();
        while (iterator.hasNext()) {
            ExerciseLog log = iterator.next();
            if (log.getExerciseId() == exerciseId) {
                iterator.remove();
                found = true;
            }
        }
        return found;
    }
}
