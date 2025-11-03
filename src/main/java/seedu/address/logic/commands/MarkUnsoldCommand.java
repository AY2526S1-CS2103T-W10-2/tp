package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_ID;

import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
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
            + "Parameters: "
            + PREFIX_PROPERTY_ID + "UUID...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PROPERTY_ID + "7 "
            + PREFIX_PROPERTY_ID + "33";

    public static final String MESSAGE_MARK_UNSOLD_SUCCESS = "Marked the properties with these IDs as unsold: %s";
    public static final String MESSAGE_PROPERTY_ERROR_UNSOLD_COMMAND =
                            "The properties with the following IDs do not exist or were already marked as unsold:\n"
                             + "%s\n"
                             + "Command has been aborted.";

    private static final Logger logger = LogsCenter.getLogger(MarkUnsoldCommand.class);

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
        assert propertyIds != null : "Property IDs to mark unsold should not be null";
        assert model != null : "Model should not be null";

        logger.log(Level.INFO, "Executing MarkUnsoldCommand for properties: {0}", propertyIds);

        String invalidIdsMessage = getInvalidPropertyIdsMessage(model, propertyIds,
                    MESSAGE_PROPERTY_ERROR_UNSOLD_COMMAND, "available");
        if (invalidIdsMessage != null) {
            throw new CommandException(invalidIdsMessage);
        }

        StringBuilder affectedIdsBuilder = new StringBuilder();
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
                    new Status("available"),
                    property.getType(),
                    property.getOwner(),
                    property.getBuyingContactIds(),
                    property.getSellingContactIds()
            );
            model.setProperty(property, updated);

            logger.log(Level.INFO, "Marked property ID {0} as unsold", id.getValue());

            if (!affectedIdsBuilder.isEmpty()) {
                affectedIdsBuilder.append(", ");
            }
            affectedIdsBuilder.append(id.getValue());
        }

        showPropertiesView();

        logger.log(Level.INFO, "MarkUnsoldCommand successfully completed");
        return new CommandResult(String.format(MESSAGE_MARK_UNSOLD_SUCCESS, affectedIdsBuilder.toString()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof MarkUnsoldCommand
                && propertyIds.equals(((MarkUnsoldCommand) other).propertyIds));
    }

    /**
     * Returns an error message listing all property IDs that could not be found in the model or
     * are already marked
     * @param model The model containing property data.
     * @param propertyIds The set of property IDs to validate.
     * @return The full error message listing missing property IDs, or {@code null} if all IDs are valid.
     */
    public static String getInvalidPropertyIdsMessage(Model model, Set<Uuid> propertyIds, String errorCommand,
                                                      String invalidStatus) {
        Set<Uuid> invalidIds = propertyIds.stream()
                .filter(id -> model.getPropertyById(id) == null)
                .collect(java.util.stream.Collectors.toSet());

        Set<Uuid> validIds = propertyIds.stream()
                .filter(id -> !invalidIds.contains(id))
                .collect(java.util.stream.Collectors.toSet());

        // Collect all valid IDs that have the invalid status
        Set<Uuid> allInvalidStatusIds = validIds.stream()
                .filter(id -> {
                    Property p = model.getPropertyById(id);
                    return p != null && p.getStatus().toString().equalsIgnoreCase(invalidStatus);
                })
                .collect(java.util.stream.Collectors.toSet());

        if (invalidIds.isEmpty() && allInvalidStatusIds.isEmpty()) {
            return null;
        }

        // Combine all invalid IDs
        Set<Uuid> allProblematicIds = new java.util.HashSet<>();
        allProblematicIds.addAll(invalidIds);
        allProblematicIds.addAll(allInvalidStatusIds);

        String idList = allProblematicIds.stream()
                .map(id -> String.valueOf(id.getValue()))
                .reduce((a, b) -> a + ", " + b)
                .orElse("");

        return String.format(errorCommand, idList);
    }
}
