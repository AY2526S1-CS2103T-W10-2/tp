package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class StatusTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Status(null));
    }

    @Test
    public void constructor_invalidStatus_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Status("invalid"));
        assertThrows(IllegalArgumentException.class, () -> new Status(" "));
        assertThrows(IllegalArgumentException.class, () -> new Status("Actve"));
    }

    @Test
    public void isValidStatus() {
        assertTrue(Status.isValidStatus("Active"));
        assertTrue(Status.isValidStatus("Inactive"));
        assertTrue(Status.isValidStatus("")); // empty string allowed
        assertTrue(Status.isValidStatus("active")); // case-insensitive
        assertTrue(Status.isValidStatus("INACTIVE"));
        assertFalse(Status.isValidStatus("Invalid"));
    }

    @Test
    public void equals() {
        Status s1 = new Status("Active");
        assertEquals(new Status("Active"), s1);
        assertEquals(s1, s1);
        assertNotEquals(null, s1);
        assertNotEquals("string", s1);
        assertNotEquals(new Status("Inactive"), s1);
    }
}
