package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_CONTACT_DISPLAYED_ID;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PROPERTY_DISPLAYED_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LINK_RELATIONSHIP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_ID;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.contact.Contact;
import seedu.address.model.property.Property;
import seedu.address.model.uuid.Uuid;

/**
 * Links properties to contacts.
 */
public class LinkCommand extends Command {

    public static final String COMMAND_WORD = "link";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Links properties to contacts.\n"
            + "Parameters: "
            + PREFIX_PROPERTY_ID + "PROPERTY_ID... "
            + PREFIX_LINK_RELATIONSHIP + "RELATIONSHIP (must be either 'buyer' or 'seller') "
            + PREFIX_CONTACT_ID + "CONTACT_ID...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PROPERTY_ID + "2 "
            + PREFIX_LINK_RELATIONSHIP + "buyer "
            + PREFIX_CONTACT_ID + "3 "
            + PREFIX_CONTACT_ID + "5";

    public static final String MESSAGE_LINK_BUYER_SUCCESS =
            "Linked Property IDs: [%1$s] with Contact IDs: [%2$s] as buyer";
    public static final String MESSAGE_LINK_SELLER_SUCCESS =
            "Linked Property IDs: [%1$s] with Contact IDs: [%2$s] as seller";

    private static final Logger logger = Logger.getLogger(LinkCommand.class.getName());

    private final LinkDescriptor linkDescriptor;

    /**
     * Creates a LinkCommand to link the specified {@code Property} to the specified {@code Contact}
     */
    public LinkCommand(LinkDescriptor linkDescriptor) {
        requireNonNull(linkDescriptor);
        this.linkDescriptor = linkDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Contact> lastShownContactList = model.getFilteredContactList();
        List<Property> lastShownPropertyList = model.getFilteredPropertyList();

        linkDescriptor.throwExceptionIfLinked(lastShownContactList, lastShownPropertyList);

        List<Contact> targetContacts = linkDescriptor.getContactsInList(lastShownContactList);
        List<Property> targetProperties = linkDescriptor.getPropertiesInList(lastShownPropertyList);

        List<Contact> updatedContacts = linkDescriptor.getUpdatedContacts(lastShownContactList);
        List<Property> updatedProperties = linkDescriptor.getUpdatedProperties(lastShownPropertyList);

        Stream.iterate(0, x -> x < targetContacts.size(), x -> x + 1)
                .forEach(i -> model.setContact(targetContacts.get(i), updatedContacts.get(i)));
        Stream.iterate(0, x -> x < targetProperties.size(), x -> x + 1)
                .forEach(i -> model.setProperty(targetProperties.get(i), updatedProperties.get(i)));

        logger.log(Level.FINER, "Successfully linked contacts to properties as {0}",
                linkDescriptor.getRelationship());

        switch (linkDescriptor.getRelationship()) {
        case "buyer":
            return new CommandResult(String.format(MESSAGE_LINK_BUYER_SUCCESS,
                    Uuid.getGuiSetDisplayAsString(linkDescriptor.getPropertyIds()),
                    Uuid.getGuiSetDisplayAsString(linkDescriptor.getContactIds())));
        case "seller":
            return new CommandResult(String.format(MESSAGE_LINK_SELLER_SUCCESS,
                    Uuid.getGuiSetDisplayAsString(linkDescriptor.getPropertyIds()),
                    Uuid.getGuiSetDisplayAsString(linkDescriptor.getContactIds())));
        default:
            logger.log(Level.WARNING, "Linking failed due to invalid relationship");
            throw new CommandException(Messages.MESSAGE_INVALID_RELATIONSHIP);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof LinkCommand)) {
            return false;
        }

        LinkCommand otherLinkCommand = (LinkCommand) other;
        return linkDescriptor.equals(otherLinkCommand.linkDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("linkDescriptor", linkDescriptor)
                .toString();
    }

    /**
     * Stores Ids and relationship to link a property to a contact.
     */
    public static class LinkDescriptor {
        private Set<Uuid> contactIds;
        private Set<Uuid> propertyIds;
        private String relationship;

        public LinkDescriptor() {}

        /**
         * Copy constructor.
         */
        public LinkDescriptor(LinkDescriptor toCopy) {
            setContactIds(toCopy.contactIds);
            setPropertyIds(toCopy.propertyIds);
            setRelationship(toCopy.relationship);
        }

        public void setContactIds(Set<Uuid> contactIds) {
            this.contactIds = contactIds;
        }

        public Set<Uuid> getContactIds() {
            return contactIds;
        }

        public void setPropertyIds(Set<Uuid> propertyIds) {
            this.propertyIds = propertyIds;
        }

        public Set<Uuid> getPropertyIds() {
            return propertyIds;
        }

        public void setRelationship(String relationship) {
            this.relationship = relationship;
        }

        public String getRelationship() {
            return relationship;
        }

        /**
         * Returns the {@code List<Contact>} in the list with all matching contactIds.
         *
         * @throws CommandException if any contact is not found in the list.
         */
        public List<Contact> getContactsInList(List<Contact> contactList) throws CommandException {
            assert (contactList != null);
            List<Contact> contactsList = new ArrayList<>(contactList).stream()
                    .filter(contact -> contactIds.contains(contact.getUuid()))
                    .collect(Collectors.toList());
            if (contactsList.size() != contactIds.size()) {
                throw new CommandException(MESSAGE_INVALID_CONTACT_DISPLAYED_ID);
            }
            return contactsList;
        }

