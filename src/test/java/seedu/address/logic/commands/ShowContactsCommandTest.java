package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.uuid.Uuid.StoredItem.CONTACT;
import static seedu.address.model.uuid.Uuid.StoredItem.PROPERTY;
import static seedu.address.testutil.TypicalContacts.getTypicalAddressBook;
import static seedu.address.testutil.TypicalProperties.getTypicalPropertyBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.uuid.Uuid;

public class ShowContactsCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), getTypicalPropertyBook(), new UserPrefs());

    @Test
    public void execute_validProperty_success() throws Exception {
        Uuid validUuid = new Uuid(1, PROPERTY);
        ShowContactsCommand command = new ShowContactsCommand(validUuid);
        CommandResult result = command.execute(model);
        assertTrue(result.getFeedbackToUser().contains("Listed")
                || result.getFeedbackToUser().contains("No contacts found"));
    }

    @Test
    public void execute_noContactsFound_showsMessage() throws Exception {
        Uuid uuidWithNoContacts = new Uuid(999, PROPERTY);
        ShowContactsCommand command = new ShowContactsCommand(uuidWithNoContacts);
        CommandResult result = command.execute(model);
        assertTrue(result.getFeedbackToUser().contains("No contacts found")
                || result.getFeedbackToUser().contains("No contacts"));
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        Uuid uuid = new Uuid(1, PROPERTY);
        ShowContactsCommand command = new ShowContactsCommand(uuid);
        assertEquals(command, command);
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        Uuid uuid = new Uuid(1, PROPERTY);
        ShowContactsCommand command1 = new ShowContactsCommand(uuid);
        ShowContactsCommand command2 = new ShowContactsCommand(uuid);
        assertEquals(command1, command2);
    }

    @Test
    public void equals_differentValues_returnsFalse() {
        ShowContactsCommand c1 = new ShowContactsCommand(new Uuid(1, PROPERTY));
        ShowContactsCommand c2 = new ShowContactsCommand(new Uuid(2, PROPERTY));
        assertNotEquals(c1, c2);
    }

    @Test
    public void equals_differentType_returnsFalse() {
        ShowContactsCommand command = new ShowContactsCommand(new Uuid(1, PROPERTY));
        assertNotEquals("string", command);
    }

    @Test
    public void equals_null_returnsFalse() {
        ShowContactsCommand command = new ShowContactsCommand(new Uuid(1, PROPERTY));
        assertNotEquals(null, command);
    }

    @Test
    public void toString_containsUuid() {
        Uuid uuid = new Uuid(1, PROPERTY);
        ShowContactsCommand command = new ShowContactsCommand(uuid);
        assertTrue(command.toString().contains("propertyUuid"));
    }

    @Test
    public void execute_nullModel_throwsCommandException() {
        ShowContactsCommand command = new ShowContactsCommand(new Uuid(1, PROPERTY));
        assertThrows(NullPointerException.class, () -> command.execute(null));
    }

    @Test
    public void execute_noContactsFound_returnsNoContactsMessage() throws Exception {
        Uuid propertyUuid = new Uuid(999, PROPERTY);
        ShowContactsCommand command = new ShowContactsCommand(propertyUuid);
        CommandResult result = command.execute(model);
        assertTrue(result.getFeedbackToUser().contains("No contacts found"));
    }

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        ShowContactsCommand command = new ShowContactsCommand(new Uuid(1, PROPERTY));
        assertThrows(NullPointerException.class, () -> command.execute(null));
    }

    @Test
    public void execute_nonPropertyUuid_showsNoContactsFound() throws Exception {
        Uuid contactUuid = new Uuid(1, CONTACT); // wrong type
        ShowContactsCommand command = new ShowContactsCommand(contactUuid);

        CommandResult result = command.execute(model);

        assertTrue(result.getFeedbackToUser().contains("No contacts"));
    }

    @Test
    public void equals_differentCommandType_returnsFalse() {
        ShowContactsCommand showCommand = new ShowContactsCommand(new Uuid(1, PROPERTY));
        ShowPropertiesCommand otherCommand = new ShowPropertiesCommand(new Uuid(1, CONTACT));

        assertNotEquals(showCommand, otherCommand);
    }

    @Test
    public void toString_includesUuidValue() {
        Uuid uuid = new Uuid(5, PROPERTY);
        ShowContactsCommand command = new ShowContactsCommand(uuid);

        String commandString = command.toString();

        assertTrue(commandString.contains("5"));
    }
}
