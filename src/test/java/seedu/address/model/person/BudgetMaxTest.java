package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class BudgetMaxTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new BudgetMax(null));
    }

    @Test
    public void constructor_invalidBudgetMax_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new BudgetMax("abc"));
        assertThrows(IllegalArgumentException.class, () -> new BudgetMax("-1"));
        assertThrows(IllegalArgumentException.class, () -> new BudgetMax("10.5"));
    }

    @Test
    public void isValidBudgetMax() {
        assertFalse(BudgetMax.isValidBudgetMax(null));
        assertFalse(BudgetMax.isValidBudgetMax("-1"));
        assertFalse(BudgetMax.isValidBudgetMax("abc"));
        assertTrue(BudgetMax.isValidBudgetMax("0"));
        assertTrue(BudgetMax.isValidBudgetMax("7890"));
    }

    @Test
    public void equals() {
        BudgetMax b1 = new BudgetMax("500");
        assertEquals(new BudgetMax("500"), b1);
        assertEquals(b1, b1);
        assertNotEquals(null, b1);
        assertNotEquals("string", b1);
        assertNotEquals(new BudgetMax("1000"), b1);
    }
}
