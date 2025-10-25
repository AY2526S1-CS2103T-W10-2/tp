package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalContacts.AMY;
import static seedu.address.testutil.TypicalContacts.BOB;
import static seedu.address.testutil.TypicalProperties.PROPERTY_ALPHA;
import static seedu.address.testutil.TypicalProperties.PROPERTY_BETA;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.FilterContactPredicate;
import seedu.address.model.property.Property;
import seedu.address.model.property.predicates.PropertyMatchesFilterPredicate;
import seedu.address.testutil.EditContactDescriptorBuilder;
import seedu.address.testutil.LinkDescriptorBuilder;
import seedu.address.testutil.UnlinkDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_TAG_BUYER = "buyer";
    public static final String VALID_TAG_TENANT = "tenant";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String TAG_DESC_BUYER = " " + PREFIX_TAG + VALID_TAG_BUYER;
    public static final String TAG_DESC_TENANT = " " + PREFIX_TAG + VALID_TAG_TENANT;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_TAG = "hubby*"; // '*' not allowed in tags
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + INVALID_TAG;

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditContactCommand.EditContactDescriptor DESC_AMY;
    public static final EditContactCommand.EditContactDescriptor DESC_BOB;

    public static final LinkCommand.LinkDescriptor LINK_DESC_AMY_BUYER_PROPERTY_ALPHA;
    public static final LinkCommand.LinkDescriptor LINK_DESC_BOB_SELLER_PROPERTY_BETA;

    public static final UnlinkCommand.UnlinkDescriptor UNLINK_DESC_AMY_PROPERTY_ALPHA;
    public static final UnlinkCommand.UnlinkDescriptor UNLINK_DESC_BOB_PROPERTY_BETA;

    static {
        DESC_AMY = new EditContactDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withTags(VALID_TAG_BUYER).build();
        DESC_BOB = new EditContactDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_BUYER, VALID_TAG_TENANT).build();

        LINK_DESC_AMY_BUYER_PROPERTY_ALPHA = new LinkDescriptorBuilder().withContactIds(Set.of(AMY.getUuid()))
                .withRelationship("buyer").withPropertyIds(Set.of(PROPERTY_ALPHA.getUuid())).build();
        LINK_DESC_BOB_SELLER_PROPERTY_BETA = new LinkDescriptorBuilder().withContactIds(Set.of(BOB.getUuid()))
                .withRelationship("seller").withPropertyIds(Set.of(PROPERTY_BETA.getUuid())).build();

        UNLINK_DESC_AMY_PROPERTY_ALPHA = new UnlinkDescriptorBuilder().withContactIds(Set.of(AMY.getUuid()))
                .withPropertyIds(Set.of(PROPERTY_ALPHA.getUuid())).build();
        UNLINK_DESC_BOB_PROPERTY_BETA = new UnlinkDescriptorBuilder().withContactIds(Set.of(BOB.getUuid()))
                .withPropertyIds(Set.of(PROPERTY_BETA.getUuid())).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered contact list and selected contact in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Contact> expectedFilteredList = new ArrayList<>(actualModel.getFilteredContactList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getAddressBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredContactList());
    }

    /**
     * Updates {@code model}'s filtered list to show only the contact at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showContactAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredContactList().size());

        Contact contact = model.getFilteredContactList().get(targetIndex.getZeroBased());
        final String[] splitName = contact.getName().fullName.split("\\s+");

        // Create a FilterContactPredicate that filters by name keyword
        FilterContactPredicate predicate = new FilterContactPredicate(
                Optional.of(Arrays.asList(splitName[0])), // name keywords
                Optional.empty(), Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty()
        );
        model.updateFilteredContactList(predicate);
        assertEquals(1, model.getFilteredContactList().size());
    }

    /**
     * Updates {@code model}'s filtered list to show only the property at the given {@code targetIndex} in the
     * {@code model}'s property book.
     */
    public static void showPropertyAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPropertyList().size());

        Property property = model.getFilteredPropertyList().get(targetIndex.getZeroBased());
        final String[] splitAddress = property.getPropertyAddress().value.split("\\s+");

        PropertyMatchesFilterPredicate predicate = new PropertyMatchesFilterPredicate(
                splitAddress[0], null, null, null, null, null, null, null, null, null);
        model.updateFilteredPropertyList(predicate);
        assertEquals(1, model.getFilteredPropertyList().size());
    }

}
