package comp3350.fittrack.persistence.stubs.food;

import comp3350.fittrack.objects.FoodLog;
import comp3350.fittrack.persistence.IFoodLogPersistence;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/** **************************************************
 CLASS NAME: FoodLogPersistenceStub.java

 Stub for managing user food log entries using a static list.

 ************************************************** */

public class FoodLogPersistenceStub implements IFoodLogPersistence {
    private List<FoodLog> foodLogs;

    public FoodLogPersistenceStub() {
        foodLogs = new ArrayList<>();

        String date1 = LocalDate.of(2021, 1, 1).toString();
        String date2 = LocalDate.of(2021, 1, 2).toString();
        String date3 = LocalDate.of(2021, 1, 17).toString();

        foodLogs.add(new FoodLog(0, 1, date1, 25));
        foodLogs.add(new FoodLog(0, 2, date1, 200));
        foodLogs.add(new FoodLog(1, 3, date3, 50));
        foodLogs.add(new FoodLog(2, 1, date1, 30));
        foodLogs.add(new FoodLog(2, 2, date1, 150));
        foodLogs.add(new FoodLog(2, 5, date1, 200));
        foodLogs.add(new FoodLog(2, 6, date1, 100));
        foodLogs.add(new FoodLog(2, 4, date2, 150));
        foodLogs.add(new FoodLog(2, 5, date2, 200));
    }

    @Override
    public FoodLog getFoodLog(int userID, int foodID, String date) {
        boolean found = false;
        FoodLog result = null;
        for (int i = 0; i < foodLogs.size() && !found; i++) {
            FoodLog log = foodLogs.get(i);
            if (log.getUserId() == userID && log.getFoodId() == foodID) {
                found = true;
                result = log;
            }
        }
        return result;
    }

    @Override
    public List<FoodLog> getFoodLogsByUserId(int userID) {
        List<FoodLog> list = new ArrayList<>();
        for (int i = 0; i < foodLogs.size(); i++) {
            if (foodLogs.get(i).getUserId() == userID)
                list.add(foodLogs.get(i));
        }
        return list;
    }

    @Override
    public List<FoodLog> getFoodLogByUserDate(int userID, String date) {
        List<FoodLog> list = new ArrayList<>();
        for (int i = 0; i < foodLogs.size(); i++) {
            FoodLog log = foodLogs.get(i);
            if (log.getUserId() == userID && log.getDate().equals(date))
                list.add(log);
        }
        return list;
    }

    @Override
    public boolean addFoodLog(FoodLog foodLog) {
        if (foodLog != null) {
            return foodLogs.add(foodLog);
        }
        return false;
    }

    @Override
    public boolean updateFoodLog(int userID, int foodID, String date, FoodLog updatedLog) {
        boolean found = false;
        for (int i = 0; i < foodLogs.size() && !found; i++) {
            FoodLog log = foodLogs.get(i);
            if (log.getUserId() == userID && log.getFoodId() == foodID && log.getDate().equals(date)) {
                foodLogs.set(i, updatedLog);
                found = true;
            }
        }
        return found;
    }

    @Override
    public void deleteFoodLog(int userID, int foodID, String date) {
        boolean found = false;
        for (int i = 0; i < foodLogs.size() && !found; i++) {
            FoodLog log = foodLogs.get(i);
            if (log.getUserId() == userID && log.getFoodId() == foodID && log.getDate().equals(date)) {
                foodLogs.remove(i);
                found = true;
            }
        }
    }

    @Override
    public boolean deleteLogsByFoodId(int foodID) {
        boolean found = false;
        Iterator<FoodLog> iterator = foodLogs.iterator();
        while (iterator.hasNext()) {
            FoodLog log = iterator.next();
            if (log.getFoodId() == foodID) {
                iterator.remove();
                found = true;
            }
        }
        return found;
    }
}