        /**
         * Returns the {@code List<Property>} in the list with the matching propertyId.
         *
         * @throws CommandException if any property is not found in the list.
         */
        public List<Property> getPropertiesInList(List<Property> propertyList) throws CommandException {
            assert (propertyList != null);
            List<Property> propertiesList = new ArrayList<>(propertyList).stream()
                    .filter(property -> propertyIds.contains(property.getUuid()))
                    .collect(Collectors.toList());
            if (propertiesList.size() != propertyIds.size()) {
                throw new CommandException(MESSAGE_INVALID_PROPERTY_DISPLAYED_ID);
            }
            return propertiesList;
        }

        /**
         * Returns an edited {@code List<Contact>} with the properties linked.
         *
         * @throws CommandException if the relationship is invalid.
         */
        public List<Contact> getUpdatedContacts(List<Contact> contactList) throws CommandException {
            assert (relationship != null);
            List<Contact> contactsToEdit = new ArrayList<>(getContactsInList(contactList));
            switch (relationship) {
            case "buyer":
                return contactsToEdit.stream()
                        .map(contactToEdit -> contactToEdit
                        .duplicateWithNewBuyingPropertyIds(
                        Stream.concat(contactToEdit.getBuyingPropertyIds().stream(), propertyIds.stream())
                        .distinct()
                        .collect(Collectors.toSet())))
                        .collect(Collectors.toList());
            case "seller":
                return contactsToEdit.stream()
                        .map(contactToEdit -> contactToEdit
                        .duplicateWithNewSellingPropertyIds(
                        Stream.concat(contactToEdit.getSellingPropertyIds().stream(), propertyIds.stream())
                        .distinct()
                        .collect(Collectors.toSet())))
                        .collect(Collectors.toList());
            default:
                throw new CommandException(Messages.MESSAGE_INVALID_RELATIONSHIP);
            }
        }

        /**
         * Returns an edited {@code List<Property>} with the contacts linked.
         *
         * @throws CommandException if the relationship is invalid.
         */
        public List<Property> getUpdatedProperties(List<Property> propertyList)
                throws CommandException {
            assert (relationship != null);
            List<Property> propertiesToEdit = new ArrayList<>(getPropertiesInList(propertyList));
            switch (relationship) {
            case "buyer":
                return propertiesToEdit.stream()
                        .map(propertyToEdit -> propertyToEdit
                        .duplicateWithNewBuyingContactIds(
                        Stream.concat(propertyToEdit.getBuyingContactIds().stream(), contactIds.stream())
                        .distinct()
                        .collect(Collectors.toSet())))
                        .collect(Collectors.toList());
            case "seller":
                return propertiesToEdit.stream()
                        .map(propertyToEdit -> propertyToEdit
                        .duplicateWithNewSellingContactIds(
                        Stream.concat(propertyToEdit.getSellingContactIds().stream(), contactIds.stream())
                        .distinct()
                        .collect(Collectors.toSet())))
                        .collect(Collectors.toList());
            default:
                throw new CommandException(Messages.MESSAGE_INVALID_RELATIONSHIP);
            }
        }

        /**
         * @throws CommandException if any of the related contacts and properties are already linked.
         */
        public void throwExceptionIfLinked(List<Contact> contactList, List<Property> propertyList)
                throws CommandException {
            List<Contact> filteredContacts = new ArrayList<>(getContactsInList(contactList));
            List<Property> filteredProperties = new ArrayList<>(getPropertiesInList(propertyList));
            boolean hasAnyContactLinkedAsBuyer = filteredContacts.stream()
                    .anyMatch(contact -> !Collections.disjoint(propertyIds, contact.getBuyingPropertyIds()));
            boolean hasAnyContactLinkedAsSeller = filteredContacts.stream()
                    .anyMatch(contact -> !Collections.disjoint(propertyIds, contact.getSellingPropertyIds()));
            boolean hasAnyPropertyLinkedByBuyer = filteredProperties.stream()
                    .anyMatch(property -> !Collections.disjoint(contactIds, property.getBuyingContactIds()));
            boolean hasAnyPropertyLinkedBySeller = filteredProperties.stream()
                    .anyMatch(property -> !Collections.disjoint(contactIds, property.getSellingContactIds()));
            if (hasAnyContactLinkedAsBuyer || hasAnyPropertyLinkedByBuyer) {
                throw new CommandException(Messages.MESSAGE_LINKING_ALREADY_LINKED_BUYER);
            }
            if (hasAnyContactLinkedAsSeller || hasAnyPropertyLinkedBySeller) {
                throw new CommandException(Messages.MESSAGE_LINKING_ALREADY_LINKED_SELLER);
            }
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            if (!(other instanceof LinkDescriptor)) {
                return false;
            }

            LinkDescriptor otherLinkDescriptor = (LinkDescriptor) other;

            return contactIds.equals(otherLinkDescriptor.contactIds)
                    && propertyIds.equals(otherLinkDescriptor.propertyIds)
                    && relationship.equals(otherLinkDescriptor.relationship);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("contactIds", contactIds)
                    .add("relationship", relationship)
                    .add("propertyIds", propertyIds)
                    .toString();
        }
    }
}
