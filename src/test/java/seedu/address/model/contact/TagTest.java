package seedu.address.model.contact;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TagTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Tag(null));
    }

    @Test
    public void constructor_invalidTag_throwsIllegalArgumentException() {
        String invalidTag = "invalid";
        assertThrows(IllegalArgumentException.class, () -> new Tag(invalidTag));
    }

    @Test
    public void isValidTagName() {
        // null tag
        assertThrows(NullPointerException.class, () -> Tag.isValidTagName(null));

        // invalid tags
        assertFalse(Tag.isValidTagName("friend")); // not in allowed list
        assertFalse(Tag.isValidTagName("buyer seller")); // multiple values
        assertFalse(Tag.isValidTagName("buy")); // partial match
        assertFalse(Tag.isValidTagName("123")); // numeric
        assertFalse(Tag.isValidTagName("buyer123")); // alphanumeric

        // valid tags
        assertTrue(Tag.isValidTagName("")); // empty string allowed
        assertTrue(Tag.isValidTagName("buyer")); // lowercase
        assertTrue(Tag.isValidTagName("BUYER")); // uppercase
        assertTrue(Tag.isValidTagName("Buyer")); // mixed case
        assertTrue(Tag.isValidTagName("seller"));
        assertTrue(Tag.isValidTagName("TENANT"));
        assertTrue(Tag.isValidTagName("landlord"));
    }

    @Test
    public void equals() {
        Tag tag = new Tag("buyer");

        // same values -> returns true
        assertEquals(new Tag("buyer"), tag);

        // same values different case -> returns true (stored as lowercase)
        assertEquals(new Tag("BUYER"), tag);
        assertEquals(new Tag("Buyer"), tag);

        // same object -> returns true
        assertEquals(tag, tag);

        // null -> returns false
        assertNotEquals(null, tag);

        // different types -> returns false
        assertNotEquals(5.0f, tag);

        // different values -> returns false
        assertNotEquals(new Tag("seller"), tag);
    }

    @Test
    public void toString_returnsLowercaseTagName() {
        assertEquals("buyer", new Tag("buyer").toString());
        assertEquals("buyer", new Tag("BUYER").toString());
        assertEquals("seller", new Tag("Seller").toString());
        assertEquals("", new Tag("").toString());
    }
}
