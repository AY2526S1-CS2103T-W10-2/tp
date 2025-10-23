package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_CONTACTS_LISTED_OVERVIEW;

import java.util.List;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.Model;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.FilterContactPredicate;
import seedu.address.ui.MainWindow;

/**
 * Filters and lists all contacts in the address book that match the given {@link FilterContactPredicate}.
 * The filtering is flexible and can be based on multiple attributes such as name, phone,
 * email, address, tags, budget range, notes, or status.
 */
public class FilterContactCommand extends Command {

    public static final String COMMAND_WORD = "filtercontact";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filters all contacts in the address book "
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
            + "[s/STATUS_KEYWORDS] "
            + "[limit/LIMIT] "
            + "[offset/OFFSET]\n"
            + "Example: " + COMMAND_WORD + " n/Alice e/alice@example.com limit/10 offset/20";

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

        // Build output message (e.g., “12 properties matched (showing 6–10)”)
        int from = total == 0 ? 0 : start + 1;
        int to = total == 0 ? 0 : endExclusive;
        String msg = String.format(MESSAGE_CONTACTS_LISTED_OVERVIEW, Math.min(limit, total - offset),
                                                                    from, to);

        //Toggle from properties list to contact list
        if (MainWindow.getInstance() != null) {
            MainWindow.getInstance().showContactsView();
        }

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
