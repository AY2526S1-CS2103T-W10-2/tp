package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BUDGET_MAX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BUDGET_MIN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;

import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.contact.Contact;
import seedu.address.model.uuid.Uuid;


/**
 * Adds a contact to the address book.
 */
public class AddContactCommand extends Command {

    public static final String COMMAND_WORD = "addcontact";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a contact to the address book.\n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_BUDGET_MIN + "AMOUNT] "
            + "[" + PREFIX_BUDGET_MAX + "AMOUNT] "
            + "[" + PREFIX_NOTES + "NOTES] "
            + "[" + PREFIX_STATUS + "STATUS]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 ";

    public static final String MESSAGE_SUCCESS = "New contact added:\n%1$s";
    public static final String MESSAGE_DUPLICATE_CONTACT = "This contact already exists in the address book";
    private static final Logger logger = LogsCenter.getLogger(AddContactCommand.class);
    private final Contact toAdd;

    /**
     * Creates an AddContactCommand to add the specified {@code Contact}
     */
    public AddContactCommand(Contact contact) {
        requireNonNull(contact);
        toAdd = contact;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        assert toAdd != null : "Contact to add should not be null";

        // Updating UUID of contact
        Uuid uuid = model.getAddressBook().generateNextUuid();
        assert uuid != null : "Generated UUID should not be null";
        Contact contactWithUuid = toAdd.duplicateWithNewUuid(uuid);

        if (model.hasContact(contactWithUuid)) {
            logger.log(Level.WARNING, "Attempted to add duplicate contact: {0}", contactWithUuid.getName());
            throw new CommandException(MESSAGE_DUPLICATE_CONTACT);
        }

        model.addContact(contactWithUuid);
        logger.log(Level.INFO, "Successfully added new contact: {0}", contactWithUuid.getName());
        showContactsView();

        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(contactWithUuid)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddContactCommand)) {
            return false;
        }

        AddContactCommand otherAddContactCommand = (AddContactCommand) other;
        return toAdd.equals(otherAddContactCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
