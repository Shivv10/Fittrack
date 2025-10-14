package comp3350.srsys.tests.unittests.business;

import org.junit.Test;

import static org.junit.Assert.*;

import comp3350.fittrack.business.utils.UIHelper;

public class UIHelperTest {

    @Test
    public void testGetAgeList() {
        Integer[] ages = UIHelper.getAgeList();
        assertEquals("Age list should contain 109 values.", 109, ages.length);
        assertEquals("First value should be 12.", 12, (int) ages[0]);
        assertEquals("Last value should be 120.", 120, (int) ages[ages.length - 1]);
    }
}

