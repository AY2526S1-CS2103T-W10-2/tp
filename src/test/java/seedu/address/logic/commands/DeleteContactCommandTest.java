package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.DeleteContactCommand.MESSAGE_PERSON_NOT_FOUND;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.PropertyBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Uuid;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteContactCommand}.
 */
public class DeleteContactCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new PropertyBook(), new UserPrefs());

    @Test
    public void execute_validUuidUnfilteredList_success() {
        Person personToDelete = model.getFilteredPersonList().get(0);
        Uuid uuidToDelete = personToDelete.getUuid();
        DeleteContactCommand deleteContactCommand = new DeleteContactCommand(uuidToDelete);

        String expectedMessage = String.format(DeleteContactCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        Model expectedModel = new ModelManager(model.getAddressBook(),
                new PropertyBook(model.getPropertyBook()), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deleteContactCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidUuidUnfilteredList_throwsCommandException() {
        Uuid invalidUuid = new Uuid(999999); // Non-existent UUID
        DeleteContactCommand deleteContactCommand = new DeleteContactCommand(invalidUuid);

        assertCommandFailure(deleteContactCommand, model,
                String.format(MESSAGE_PERSON_NOT_FOUND, invalidUuid.value));
    }

    @Test
    public void equals() {
        Uuid firstUuid = model.getFilteredPersonList().get(0).getUuid();
        Uuid secondUuid = model.getFilteredPersonList().get(1).getUuid();

        DeleteContactCommand deleteFirstCommand = new DeleteContactCommand(firstUuid);
        DeleteContactCommand deleteSecondCommand = new DeleteContactCommand(secondUuid);

        // same object -> returns true
        assertEquals(deleteFirstCommand, deleteFirstCommand);

        // same values -> returns true
        DeleteContactCommand deleteFirstCommandCopy = new DeleteContactCommand(firstUuid);
        assertEquals(deleteFirstCommand, deleteFirstCommandCopy);

        // different types -> returns false
        assertNotEquals(1, deleteFirstCommand);

        // null -> returns false
        assertNotEquals(null, deleteFirstCommand);

        // different person -> returns false
        assertNotEquals(deleteFirstCommand, deleteSecondCommand);
    }

    @Test
    public void toStringMethod() {
        Uuid targetUuid = model.getFilteredPersonList().get(0).getUuid();
        DeleteContactCommand deleteContactCommand = new DeleteContactCommand(targetUuid);
        String expected = DeleteContactCommand.class.getCanonicalName() + "{targetUuid=" + targetUuid + "}";
        assertEquals(expected, deleteContactCommand.toString());
    }
}
