package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Set;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.property.Property;
import seedu.address.model.property.Status;
import seedu.address.model.uuid.Uuid;

/**
 * This command finds properties by their IDs and marks them as unsold.
 * If a property with the given ID does not exist, the command throws a {@link CommandException}.
 * <p>
 *  Usage example:
 *  <pre>
 *  unsold p/7 p/33
 *  </pre>
 */
public class MarkUnsoldCommand extends Command {

    public static final String COMMAND_WORD = "unsold";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks one or more properties as unsold.\n"
            + "Parameters: p/PROPERTY_ID [p/PROPERTY_ID]...\n"
            + "Example: " + COMMAND_WORD + " p/7 p/33";

    public static final String MESSAGE_MARK_UNSOLD_SUCCESS = "Marked %d property(ies) as unsold.";
    public static final String MESSAGE_PROPERTY_NOT_FOUND = "%s not found.";

    private final Set<Uuid> propertyIds;

    /**
     * Constructs a {@code MarkUnsoldCommand} with the specified list of property IDs.
     *
     * @param propertyIds The IDs of the properties to mark as unsold. Must not be null.
     */
    public MarkUnsoldCommand(Set<Uuid> propertyIds) {
        requireNonNull(propertyIds);
        this.propertyIds = propertyIds;
    }

    /**
     * Executes the command to mark the specified properties as unsold.
     *
     * @param model The model which contains the property book and data manipulation methods.
     * @return A {@link CommandResult} indicating the outcome of the command.
     * @throws CommandException If any of the property IDs do not exist in the model.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Validate all IDs first
        for (Uuid id : propertyIds) {
            if (model.getPropertyById(id) == null) {
                throw new CommandException(String.format(MESSAGE_PROPERTY_NOT_FOUND, id));
            }
        }

        int count = 0;
        for (Uuid id : propertyIds) {
            Property property = model.getPropertyById(id);
            Property updated = new Property(
                    property.getUuid(),
                    property.getPropertyAddress(),
                    property.getBathroom(),
                    property.getBedroom(),
                    property.getFloorArea(),
                    property.getListing(),
                    property.getPostal(),
                    property.getPrice(),
                    new Status("unsold"),
                    property.getType(),
                    property.getOwner(),
                    property.getBuyingPersonIds(),
                    property.getSellingPersonIds()
            );
            model.setProperty(property, updated);
            count++;
        }
        return new CommandResult(String.format(MESSAGE_MARK_UNSOLD_SUCCESS, count));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof MarkUnsoldCommand
                && propertyIds.equals(((MarkUnsoldCommand) other).propertyIds));
    }
}
