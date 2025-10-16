package seedu.address.model.uuid;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.uuid.Uuid.StoredItem.PERSON;
import static seedu.address.model.uuid.Uuid.StoredItem.PROPERTY;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Set;

import org.junit.jupiter.api.Test;

public class UuidTest {

    @Test
    public void constructor_invalidUuid_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Uuid(0, PERSON)); // zero not allowed
        assertThrows(IllegalArgumentException.class, () -> new Uuid(-1, PROPERTY)); // negative not allowed
    }

    @Test
    public void constructor_validUuid_success() {
        assertDoesNotThrow(() -> new Uuid(1, PERSON));
        assertDoesNotThrow(() -> new Uuid(1, PROPERTY));
    }

    @Test
    public void constructduplicateUuid_validUuid_success() {
        Uuid testUuid = new Uuid(1, PERSON);
        assertDoesNotThrow(() -> new Uuid(testUuid));
    }

    @Test
    public void isValidUuid() {
        assertFalse(Uuid.isValidUuid(0));
        assertFalse(Uuid.isValidUuid(-10));
        assertTrue(Uuid.isValidUuid(1));
        assertTrue(Uuid.isValidUuid(100));
    }

    @Test
    public void getGuiSetDisplayAsString_validUuidSet_success() {
        Set<Uuid> uuids = Set.of(new Uuid(1, PERSON), new Uuid(2, PERSON), new Uuid(3, PERSON));
        String displayString = Uuid.getGuiSetDisplayAsString(uuids);
        String expectedString = "1, 2, 3";
        assertEquals(expectedString, displayString);
    }

    @Test
    public void equals() {
        Uuid uuid = new Uuid(5, PERSON);
        assertEquals(new Uuid(5, PERSON), uuid);
        assertNotEquals(null, uuid);
        assertNotEquals("string", uuid);
        assertNotEquals(new Uuid(10, PERSON), uuid);
        assertNotEquals(new Uuid(5, PROPERTY), uuid);
    }
}
