package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_BATHROOM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_BEDROOM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_FLOOR_AREA;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_OWNER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_POSTAL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_TYPE;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.contact.Contact;
import seedu.address.model.property.Property;
import seedu.address.model.uuid.Uuid;

/**
 * Adds a property to the property list.
 */
public class AddPropertyCommand extends Command {

    public static final String COMMAND_WORD = "addproperty";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a property to the property list.\n"
            + "Parameters: "
            + PREFIX_PROPERTY_ADDRESS + "ADDRESS "
            + PREFIX_PROPERTY_POSTAL + "POSTAL "
            + PREFIX_PROPERTY_PRICE + "PRICE "
            + PREFIX_PROPERTY_TYPE + "TYPE "
            + PREFIX_PROPERTY_STATUS + "STATUS "
            + PREFIX_PROPERTY_BEDROOM + "BEDROOM "
            + PREFIX_PROPERTY_BATHROOM + "BATHROOM "
            + PREFIX_PROPERTY_FLOOR_AREA + "FLOOR_AREA "
            + PREFIX_PROPERTY_OWNER + "OWNER\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PROPERTY_ADDRESS + "123 Orchard Rd "
            + PREFIX_PROPERTY_POSTAL + "238888 "
            + PREFIX_PROPERTY_TYPE + "condo "
            + PREFIX_PROPERTY_BEDROOM + "3 "
            + PREFIX_PROPERTY_BATHROOM + "2 "
            + PREFIX_PROPERTY_FLOOR_AREA + "1023 "
            + PREFIX_PROPERTY_PRICE + "1950000 "
            + PREFIX_PROPERTY_STATUS + "unavailable "
            + PREFIX_PROPERTY_OWNER + "1";

    public static final String MESSAGE_SUCCESS = "New property added: %1$s";
    public static final String MESSAGE_DUPLICATE_PROPERTY = "This property already exists in the property list";
    public static final String MESSAGE_OWNER_NOT_FOUND =
            "Owner contact ID must match an existing contact (received: %s).";

    private final Property toAdd;

    /**
     * Creates an AddPropertyCommand to add the specified {@code Property}
     */
    public AddPropertyCommand(Property property) {
        requireNonNull(property);
        toAdd = property;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Updating UUID of property
        Uuid uuid = model.getPropertyBook().generateNextUuid();
        Property propertyWithUuid = toAdd.duplicateWithNewUuid(uuid);

        ensureOwnerExists(model, propertyWithUuid);

        if (model.hasProperty(propertyWithUuid)) {
            throw new CommandException(MESSAGE_DUPLICATE_PROPERTY);
        }

        model.addProperty(propertyWithUuid);

        showPropertiesView();

        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(propertyWithUuid)));
    }

    private void ensureOwnerExists(Model model, Property property) throws CommandException {
        String ownerId = property.getOwner().value;
        int ownerUuid;
        try {
            ownerUuid = Integer.parseInt(ownerId);
        } catch (NumberFormatException e) {
            throw new CommandException(String.format(MESSAGE_OWNER_NOT_FOUND, ownerId));
        }

        boolean ownerExists = model.getAddressBook().getContactList().stream()
                .map(Contact::getUuid)
                .filter(uuid -> uuid != null)
                .anyMatch(uuid -> uuid.getValue() == ownerUuid);

        if (!ownerExists) {
            throw new CommandException(String.format(MESSAGE_OWNER_NOT_FOUND, ownerId));
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddPropertyCommand)) {
            return false;
        }

        AddPropertyCommand otherAddPropertyCommand = (AddPropertyCommand) other;
        return toAdd.equals(otherAddPropertyCommand.toAdd);
    }

    @Override
    public int hashCode() {
        return toAdd.hashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
