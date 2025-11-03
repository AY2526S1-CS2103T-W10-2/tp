package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.property.predicates.AssociatedWithContactPredicate;
import seedu.address.model.uuid.Uuid;
import seedu.address.ui.MainWindow;

/**
 * Finds and lists all properties associated with a specified contact.
 *
 * Show all properties linked to contact (as buyer, seller, etc.)
 * when property-contact association model is implemented.
 */
public class ShowPropertiesCommand extends Command {

    public static final String COMMAND_WORD = "showproperties";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Shows all properties associated with the specified contact.\n"
            + "Parameters: CONTACT_ID (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 123";

    public static final String MESSAGE_SUCCESS = "Listed %2$d propert%3$s associated to contact ID: %1$s";

    public static final String MESSAGE_NO_PROPERTIES =
            "No properties found associated to contact ID: %1$s\n"
                    + "Possible reasons:\n"
                    + "  • The contact exists but is not linked to any properties yet\n"
                    + "  • The contact ID doesn't exist (use 'filtercontact' to verify)\n"
                    + "Tip: Use 'addproperty ... o/%1$s' to add a property for this contact.";

    private final Uuid contactUuid;

    /**
     * Creates a ShowPropertiesCommand to find all properties associated to the specified contact.
     *
     * @param contactUuid The UUID of the contact to search for.
     */
    public ShowPropertiesCommand(Uuid contactUuid) {
        requireNonNull(contactUuid);
        this.contactUuid = contactUuid;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Filter properties where owner matches the contact UUID
        AssociatedWithContactPredicate predicate = new AssociatedWithContactPredicate(contactUuid);
        model.updateFilteredPropertyList(predicate);

        //Toggle from contacts list to property list
        if (MainWindow.getInstance() != null) {
            MainWindow.getInstance().showPropertiesView();
        }

        int numPropertiesFound = model.getFilteredPropertyList().size();

        if (numPropertiesFound == 0) {
            return new CommandResult(String.format(MESSAGE_NO_PROPERTIES, contactUuid.getValue()));
        }

        // Grammatically correct: "1 property" vs "2 properties"
        String propertyWord = numPropertiesFound == 1 ? "y" : "ies";
        return new CommandResult(
                String.format(MESSAGE_SUCCESS, contactUuid.getValue(), numPropertiesFound, propertyWord));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ShowPropertiesCommand)) {
            return false;
        }

        ShowPropertiesCommand otherCommand = (ShowPropertiesCommand) other;
        return contactUuid.equals(otherCommand.contactUuid);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("contactUuid", contactUuid)
                .toString();
    }
}
