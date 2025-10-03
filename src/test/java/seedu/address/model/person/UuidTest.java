package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class UuidTest {

    @Test
    public void constructor_invalidUuid_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Uuid(0)); // zero not allowed
        assertThrows(IllegalArgumentException.class, () -> new Uuid(-1)); // negative not allowed
    }

    @Test
    public void constructor_validUuid_success() {
        assertDoesNotThrow(() -> new Uuid(1));
    }

    @Test
    public void isValidUuid() {
        assertFalse(Uuid.isValidUuid(0));
        assertFalse(Uuid.isValidUuid(-10));
        assertTrue(Uuid.isValidUuid(1));
        assertTrue(Uuid.isValidUuid(100));
    }

    @Test
    public void equals() {
        Uuid uuid = new Uuid(5);
        assertEquals(new Uuid(5), uuid);
        assertNotEquals(null, uuid);
        assertNotEquals("string", uuid);
        assertNotEquals(new Uuid(10), uuid);
    }
}
