package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditContactCommand.EditPersonDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.PropertyBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Uuid;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilderUtil;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditContactCommand.
 */
public class EditContactCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new PropertyBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Person originalPerson = model.getFilteredPersonList().get(0);
        Uuid targetUuid = originalPerson.getUuid();

        Person editedPerson = new PersonBuilderUtil(originalPerson)
                .withName("Bob Builder")
                .withPhone("91234567")
                .withEmail("bob@example.com")
                .build();

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(editedPerson).build();
        EditContactCommand command = new EditContactCommand(targetUuid, descriptor);

        String expectedMessage = String.format(EditContactCommand.MESSAGE_EDIT_PERSON_SUCCESS,
                Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new PropertyBook(model.getPropertyBook()), new UserPrefs());
        expectedModel.setPerson(originalPerson, editedPerson);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Person personToEdit = model.getFilteredPersonList().get(0);
        Uuid targetUuid = personToEdit.getUuid();

        Person editedPerson = new PersonBuilderUtil(personToEdit)
                .withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB)
                .withTags(VALID_TAG_HUSBAND)
                .build();

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder()
                .withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB)
                .withTags(VALID_TAG_HUSBAND)
                .build();

        EditContactCommand command = new EditContactCommand(targetUuid, descriptor);

        String expectedMessage = String.format(EditContactCommand.MESSAGE_EDIT_PERSON_SUCCESS,
                Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new PropertyBook(model.getPropertyBook()), new UserPrefs());
        expectedModel.setPerson(personToEdit, editedPerson);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        Person personToEdit = model.getFilteredPersonList().get(0);
        Uuid targetUuid = personToEdit.getUuid();

        EditContactCommand command = new EditContactCommand(targetUuid, new EditPersonDescriptor());
        String expectedMessage = String.format(EditContactCommand.MESSAGE_EDIT_PERSON_SUCCESS,
                Messages.format(personToEdit));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new PropertyBook(model.getPropertyBook()), new UserPrefs());

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidUuid_failure() {
        Uuid invalidUuid = new Uuid(999);
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditContactCommand command = new EditContactCommand(invalidUuid, descriptor);

        assertCommandFailure(command, model, EditContactCommand.MESSAGE_PERSON_NOT_FOUND);
    }

    @Test
    public void execute_duplicatePerson_failure() {
        Person firstPerson = model.getFilteredPersonList().get(0);
        Person secondPerson = model.getFilteredPersonList().get(1);
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(secondPerson).build();
        EditContactCommand command = new EditContactCommand(firstPerson.getUuid(), descriptor);

        assertCommandFailure(command, model, EditContactCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void equals() {
        Person firstPerson = model.getFilteredPersonList().get(0);
        Uuid targetUuid = firstPerson.getUuid();

        final EditContactCommand standardCommand = new EditContactCommand(targetUuid, DESC_AMY);
        EditPersonDescriptor copyDescriptor = new EditPersonDescriptor(DESC_AMY);
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
        Uuid otherUuid = model.getFilteredPersonList().get(1).getUuid();
        assertNotEquals(standardCommand, new EditContactCommand(otherUuid, DESC_AMY));

        // different descriptor -> false
        assertNotEquals(standardCommand, new EditContactCommand(targetUuid, DESC_BOB));
    }

    @Test
    public void toStringMethod() {
        Person firstPerson = model.getFilteredPersonList().get(0);
        Uuid targetUuid = firstPerson.getUuid();
        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        EditContactCommand command = new EditContactCommand(targetUuid, editPersonDescriptor);

        String expected = EditContactCommand.class.getCanonicalName() + "{targetUuid=" + targetUuid
                + ", editPersonDescriptor=" + editPersonDescriptor + "}";
        assertEquals(expected, command.toString());
    }

}
