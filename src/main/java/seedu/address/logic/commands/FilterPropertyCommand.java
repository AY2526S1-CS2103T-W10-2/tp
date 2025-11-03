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

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.property.Property;
import seedu.address.model.property.predicates.PropertyMatchesFilterPredicate;
import seedu.address.model.uuid.Uuid;

/**
 * Filters properties using various optional attributes with pagination.
 */
public class FilterPropertyCommand extends Command {

    public static final String COMMAND_WORD = "filterproperty";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filters all properties in the property book "
            + "based on the given fields. All prefixes are optional.\n"
            + "Parameters: "
            + "[" + PREFIX_PROPERTY_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_PROPERTY_POSTAL + "POSTAL] "
            + "[" + PREFIX_PROPERTY_TYPE + "TYPE] "
            + "[" + PREFIX_PROPERTY_BEDROOM + "BEDROOM] "
            + "[" + PREFIX_PROPERTY_BATHROOM + "BATHROOM] "
            + "[" + PREFIX_PROPERTY_FLOOR_AREA + "FLOORAREA] "
            + "[" + PREFIX_PROPERTY_STATUS + "STATUS] "
            + "[" + PREFIX_PROPERTY_PRICE + "PRICE] "
            + "[" + PREFIX_PROPERTY_OWNER + "CONTACT_ID]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PROPERTY_POSTAL + "123000 "
            + PREFIX_PROPERTY_BEDROOM + "2 "
            + PREFIX_PROPERTY_BATHROOM + "3 "
            + PREFIX_PROPERTY_PRICE + "500000";

    private final PropertyMatchesFilterPredicate predicate;

    /**
     * Creates an FilterPropertyCommand to filter {@code Property} with given predicate.
     */
    public FilterPropertyCommand(PropertyMatchesFilterPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Set<Uuid> allMatches = model.getFilteredPropertyList().stream()
                .filter(predicate)
                .toList()
                .stream()
                .map(Property::getUuid)
                .collect(Collectors.toSet());

        int total = allMatches.size();

        model.updateFilteredPropertyList(p -> allMatches.contains(p.getUuid()));

        // Build “X properties matched”
        String msg = String.format("%d properties matched", total);

        showPropertiesView();

        return new CommandResult(msg);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof FilterPropertyCommand
                && predicate.equals(((FilterPropertyCommand) other).predicate));
    }
}
