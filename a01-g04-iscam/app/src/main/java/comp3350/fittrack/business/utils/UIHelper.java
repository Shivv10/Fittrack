package comp3350.fittrack.business.utils;

import java.util.Arrays;
import java.util.List;

/**
 * Holds UI-related constants and configuration settings.
 */
public class UIHelper {

    /**
     * Returns an array of selectable ages for the age spinner.
     *
     * @return Integer array containing valid ages.
     */
    public static Integer[] getAgeList() {
        Integer[] ages = new Integer[109];
        for (int i = 12; i <= 120; i++) {
            ages[i - 12] = i;
        }
        return ages;
    }

    /**
     * Returns a list of selectable ages.
     *
     * @return List containing valid ages.
     */
    public static List<Integer> getAgeListAsList() {
        return Arrays.asList(getAgeList());
    }

}
