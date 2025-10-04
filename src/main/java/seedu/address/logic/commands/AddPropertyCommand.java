package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_BATHROOM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_BEDROOM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_FLOOR_AREA;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_LISTING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_POSTAL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_TYPE;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.property.Property;

/**
 * Adds a property to the property list.
 */
public class AddPropertyCommand extends Command {

    public static final String COMMAND_WORD = "addproperty";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a property to the property list. "
            + "Parameters: "
            + PREFIX_PROPERTY_ADDRESS + "ADDRESS "
            + PREFIX_PROPERTY_POSTAL + "POSTAL "
            + PREFIX_PROPERTY_PRICE + "PRICE "
            + PREFIX_PROPERTY_TYPE + "TYPE "
            + PREFIX_PROPERTY_STATUS + "STATUS "
            + PREFIX_PROPERTY_BEDROOM + "BEDROOM "
            + PREFIX_PROPERTY_BATHROOM + "BATHROOM "
            + PREFIX_PROPERTY_FLOOR_AREA + "FLOOR_AREA "
            + "[" + PREFIX_PROPERTY_LISTING + "LISTING]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PROPERTY_ADDRESS + "123 Main St "
            + PREFIX_PROPERTY_POSTAL + "123456 "
            + PREFIX_PROPERTY_PRICE + "500000 "
            + PREFIX_PROPERTY_TYPE + "HDB "
            + PREFIX_PROPERTY_STATUS + "Available "
            + PREFIX_PROPERTY_BEDROOM + "3 "
            + PREFIX_PROPERTY_BATHROOM + "2 "
            + PREFIX_PROPERTY_FLOOR_AREA + "100";

    public static final String MESSAGE_SUCCESS = "New property added: %1$s";
    public static final String MESSAGE_DUPLICATE_PROPERTY = "This property already exists in the property list";

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

        if (model.hasProperty(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PROPERTY);
        }

        model.addProperty(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd.toString()));
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
