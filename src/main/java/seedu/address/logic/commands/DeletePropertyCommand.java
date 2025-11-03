package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.property.Property;
import seedu.address.model.uuid.Uuid;

/**
 * Deletes a property identified using its unique ID from the property list.
 */
public class DeletePropertyCommand extends Command {

    public static final String COMMAND_WORD = "deleteproperty";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the property identified by their unique UUID.\n"
            + "Parameters: UUID (must be a positive integer)e\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_PROPERTY_SUCCESS = "Deleted property: %1$s";

    private final Uuid targetPropertyId;

    public DeletePropertyCommand(Uuid targetPropertyId) {
        this.targetPropertyId = requireNonNull(targetPropertyId);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Property> lastShownList = model.getFilteredPropertyList();

        Property propertyToDelete = lastShownList.stream()
                .filter(property -> property.getUuid().equals(targetPropertyId))
                .findFirst()
                .orElseThrow(() -> new CommandException(Messages.MESSAGE_INVALID_PROPERTY_DISPLAYED_ID));

        model.deleteProperty(propertyToDelete);

        showPropertiesView();

        return new CommandResult(String.format(MESSAGE_DELETE_PROPERTY_SUCCESS, Messages.format(propertyToDelete)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DeletePropertyCommand)) {
            return false;
        }

        DeletePropertyCommand otherDeletePropertyCommand = (DeletePropertyCommand) other;
        return targetPropertyId.equals(otherDeletePropertyCommand.targetPropertyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(targetPropertyId);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetPropertyId", targetPropertyId)
                .toString();
    }
}
