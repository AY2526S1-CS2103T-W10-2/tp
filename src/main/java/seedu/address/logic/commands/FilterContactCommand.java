package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_CONTACTS_LISTED_OVERVIEW;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BUDGET_MAX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BUDGET_MIN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LIMIT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OFFSET;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;

import java.util.List;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.Model;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.FilterContactPredicate;

/**
 * Filters and lists all contacts in the address book that match the given {@link FilterContactPredicate}.
 * The filtering is flexible and can be based on multiple attributes such as name, phone,
 * email, address, tags, budget range, notes, or status.
 */
public class FilterContactCommand extends Command {

    public static final String COMMAND_WORD = "filtercontact";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filters all contacts in the address book "
            + "based on the given fields. All prefixes are optional. \n"
            + "Parameters: "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_BUDGET_MIN + "MIN_BUDGET] "
            + "[" + PREFIX_BUDGET_MAX + "MAX_BUDGET] "
            + "[" + PREFIX_NOTES + "NOTES] "
            + "[" + PREFIX_STATUS + "STATUS] "
            + "[" + PREFIX_LIMIT + "LIMIT] "
            + "[" + PREFIX_OFFSET + "OFFSET]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    private final FilterContactPredicate predicate;

    /**
     * Creates a {@code FilterContactCommand} with the specified filtering predicate.
     *
     * @param predicate The predicate used to test whether a {@code Contact} should be included
     *                  in the filtered results.
     */
    public FilterContactCommand(FilterContactPredicate predicate) {
        this.predicate = predicate;
    }

    /**
     * Executes the filter operation by updating the model's filtered contact list
     * with the given predicate.
     *
     * @param model The {@link Model} in which the filtering is applied.
     * @return A {@link CommandResult} containing a summary message of the number of contacts listed.
     */
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        // filter all matches normally
        List<Contact> allMatches = model.getFilteredContactList().stream()
                .filter(predicate)
                .toList();
        // Set offset and limit
        int total = allMatches.size();
        int offset = predicate.getOffset().orElse(0);
        int limit = predicate.getLimit().orElse(total);
        int start = Math.min(offset, total);
        int endExclusive = Math.min(offset + limit, total);

        // Update filtered list to display only this page
        List<Contact> page = allMatches.subList(start, endExclusive);
        model.updateFilteredContactList(page::contains);

        // Build output message (e.g., “12 properties matched”)
        String msg = String.format(MESSAGE_CONTACTS_LISTED_OVERVIEW, page.size());

        showContactsView();

        return new CommandResult(msg);
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
