package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.property.Property;
import seedu.address.model.property.Status;

/**
 * This command finds properties by their IDs and marks them as sold.
 * If a property with the given ID does not exist, the command throws a {@link CommandException}.
 * <p>
 *  Usage example:
 *  <pre>
 *  sold p/14 p/27
 *  </pre>
 */
public class MarkSoldCommand extends Command {

    public static final String COMMAND_WORD = "sold";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks one or more properties as sold.\n"
            + "Parameters: p/PROPERTY_ID [p/PROPERTY_ID]...\n"
            + "Example: " + COMMAND_WORD + " p/14 p/27";

    public static final String MESSAGE_MARK_SOLD_SUCCESS = "Marked %d property(ies) as sold.";
    public static final String MESSAGE_PROPERTY_NOT_FOUND = "Property with ID %s not found.";

    private final List<String> propertyIds;

    /**
     * Constructs a {@code MarkSoldCommand} with the specified list of property IDs.
     *
     * @param propertyIds The IDs of the properties to mark as sold. Must not be null.
     */
    public MarkSoldCommand(List<String> propertyIds) {
        requireNonNull(propertyIds);
        this.propertyIds = propertyIds;
    }

    /**
     * Executes the command to mark the specified properties as sold.
     *
     * @param model The model which contains the property book and data manipulation methods.
     * @return A {@link CommandResult} indicating the outcome of the command.
     * @throws CommandException If any of the property IDs do not exist in the model.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        int count = 0;
        for (String id : propertyIds) {
            Property property = model.getPropertyById(id);
            if (property == null) {
                throw new CommandException(String.format(MESSAGE_PROPERTY_NOT_FOUND, id));
            }
            Property updated = new Property(
                    property.getPropertyAddress(),
                    property.getBathroom(),
                    property.getBedroom(),
                    property.getFloorArea(),
                    property.getListing(),
                    property.getPostal(),
                    property.getPrice(),
                    new Status("sold"),
                    property.getType(),
                    property.getOwner()
            );
            model.setProperty(property, updated);
            count++;
        }
        return new CommandResult(String.format(MESSAGE_MARK_SOLD_SUCCESS, count));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof MarkSoldCommand
                && propertyIds.equals(((MarkSoldCommand) other).propertyIds));
    }
}
