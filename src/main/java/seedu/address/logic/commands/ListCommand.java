package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CONTACTS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PROPERTIES;

import seedu.address.model.Model;
import seedu.address.ui.MainWindow;

/**
 * Lists all contacts in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all contacts";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredContactList(PREDICATE_SHOW_ALL_CONTACTS); // list all contacts
        model.updateFilteredPropertyList(PREDICATE_SHOW_ALL_PROPERTIES); // list all properties

        if (MainWindow.getInstance() != null) {
            MainWindow.getInstance().showContactsView();
        }

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
