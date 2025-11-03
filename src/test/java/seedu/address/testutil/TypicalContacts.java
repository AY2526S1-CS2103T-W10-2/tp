package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.contact.Contact;

/**
 * A utility class containing a list of {@code Contact} objects to be used in tests.
 */
public class TypicalContacts {

    public static final Contact ALICE = new ContactBuilderUtil().withName("Alice Pauline")
            .withUuid(1)
            .withAddress("123, Jurong West Ave 6, #08-111")
            .withEmail("alice@example.com")
            .withPhone("94351253")
            .withBudgetMin("0")
            .withBudgetMax("1000")
            .withNotes("No notes")
            .withStatus("Active")
            .build();

    public static final Contact BENSON = new ContactBuilderUtil().withName("Benson Meier")
            .withUuid(2)
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com")
            .withPhone("98765432")
            .withBudgetMin("100")
            .withBudgetMax("2000")
            .withNotes("Owes money")
            .withStatus("Active")
            .build();

    public static final Contact CARL = new ContactBuilderUtil().withName("Carl Kurz")
            .withUuid(3)
            .withPhone("95352563")
            .withEmail("heinz@example.com")
            .withAddress("wall street")
            .withBudgetMin("50")
            .withBudgetMax("1500")
            .withNotes("No notes")
            .withStatus("Inactive")
            .build();

    public static final Contact DANIEL = new ContactBuilderUtil().withName("Daniel Meier")
            .withUuid(4)
            .withPhone("87652533")
            .withEmail("cornelia@example.com")
            .withAddress("10th street")
            .withBudgetMin("200")
            .withBudgetMax("3000")
            .withNotes("Friend from work")
            .withStatus("Active")
            .build();

    public static final Contact ELLE = new ContactBuilderUtil().withName("Elle Meyer")
            .withUuid(5)
            .withPhone("9482224")
            .withEmail("werner@example.com")
            .withAddress("michegan ave")
            .withBudgetMin("800000")
            .withBudgetMax("1000000")
            .withNotes("")
            .withStatus("")
            .build();

    public static final Contact FIONA = new ContactBuilderUtil().withName("Fiona Kunz")
            .withUuid(6)
            .withPhone("9482427")
            .withEmail("lydia@example.com")
            .withAddress("little tokyo")
            .withBudgetMin("800000")
            .withBudgetMax("1000000")
            .withNotes("VIP client")
            .withStatus("Inactive")
            .build();

    public static final Contact GEORGE = new ContactBuilderUtil().withName("George Best")
            .withUuid(7)
            .withPhone("9482442")
            .withEmail("anna@example.com")
            .withAddress("4th street")
            .withBudgetMin("800000")
            .withBudgetMax("1000000")
            .withNotes("")
            .withStatus("")
            .build();

    // Manually added
    public static final Contact HOON = new ContactBuilderUtil().withName("Hoon Meier")
            .withUuid(8)
            .withPhone("8482424")
            .withEmail("stefan@example.com")
            .withAddress("little india")
            .withBudgetMin("50")
            .withBudgetMax("1000")
            .withNotes("Test contact")
            .withStatus("Inactive")
            .build();

    public static final Contact IDA = new ContactBuilderUtil().withName("Ida Mueller")
            .withUuid(9)
            .withPhone("8482131")
            .withEmail("hans@example.com")
            .withAddress("chicago ave")
            .withBudgetMin("150")
            .withBudgetMax("2500")
            .withNotes("")
            .withStatus("Active")
            .build();

    // Manually added - Contact's details found in {@code CommandTestUtil}
    public static final Contact AMY = new ContactBuilderUtil().withName(VALID_NAME_AMY)
            .withUuid(10)
            .withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY)
            .withAddress(VALID_ADDRESS_AMY)
            .withBudgetMin("100")
            .withBudgetMax("1000")
            .withNotes("Friend from school")
            .withStatus("Active")
            .build();

    public static final Contact BOB = new ContactBuilderUtil().withName(VALID_NAME_BOB)
            .withUuid(11)
            .withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB)
            .withAddress(VALID_ADDRESS_BOB)
            .withBudgetMin("200")
            .withBudgetMax("2000")
            .withNotes("Brother")
            .withStatus("Inactive")
            .build();

    private TypicalContacts() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical contacts.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Contact contact : getTypicalContacts()) {
            ab.addContact(contact);
        }
        return ab;
    }

    public static List<Contact> getTypicalContacts() {
        return new ArrayList<>(Arrays.asList(new ContactBuilderUtil(ALICE).build(),
                new ContactBuilderUtil(BENSON).build(),
                new ContactBuilderUtil(CARL).build(),
                new ContactBuilderUtil(DANIEL).build(),
                new ContactBuilderUtil(ELLE).build(),
                new ContactBuilderUtil(FIONA).build(),
                new ContactBuilderUtil(GEORGE).build()));
    }
}
