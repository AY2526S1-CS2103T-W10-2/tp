package seedu.address.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.contact.Contact;
import seedu.address.model.property.Property;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX = "A contact index provided is invalid";
    public static final String MESSAGE_CONTACTS_LISTED_OVERVIEW = "%d contacts listed! (showing %d–%d)";
    public static final String MESSAGE_DUPLICATE_FIELDS =
            "Multiple values specified for the following single-valued field(s): ";
    public static final String MESSAGE_INVALID_PROPERTY_DISPLAYED_INDEX = "A property index provided is invalid";
    public static final String MESSAGE_INVALID_RELATIONSHIP = "The relationship provided is invalid";
    public static final String MESSAGE_INVALID_PROPERTY_DISPLAYED_ID = "The property's id provided is invalid";

    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields = Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Formats the {@code contact} for display to the user.
     */
    public static String format(Contact contact) {
        final StringBuilder builder = new StringBuilder();
        builder.append("Id: ")
                .append(contact.getUuid().getValue())
                .append(", Name: ")
                .append(contact.getName())
                .append(", Phone: ")
                .append(contact.getPhone())
                .append(", Email: ")
                .append(contact.getEmail())
                .append(", Address: ")
                .append(contact.getAddress())
                .append(", Min Budget: ")
                .append(contact.getBudgetMin())
                .append(", Max Budget: ")
                .append(contact.getBudgetMax())
                .append(", Tags: [");
        contact.getTags().forEach(tag -> builder.append(tag.toString() + ", "));
        if (!contact.getTags().isEmpty()) {
            builder.delete(builder.length() - 2, builder.length()); //remove extra comma and space
        }
        builder.append("], Notes: ")
                .append(contact.getNotes())
                .append(", Status: ")
                .append(contact.getStatus());
        builder.append(", Buying Property Ids: [");
        contact.getBuyingPropertyIds().forEach(id -> builder.append(id.toString() + ", "));
        if (!contact.getBuyingPropertyIds().isEmpty()) {
            builder.delete(builder.length() - 2, builder.length()); //remove extra comma and space
        }
        builder.append("], Selling Property Ids: [");
        contact.getSellingPropertyIds().forEach(id -> builder.append(id.toString() + ", "));
        if (!contact.getSellingPropertyIds().isEmpty()) {
            builder.delete(builder.length() - 2, builder.length()); //remove extra comma and space
        }
        builder.append("]");
        return builder.toString();
    }

    /**
     * Formats the {@code property} for display to the user.
     */
    public static String format(Property property) {
        final StringBuilder builder = new StringBuilder();
        builder.append(property.getPropertyAddress())
                .append("; Id: ")
                .append(property.getUuid())
                .append("; Bathrooms: ")
                .append(property.getBathroom())
                .append("; Bedroom: ")
                .append(property.getBedroom())
                .append("; Floor Area: ")
                .append(property.getFloorArea());

        if (property.getListing() != null) {
            builder.append("; Listing: ")
                    .append(property.getListing());
        }

        builder.append("; Postal: ")
                .append(property.getPostal())
                .append("; Price: ")
                .append(property.getPrice())
                .append("; Status: ")
                .append(property.getStatus())
                .append("; Type: ")
                .append(property.getType())
                .append("; Owner: ")
                .append(property.getOwner());
        builder.append("; Buying Contact Ids: [");
        property.getBuyingContactIds().forEach(builder::append);
        builder.append("]; Selling Contact Ids: [");
        property.getSellingContactIds().forEach(builder::append);
        builder.append("]");
        return builder.toString();
    }
}
