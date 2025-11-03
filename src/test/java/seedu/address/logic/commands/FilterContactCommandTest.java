package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_CONTACTS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalContacts.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.PropertyBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.contact.FilterContactPredicate;

public class FilterContactCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new PropertyBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new PropertyBook(), new UserPrefs());

    @Test
    public void equals() {
        FilterContactPredicate firstPredicate =
                new FilterContactPredicate(Optional.of(Arrays.asList("first")),
                        Optional.empty(), Optional.empty(), Optional.empty(),
                        Optional.empty(), Optional.empty(), Optional.empty(),
                        Optional.empty());

        FilterContactPredicate secondPredicate =
                new FilterContactPredicate(Optional.of(Arrays.asList("second")),
                        Optional.empty(), Optional.empty(), Optional.empty(),
                        Optional.empty(), Optional.empty(), Optional.empty(),
                        Optional.empty());

        FilterContactCommand firstCommand = new FilterContactCommand(firstPredicate);
        FilterContactCommand secondCommand = new FilterContactCommand(secondPredicate);

        // same object -> returns true
        assertTrue(firstCommand.equals(firstCommand));

        // same values -> returns true
        FilterContactCommand firstCommandCopy = new FilterContactCommand(firstPredicate);
        assertTrue(firstCommand.equals(firstCommandCopy));

        // different types -> returns false
        assertFalse(firstCommand.equals(1));

        // null -> returns false
        assertFalse(firstCommand.equals(null));

        // different predicates -> returns false
        assertFalse(firstCommand.equals(secondCommand));
    }

    @Test
    public void execute_zeroFilters_noContactFound() {
        String expectedMessage = String.format(MESSAGE_CONTACTS_LISTED_OVERVIEW,
                model.getFilteredContactList().size(), 1, 7);
        FilterContactPredicate predicate = new FilterContactPredicate(
                Optional.empty(), Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty(), Optional.empty());
        FilterContactCommand command = new FilterContactCommand(predicate);
        expectedModel.updateFilteredContactList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_multipleFilters_multipleContactsFound() {
        FilterContactPredicate predicate = new FilterContactPredicate(
                Optional.of(Arrays.asList("Kurz", "Elle", "Kunz")),
                Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty());

        FilterContactCommand command = new FilterContactCommand(predicate);

        expectedModel.updateFilteredContactList(predicate);

        CommandResult result = command.execute(model);

        String expectedMessage = String.format(MESSAGE_CONTACTS_LISTED_OVERVIEW,
                expectedModel.getFilteredContactList().size(), 1, 3);

        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(expectedModel.getFilteredContactList(), model.getFilteredContactList());
    }

    @Test
    public void toStringMethod() {
        FilterContactPredicate predicate = new FilterContactPredicate(
                Optional.of(Arrays.asList("keyword")), Optional.empty(),
                Optional.empty(), Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty(), Optional.empty());

        FilterContactCommand command = new FilterContactCommand(predicate);
        String expected = FilterContactCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, command.toString());
    }
}
