package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Person;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline")
            .withUuid(1)
            .withAddress("123, Jurong West Ave 6, #08-111")
            .withEmail("alice@example.com")
            .withPhone("94351253")
            .withBudgetMin("0")
            .withBudgetMax("1000")
            .withNotes("No notes")
            .withStatus("Active")
            .withTags("friends")
            .build();

    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withUuid(2)
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com")
            .withPhone("98765432")
            .withBudgetMin("100")
            .withBudgetMax("2000")
            .withNotes("Owes money")
            .withStatus("Active")
            .withTags("owesMoney", "friends")
            .build();

    public static final Person CARL = new PersonBuilder().withName("Carl Kurz")
            .withUuid(3)
            .withPhone("95352563")
            .withEmail("heinz@example.com")
            .withAddress("wall street")
            .withBudgetMin("50")
            .withBudgetMax("1500")
            .withNotes("No notes")
            .withStatus("Inactive")
            .build();

    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier")
            .withUuid(4)
            .withPhone("87652533")
            .withEmail("cornelia@example.com")
            .withAddress("10th street")
            .withBudgetMin("200")
            .withBudgetMax("3000")
            .withNotes("Friend from work")
            .withStatus("Active")
            .withTags("friends")
            .build();

    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer")
            .withUuid(5)
            .withPhone("9482224")
            .withEmail("werner@example.com")
            .withAddress("michegan ave")
            .withBudgetMin("0")
            .withBudgetMax("1200")
            .withNotes("")
            .withStatus("Active")
            .build();

    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz")
            .withUuid(6)
            .withPhone("9482427")
            .withEmail("lydia@example.com")
            .withAddress("little tokyo")
            .withBudgetMin("300")
            .withBudgetMax("4000")
            .withNotes("VIP client")
            .withStatus("Inactive")
            .build();

    public static final Person GEORGE = new PersonBuilder().withName("George Best")
            .withUuid(7)
            .withPhone("9482442")
            .withEmail("anna@example.com")
            .withAddress("4th street")
            .withBudgetMin("0")
            .withBudgetMax("500")
            .withNotes("")
            .withStatus("Active")
            .build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier")
            .withUuid(8)
            .withPhone("8482424")
            .withEmail("stefan@example.com")
            .withAddress("little india")
            .withBudgetMin("50")
            .withBudgetMax("1000")
            .withNotes("Test contact")
            .withStatus("Inactive")
            .build();

    public static final Person IDA = new PersonBuilder().withName("Ida Mueller")
            .withUuid(9)
            .withPhone("8482131")
            .withEmail("hans@example.com")
            .withAddress("chicago ave")
            .withBudgetMin("150")
            .withBudgetMax("2500")
            .withNotes("")
            .withStatus("Active")
            .build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY)
            .withUuid(10)
            .withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY)
            .withAddress(VALID_ADDRESS_AMY)
            .withBudgetMin("100")
            .withBudgetMax("1000")
            .withNotes("Friend from school")
            .withStatus("Active")
            .withTags(VALID_TAG_FRIEND)
            .build();

    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB)
            .withUuid(11)
            .withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB)
            .withAddress(VALID_ADDRESS_BOB)
            .withBudgetMin("200")
            .withBudgetMax("2000")
            .withNotes("Brother")
            .withStatus("Inactive")
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalPersons()) {
            ab.addPerson(person);
        }
        return ab;
    }

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
