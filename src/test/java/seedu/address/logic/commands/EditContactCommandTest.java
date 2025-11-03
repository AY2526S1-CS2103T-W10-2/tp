package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalContacts.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditContactCommand.EditContactDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.PropertyBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.contact.Contact;
import seedu.address.model.uuid.Uuid;
import seedu.address.testutil.ContactBuilderUtil;
import seedu.address.testutil.EditContactDescriptorBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditContactCommand.
 */
public class EditContactCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new PropertyBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Contact originalContact = model.getFilteredContactList().get(0);
        Uuid targetUuid = originalContact.getUuid();

        Contact editedContact = new ContactBuilderUtil(originalContact)
                .withName("Bob Builder")
                .withPhone("91234567")
                .withEmail("bob@example.com")
                .build();

        EditContactCommand.EditContactDescriptor descriptor = new EditContactDescriptorBuilder(editedContact).build();
        EditContactCommand command = new EditContactCommand(targetUuid, descriptor);

        String expectedMessage = String.format(EditContactCommand.MESSAGE_EDIT_CONTACT_SUCCESS,
                Messages.format(editedContact));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new PropertyBook(model.getPropertyBook()), new UserPrefs());
        expectedModel.setContact(originalContact, editedContact);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Contact contactToEdit = model.getFilteredContactList().get(0);
        Uuid targetUuid = contactToEdit.getUuid();

        Contact editedContact = new ContactBuilderUtil(contactToEdit)
                .withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB)
                .build();

        EditContactCommand.EditContactDescriptor descriptor = new EditContactDescriptorBuilder()
                .withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB)
                .build();

        EditContactCommand command = new EditContactCommand(targetUuid, descriptor);

        String expectedMessage = String.format(EditContactCommand.MESSAGE_EDIT_CONTACT_SUCCESS,
                Messages.format(editedContact));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new PropertyBook(model.getPropertyBook()), new UserPrefs());
        expectedModel.setContact(contactToEdit, editedContact);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        Contact contactToEdit = model.getFilteredContactList().get(0);
        Uuid targetUuid = contactToEdit.getUuid();

        EditContactCommand command = new EditContactCommand(targetUuid, new EditContactCommand.EditContactDescriptor());
        String expectedMessage = String.format(EditContactCommand.MESSAGE_EDIT_CONTACT_SUCCESS,
                Messages.format(contactToEdit));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new PropertyBook(model.getPropertyBook()), new UserPrefs());

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidUuid_failure() {
        Uuid invalidUuid = new Uuid(999, Uuid.StoredItem.CONTACT);
        EditContactCommand.EditContactDescriptor descriptor =
                new EditContactDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditContactCommand command = new EditContactCommand(invalidUuid, descriptor);

        assertCommandFailure(command, model, EditContactCommand.MESSAGE_CONTACT_NOT_FOUND);
    }

    @Test
    public void execute_duplicateContact_failure() {
        Contact firstContact = model.getFilteredContactList().get(0);
        Contact secondContact = model.getFilteredContactList().get(1);
        EditContactDescriptor descriptor = new EditContactDescriptorBuilder(secondContact).build();
        EditContactCommand command = new EditContactCommand(firstContact.getUuid(), descriptor);

        assertCommandFailure(command, model, EditContactCommand.MESSAGE_DUPLICATE_CONTACT);
    }

    @Test
    public void equals() {
        Contact firstContact = model.getFilteredContactList().get(0);
        Uuid targetUuid = firstContact.getUuid();

        final EditContactCommand standardCommand = new EditContactCommand(targetUuid, DESC_AMY);
        EditContactCommand.EditContactDescriptor copyDescriptor = new EditContactDescriptor(DESC_AMY);
        EditContactCommand commandWithSameValues = new EditContactCommand(targetUuid, copyDescriptor);

        // same values -> true
        assertEquals(standardCommand, commandWithSameValues);

        // same object -> true
        assertEquals(standardCommand, standardCommand);

        // null -> false
        assertNotEquals(null, standardCommand);

        // different type -> false
        assertNotEquals(new ClearCommand(), standardCommand);

        // different UUID -> false
        Uuid otherUuid = model.getFilteredContactList().get(1).getUuid();
        assertNotEquals(standardCommand, new EditContactCommand(otherUuid, DESC_AMY));

        // different descriptor -> false
        assertNotEquals(standardCommand, new EditContactCommand(targetUuid, DESC_BOB));
    }

    @Test
    public void toStringMethod() {
        Contact firstContact = model.getFilteredContactList().get(0);
        Uuid targetUuid = firstContact.getUuid();
        EditContactCommand.EditContactDescriptor editContactDescriptor = new EditContactCommand.EditContactDescriptor();
        EditContactCommand command = new EditContactCommand(targetUuid, editContactDescriptor);

        String expected = EditContactCommand.class.getCanonicalName() + "{targetUuid=" + targetUuid
                + ", editContactDescriptor=" + editContactDescriptor + "}";
        assertEquals(expected, command.toString());
    }

}
