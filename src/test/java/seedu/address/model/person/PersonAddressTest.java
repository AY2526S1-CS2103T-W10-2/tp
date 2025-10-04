package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class PersonAddressTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new PersonAddress(null));
    }

    @Test
    public void constructor_blankAddress_isAllowed() {
        assertTrue(PersonAddress.isValidAddress(""));
    }

    @Test
    public void isValidAddress() {
        // null address
        assertThrows(NullPointerException.class, () -> PersonAddress.isValidAddress(null));

        // invalid addresses
        assertFalse(PersonAddress.isValidAddress(" ")); // spaces only

        // valid addresses
        assertTrue(PersonAddress.isValidAddress("Blk 456, Den Road, #01-355"));
        assertTrue(PersonAddress.isValidAddress("-")); // one character
        assertTrue(PersonAddress.isValidAddress(
                "Leng Inc; 1234 Market St; San Francisco CA 2349879; USA")); // long address
    }

    @Test
    public void equals() {
        PersonAddress address = new PersonAddress("Valid Address");

        // same values -> returns true
        assertTrue(address.equals(new PersonAddress("Valid Address")));

        // same object -> returns true
        assertTrue(address.equals(address));

        // null -> returns false
        assertFalse(address.equals(null));

        // different types -> returns false
        assertFalse(address.equals(5.0f));

        // different values -> returns false
        assertFalse(address.equals(new PersonAddress("Other Valid Address")));
    }
}
