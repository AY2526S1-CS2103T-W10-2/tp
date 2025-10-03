package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.FilterContactPredicate;

/**
 * Filters and lists all persons in the address book that match the given {@link FilterContactPredicate}.
 * The filtering is flexible and can be based on multiple attributes such as name, phone,
 * email, address, tags, budget range, notes, or status.
 */
public class FilterContactCommand extends Command {

    public static final String COMMAND_WORD = "filtercontact";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filters all persons in the address book "
            + "based on the given fields. All prefixes are optional.\n"
            + "Parameters: "
            + "[n/NAME_KEYWORDS] "
            + "[p/PHONE_KEYWORDS] "
            + "[e/EMAIL_KEYWORDS] "
            + "[a/ADDRESS_KEYWORDS] "
            + "[t/TAG_KEYWORDS] "
            + "[min/MIN_BUDGET] "
            + "[max/MAX_BUDGET] "
            + "[notes/NOTES_KEYWORDS] "
            + "[s/STATUS_KEYWORDS]\n"
            + "Example: " + COMMAND_WORD + " n/Alice e/alice@example.com";

    private final FilterContactPredicate predicate;

    /**
     * Creates a {@code FilterContactCommand} with the specified filtering predicate.
     *
     * @param predicate The predicate used to test whether a {@code Person} should be included
     *                  in the filtered results.
     */
    public FilterContactCommand(FilterContactPredicate predicate) {
        this.predicate = predicate;
    }

    /**
     * Executes the filter operation by updating the model's filtered person list
     * with the given predicate.
     *
     * @param model The {@link Model} in which the filtering is applied.
     * @return A {@link CommandResult} containing a summary message of the number of persons listed.
     */
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    /**
     * Checks whether another object is equal to this {@code FilterContactCommand}.
     * Equality is defined based on the equality of their {@link FilterContactPredicate}.
     *
     * @param other The object to compare with.
     * @return True if both objects are the same or have equivalent predicates; false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FilterContactCommand)) {
            return false;
        }

        FilterContactCommand otherFilterContactCommand = (FilterContactCommand) other;
        return predicate.equals(otherFilterContactCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
