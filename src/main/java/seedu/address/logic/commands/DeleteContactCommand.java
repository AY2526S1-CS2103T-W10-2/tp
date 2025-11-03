package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.UnlinkCommand.UnlinkDescriptor;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.contact.Contact;
import seedu.address.model.property.Property;
import seedu.address.model.uuid.Uuid;



/**
 * Deletes a contact identified by their unique ID from the address book.
 */
public class DeleteContactCommand extends Command {

    public static final String COMMAND_WORD = "deletecontact";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the contact identified by their unique UUID.\n"
            + "Parameters: UUID\n"
            + "Example: " + COMMAND_WORD + " 12";

    public static final String MESSAGE_DELETE_CONTACT_SUCCESS = "Deleted Contact: %1$s";
    public static final String MESSAGE_CONTACT_NOT_FOUND = "No contact found with ID: %s";

    private static final Logger logger = LogsCenter.getLogger(DeleteContactCommand.class);

    private final Uuid targetUuid;

    /**
     * Constructs a {@code DeleteContactCommand} with the specified UUID.
     *
     * @param targetUuid The UUID of the contact to delete.
     */
    public DeleteContactCommand(Uuid targetUuid) {
        requireNonNull(targetUuid);
        this.targetUuid = targetUuid;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        assert targetUuid != null : "targetUuid should not be null";

        List<Contact> allContacts = model.getAddressBook().getContactList();
        assert allContacts != null : "Contact list should not be null";

        Optional<Contact> contactToDelete = allContacts.stream()
                .filter(p -> p.getUuid().equals(targetUuid))
                .findFirst();

        if (contactToDelete.isEmpty()) {
            logger.log(Level.WARNING, "Failed to delete contact. UUID not found: {0}", targetUuid.getValue());
            throw new CommandException(String.format(MESSAGE_CONTACT_NOT_FOUND, targetUuid.getValue()));
        }

        unlinkAllLinkedProperties(model);

        model.deleteContact(contactToDelete.get());
        logger.log(Level.INFO, "Successfully deleted contact: {0}", contactToDelete.get().getName());

        showContactsView();

        return new CommandResult(String.format(
                MESSAGE_DELETE_CONTACT_SUCCESS, Messages.format(contactToDelete.get())));
    }

    private void unlinkAllLinkedProperties(Model model) {
        model.getPropertyBook().getPropertyList().stream()
                .filter(property -> property.getBuyingContactIds().contains(targetUuid)
                        || property.getSellingContactIds().contains(targetUuid))
                .forEach(property -> unlinkProperty(property, model));
    }

    private void unlinkProperty(Property property, Model model) {
        UnlinkDescriptor unlinkDescriptor = new UnlinkDescriptor();
        unlinkDescriptor.setPropertyIds(Set.of(property.getUuid()));
        unlinkDescriptor.setContactIds(Set.of(targetUuid));
        try {
            new UnlinkCommand(unlinkDescriptor).execute(model);
        } catch (CommandException exception) {
            assert false; //Should not happen
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteContactCommand)) {
            return false;
        }

        DeleteContactCommand otherDeleteContactCommand = (DeleteContactCommand) other;
        return targetUuid.equals(otherDeleteContactCommand.targetUuid);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetUuid", targetUuid)
                .toString();
    }
}
