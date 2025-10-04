package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class BudgetMinTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new BudgetMin(null));
    }

    @Test
    public void constructor_invalidBudgetMin_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new BudgetMin("abc"));
        assertThrows(IllegalArgumentException.class, () -> new BudgetMin("-1"));
        assertThrows(IllegalArgumentException.class, () -> new BudgetMin("12.5"));
    }

    @Test
    public void isValidBudgetMin() {
        assertFalse(BudgetMin.isValidBudgetMin(null));
        assertFalse(BudgetMin.isValidBudgetMin("-1"));
        assertFalse(BudgetMin.isValidBudgetMin("abc"));
        assertTrue(BudgetMin.isValidBudgetMin("0"));
        assertTrue(BudgetMin.isValidBudgetMin("123"));
    }

    @Test
    public void equals() {
        BudgetMin b1 = new BudgetMin("100");
        assertEquals(new BudgetMin("100"), b1);
        assertEquals(b1, b1);
        assertNotEquals(null, b1);
        assertNotEquals("string", b1);
        assertNotEquals(new BudgetMin("200"), b1);
    }
}
