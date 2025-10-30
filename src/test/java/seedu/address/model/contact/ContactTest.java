package seedu.address.model.contact;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_BUYER;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalContacts.ALICE;
import static seedu.address.testutil.TypicalContacts.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.ContactBuilderUtil;

public class ContactTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Contact contact = new ContactBuilderUtil().build();
        assertThrows(UnsupportedOperationException.class, () -> contact.getTags().remove(0));
    }

    @Test
    public void equals() {
        //  boundary value: same object -> returns true
        assertEquals(ALICE, ALICE);

        // equivalence partition: null -> returns false
        assertNotEquals(null, ALICE);

        // equivalence partition: valid input
        // boundary value: same name and same contact,  all other attributes different -> returns true
        Contact editedAlice = new ContactBuilderUtil(ALICE)
                .withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_BUYER)
                .build();
        assertEquals(ALICE, editedAlice);

        // equivalence partition: valid input
        // boundary value: name differs in case but phone same -> returns true (case-insensitive check of duplicates)
        Contact editedBob = new ContactBuilderUtil(BOB).withName(VALID_NAME_BOB.toLowerCase()).build();
        assertEquals(BOB, editedBob);

        // equivalence partition: valid input
        // name has trailing spaces but phone same -> returns true (because trailing spaces are trimmed)
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBob = new ContactBuilderUtil(BOB).withName(nameWithTrailingSpaces).build();
        assertEquals(BOB, editedBob);

        // equivalence partition: valid input
        // different email -> returns true
        editedAlice = new ContactBuilderUtil(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertEquals(ALICE, editedAlice);

        // equivalence partition: invalid input
        // same name, different phone -> returns false
        editedAlice = new ContactBuilderUtil(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertNotEquals(ALICE, editedAlice);

        // equivalence partition: invalid input
        // different name, same phone -> returns false
        editedAlice = new ContactBuilderUtil(ALICE).withName(VALID_NAME_BOB).build();
        assertNotEquals(ALICE, editedAlice);
    }

    @Test
    public void toStringMethod() {
        String expected = Contact.class.getCanonicalName()
                + "{uuid=" + ALICE.getUuid().getValue()
                + ", name=" + ALICE.getName()
                + ", phone=" + ALICE.getPhone()
                + ", email=" + ALICE.getEmail()
                + ", address=" + ALICE.getAddress()
                + ", tags=" + ALICE.getTags()
                + ", budgetMin=" + ALICE.getBudgetMin()
                + ", budgetMax=" + ALICE.getBudgetMax()
                + ", notes=" + ALICE.getNotes()
                + ", status=" + ALICE.getStatus()
                + ", buyingPropertyIds=" + ALICE.getBuyingPropertyIds()
                + ", sellingPropertyIds=" + ALICE.getSellingPropertyIds()
                + "}";
        assertEquals(expected, ALICE.toString());
    }
}
