package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BUDGET_MAX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BUDGET_MIN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.contact.BudgetMax;
import seedu.address.model.contact.BudgetMin;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.ContactAddress;
import seedu.address.model.contact.ContactStatus;
import seedu.address.model.contact.Email;
import seedu.address.model.contact.Name;
import seedu.address.model.contact.Notes;
import seedu.address.model.contact.Phone;
import seedu.address.model.contact.Tag;
import seedu.address.model.uuid.Uuid;

/**
 * Edits the details of an existing contact in the address book.
 */
public class EditContactCommand extends Command {

    public static final String COMMAND_WORD = "editcontact";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the contact identified "
            + "by their UUID. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: UUID "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_BUDGET_MIN + "BUDGET_MIN] "
            + "[" + PREFIX_BUDGET_MAX + "BUDGET_MAX] "
            + "[" + PREFIX_NOTES + "NOTES] "
            + "[" + PREFIX_STATUS + "STATUS] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 123 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_CONTACT_SUCCESS = "Edited Contact: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_CONTACT_NOT_FOUND = "No contact found with the given UUID.";
    public static final String MESSAGE_DUPLICATE_CONTACT = "This contact already exists in the address book.";

    private final Uuid targetUuid;
    private final EditContactDescriptor editContactDescriptor;

    /**
     * @param targetUuid of the contact to edit
     * @param editContactDescriptor details to edit the contact with
     */
    public EditContactCommand(Uuid targetUuid, EditContactDescriptor editContactDescriptor) {
        requireNonNull(targetUuid);
        requireNonNull(editContactDescriptor);

        this.targetUuid = targetUuid;
        this.editContactDescriptor = new EditContactDescriptor(editContactDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Contact> allContacts = model.getAddressBook().getContactList();

        Contact contactToEdit = allContacts.stream()
                .filter(p -> p.getUuid().equals(targetUuid))
                .findFirst()
                .orElseThrow(() -> new CommandException(MESSAGE_CONTACT_NOT_FOUND));

        Contact editedContact = createEditedContact(contactToEdit, editContactDescriptor);

        if (!contactToEdit.equals(editedContact) && model.hasContact(editedContact)) {
            throw new CommandException(MESSAGE_DUPLICATE_CONTACT);
        }

        model.setContact(contactToEdit, editedContact);

        showContactsView();

        return new CommandResult(String.format(MESSAGE_EDIT_CONTACT_SUCCESS, Messages.format(editedContact)));
    }

    /**
     * Creates and returns a {@code Contact} with the details of {@code contactToEdit}
     * edited with {@code editContactDescriptor}.
     */
    private static Contact createEditedContact(Contact contactToEdit, EditContactDescriptor editContactDescriptor) {
        assert contactToEdit != null;

        Uuid updatedUuid = contactToEdit.getUuid();
        Name updatedName = editContactDescriptor.getName().orElse(contactToEdit.getName());
        Phone updatedPhone = editContactDescriptor.getPhone().orElse(contactToEdit.getPhone());
        Email updatedEmail = editContactDescriptor.getEmail().orElse(contactToEdit.getEmail());
        ContactAddress updatedAddress = editContactDescriptor.getAddress().orElse(contactToEdit.getAddress());
        BudgetMin updatedBudgetMin = editContactDescriptor.getBudgetMin().orElse(contactToEdit.getBudgetMin());
        BudgetMax updatedBudgetMax = editContactDescriptor.getBudgetMax().orElse(contactToEdit.getBudgetMax());
        Notes updatedNotes = editContactDescriptor.getNotes().orElse(contactToEdit.getNotes());
        ContactStatus updatedStatus = editContactDescriptor.getStatus().orElse(contactToEdit.getStatus());
        Set<Tag> updatedTags = editContactDescriptor.getTags().orElse(contactToEdit.getTags());
        Set<Uuid> updatedBuyingPropertyIds = contactToEdit.getBuyingPropertyIds();
        Set<Uuid> updatedSellingPropertyIds = contactToEdit.getSellingPropertyIds();

        return new Contact(updatedUuid, updatedName, updatedPhone, updatedEmail, updatedAddress,
                          updatedTags, updatedBudgetMin, updatedBudgetMax, updatedNotes, updatedStatus,
                updatedBuyingPropertyIds, updatedSellingPropertyIds);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditContactCommand)) {
            return false;
        }

        EditContactCommand otherEditContactCommand = (EditContactCommand) other;
        return targetUuid.equals(otherEditContactCommand.targetUuid)
                && editContactDescriptor.equals(otherEditContactCommand.editContactDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetUuid", targetUuid)
                .add("editContactDescriptor", editContactDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the contact with. Each non-empty field value will replace the
     * corresponding field value of the contact.
     */
    public static class EditContactDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private ContactAddress address;
        private BudgetMin budgetMin;
        private BudgetMax budgetMax;
        private Notes notes;
        private ContactStatus status;
        private Set<Tag> tags;

        public EditContactDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditContactDescriptor(EditContactDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
            setBudgetMin(toCopy.budgetMin);
            setBudgetMax(toCopy.budgetMax);
            setNotes(toCopy.notes);
            setStatus(toCopy.status);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, address, tags, budgetMin, budgetMax, notes, status);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setAddress(ContactAddress address) {
            this.address = address;
        }

        public Optional<ContactAddress> getAddress() {
            return Optional.ofNullable(address);
        }

        public void setBudgetMin(BudgetMin budgetMin) {
            this.budgetMin = budgetMin;
        }

        public Optional<BudgetMin> getBudgetMin() {
            return Optional.ofNullable(budgetMin);
        }

        public void setBudgetMax(BudgetMax budgetMax) {
            this.budgetMax = budgetMax;
        }

        public Optional<BudgetMax> getBudgetMax() {
            return Optional.ofNullable(budgetMax);
        }

        public void setNotes(Notes notes) {
            this.notes = notes;
        }

        public Optional<Notes> getNotes() {
            return Optional.ofNullable(notes);
        }

        public void setStatus(ContactStatus status) {
            this.status = status;
        }

        public Optional<ContactStatus> getStatus() {
            return Optional.ofNullable(status);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditContactDescriptor)) {
                return false;
            }

            EditContactDescriptor otherEditContactDescriptor = (EditContactDescriptor) other;
            return Objects.equals(name, otherEditContactDescriptor.name)
                    && Objects.equals(phone, otherEditContactDescriptor.phone)
                    && Objects.equals(email, otherEditContactDescriptor.email)
                    && Objects.equals(address, otherEditContactDescriptor.address)
                    && Objects.equals(budgetMin, otherEditContactDescriptor.budgetMin)
                    && Objects.equals(budgetMax, otherEditContactDescriptor.budgetMax)
                    && Objects.equals(notes, otherEditContactDescriptor.notes)
                    && Objects.equals(status, otherEditContactDescriptor.status)
                    && Objects.equals(tags, otherEditContactDescriptor.tags);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("phone", phone)
                    .add("email", email)
                    .add("address", address)
                    .add("budgetMin", budgetMin)
                    .add("budgetMax", budgetMax)
                    .add("notes", notes)
                    .add("status", status)
                    .add("tags", tags)
                    .toString();
        }
    }
}
