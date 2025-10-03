package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class NotesTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Notes(null));
    }

    @Test
    public void constructor_invalidNotes_throwsIllegalArgumentException() {
        // Notes accept anything up to 500 chars, so test exceeding length
        String tooLong = "a".repeat(501);
        assertThrows(IllegalArgumentException.class, () -> new Notes(tooLong));
    }

    @Test
    public void isValidNotes() {
        assertTrue(Notes.isValidNotes(""));
        assertTrue(Notes.isValidNotes("any notes here"));
        String maxLength = "a".repeat(500);
        assertTrue(Notes.isValidNotes(maxLength));
        String tooLong = "a".repeat(501);
        assertFalse(Notes.isValidNotes(tooLong));
    }

    @Test
    public void equals() {
        Notes n1 = new Notes("note1");
        assertEquals(new Notes("note1"), n1);
        assertEquals(n1, n1);
        assertNotEquals(null, n1);
        assertNotEquals("string", n1);
        assertNotEquals(new Notes("different"), n1);
    }
}
