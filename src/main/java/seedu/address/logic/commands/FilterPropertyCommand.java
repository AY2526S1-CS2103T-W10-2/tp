package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.property.Property;
import seedu.address.model.property.predicates.PropertyMatchesFilterPredicate;

/**
 * Filters properties using various optional attributes with pagination.
 */
public class FilterPropertyCommand extends Command {

    public static final String COMMAND_WORD = "filterproperty";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Filters properties using optional fields.\n"
            + "Available tags:\n"
            + "address/ type/ bedroom/ bathroom/ price/ status/ owner/ limit/ offset/\n"
            + "Example: " + COMMAND_WORD + " bedroom/2 bathroom/3 price/500000";

    public static final String MESSAGE_INVALID_LIMIT = "Error: Invalid limit";
    public static final String MESSAGE_INVALID_OFFSET = "Error: Invalid offset";
    public static final String MESSAGE_DUPLICATE_TAGS = "Error: Duplicate tags";

    private final PropertyMatchesFilterPredicate predicate;
    private final int limit;
    private final int offset;

    /**
     * Creates an FilterPropertyCommand to filter {@code Property} with given predicate.
     */
    public FilterPropertyCommand(PropertyMatchesFilterPredicate predicate, int limit, int offset) {
        this.predicate = predicate;
        this.limit = limit;
        this.offset = offset;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (limit < 1) {
            throw new CommandException(MESSAGE_INVALID_LIMIT);
        }
        if (offset < 0) {
            throw new CommandException(MESSAGE_INVALID_OFFSET);
        }

        List<Property> allMatches = model.getFilteredPropertyList().stream()
                .filter(predicate)
                .toList();

        int total = allMatches.size();
        int start = Math.min(offset, total);
        int endExclusive = Math.min(offset + limit, total);
        List<Property> page = allMatches.subList(start, endExclusive);

        model.updateFilteredPropertyList(page::contains);

        // Build “X properties matched (showing i–j)”
        int from = total == 0 ? 0 : start + 1;
        int to = total == 0 ? 0 : endExclusive;
        String msg = String.format("%d properties matched (showing %d–%d)", total, from, to);
        return new CommandResult(msg);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof FilterPropertyCommand
                && predicate.equals(((FilterPropertyCommand) other).predicate)
                && limit == ((FilterPropertyCommand) other).limit
                && offset == ((FilterPropertyCommand) other).offset);
    }
}
