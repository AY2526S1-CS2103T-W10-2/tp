package seedu.address.model.uuid;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.uuid.Uuid.StoredItem.CONTACT;
import static seedu.address.model.uuid.Uuid.StoredItem.PROPERTY;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Set;

import org.junit.jupiter.api.Test;

public class UuidTest {

    @Test
    public void constructor_invalidUuid_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Uuid(0, CONTACT)); // zero not allowed
        assertThrows(IllegalArgumentException.class, () -> new Uuid(-1, PROPERTY)); // negative not allowed
    }

    @Test
    public void constructor_validUuid_success() {
        assertDoesNotThrow(() -> new Uuid(1, CONTACT));
        assertDoesNotThrow(() -> new Uuid(1, PROPERTY));
    }

    @Test
    public void constructduplicateUuid_validUuid_success() {
        Uuid testUuid = new Uuid(1, CONTACT);
        assertDoesNotThrow(() -> new Uuid(testUuid));
    }

    @Test
    public void isValidUuid() {
        assertFalse(Uuid.isValidUuid("0"));
        assertFalse(Uuid.isValidUuid("-10"));
        assertFalse(Uuid.isValidUuid("2000001"));

        assertTrue(Uuid.isValidUuid("2000000"));
        assertTrue(Uuid.isValidUuid("1"));
        assertTrue(Uuid.isValidUuid("100"));
    }

    @Test
    public void getGuiSetDisplayAsString_validUuidSet_success() {
        Set<Uuid> uuids = Set.of(new Uuid(1, CONTACT), new Uuid(2, CONTACT), new Uuid(3, CONTACT));
        String displayString = Uuid.getGuiSetDisplayAsString(uuids);
        String expectedString = "1, 2, 3";
        assertEquals(expectedString, displayString);
    }

    @Test
    public void equals() {
        Uuid uuid = new Uuid(5, CONTACT);
        assertEquals(new Uuid(5, CONTACT), uuid);
        assertNotEquals(null, uuid);
        assertNotEquals("string", uuid);
        assertNotEquals(new Uuid(10, CONTACT), uuid);
        assertNotEquals(new Uuid(5, PROPERTY), uuid);
    }
}
