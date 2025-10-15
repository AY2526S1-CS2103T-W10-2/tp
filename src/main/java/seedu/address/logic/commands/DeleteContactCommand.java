package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Uuid;

/**
 * Deletes a person identified by their unique ID from the address book.
 */
public class DeleteContactCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by their unique UUID.\n"
            + "Parameters: c/UUID (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " c/12";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";
    public static final String MESSAGE_PERSON_NOT_FOUND = "No person found with UUID: %s";

    private final Uuid targetUuid;

    /**
     * Constructs a {@code DeleteContactCommand} with the specified UUID.
     *
     * @param targetUuid The UUID of the person to delete.
     */
    public DeleteContactCommand(Uuid targetUuid) {
        requireNonNull(targetUuid);
        this.targetUuid = targetUuid;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Find person by UUID
        Optional<Person> personToDelete = model.getFilteredPersonList().stream()
                .filter(p -> p.getUuid().equals(targetUuid))
                .findFirst();

        if (personToDelete.isEmpty()) {
            throw new CommandException(String.format(MESSAGE_PERSON_NOT_FOUND, targetUuid.value));
        }

        model.deletePerson(personToDelete.get());
        return new CommandResult(String.format(
                MESSAGE_DELETE_PERSON_SUCCESS, Messages.format(personToDelete.get())));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteContactCommand)) {
            return false;
        }

        DeleteContactCommand otherDeleteContactCommand = (DeleteContactCommand) other;
        return targetUuid.equals(otherDeleteContactCommand.targetUuid);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetUuid", targetUuid)
                .toString();
    }
}
