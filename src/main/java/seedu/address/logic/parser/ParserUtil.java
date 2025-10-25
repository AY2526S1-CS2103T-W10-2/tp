package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.uuid.Uuid.StoredItem.CONTACT;
import static seedu.address.model.uuid.Uuid.StoredItem.PROPERTY;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.contact.BudgetMax;
import seedu.address.model.contact.BudgetMin;
import seedu.address.model.contact.ContactAddress;
import seedu.address.model.contact.ContactStatus;
import seedu.address.model.contact.Email;
import seedu.address.model.contact.Name;
import seedu.address.model.contact.Notes;
import seedu.address.model.contact.Phone;
import seedu.address.model.contact.Tag;
import seedu.address.model.property.Bathroom;
import seedu.address.model.property.Bedroom;
import seedu.address.model.property.FloorArea;
import seedu.address.model.property.Listing;
import seedu.address.model.property.Owner;
import seedu.address.model.property.Postal;
import seedu.address.model.property.Price;
import seedu.address.model.property.PropertyAddress;
import seedu.address.model.property.Status;
import seedu.address.model.property.Type;
import seedu.address.model.uuid.Uuid;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_INVALID_UUID = "UUID is not a valid format.";
    public static final String DEFAULT_BUDGET_MIN = "0";
    public static final String DEFAULT_BUDGET_MAX = String.valueOf(200_000_000_000L);

    /**
     * Returns true if the given string looks like an unrecognized prefix (e.g., "x/foo").
     */
    public static boolean looksLikePrefix(String s) {
        return s.trim().contains("/");
    }

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses {@code contactId} into a {@code Uuid} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified contactId is invalid (not non-zero unsigned integer).
     */
    public static Uuid parseContactId(String contactId) throws ParseException {
        requireNonNull(contactId);
        String trimmedContactId = contactId.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedContactId)) {
            throw new ParseException(MESSAGE_INVALID_UUID);
        }
        return new Uuid(Integer.parseInt(trimmedContactId), CONTACT);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Uuid>},
     * both representing a collection of contact ids, and returns it.
     */
    public static Set<Uuid> parseContactIds(Collection<String> contactIds) throws ParseException {
        requireNonNull(contactIds);
        final Set<Uuid> contactIdsSet = new HashSet<>();
        for (String contactId : contactIds) {
            contactIdsSet.add(parseContactId(contactId));
        }
        return contactIdsSet;
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        // remove extra spaces start/end of name and between name/surname
        String trimmedName = name.trim().replaceAll("\\s+", " ");
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Returns a non-null string value by replacing {@code null} inputs with {@code defaultValue}.
     * <p>
     * This method helps simplify null checks when parsing optional user inputs.
     *
     * @param value the input string, which may be {@code null}
     * @param defaultValue the fallback string to use if {@code value} is {@code null}
     * @return {@code value} if non-null, otherwise {@code defaultValue}
     */
    private static String sanitiseNull(String value, String defaultValue) {
        return value == null || value.isEmpty() ? defaultValue : value;
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static ContactAddress parseAddress(String address) throws ParseException {
        String trimmedAddress = sanitiseNull(address, "").trim();
        if (!ContactAddress.isValidAddress(trimmedAddress)) {
            throw new ParseException(ContactAddress.MESSAGE_CONSTRAINTS);
        }
        return new ContactAddress(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        String trimmedEmail = sanitiseNull(email, "").trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(String.format(Tag.MESSAGE_CONSTRAINTS, trimmedTag));
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            if (!tagName.isEmpty()) {
                tagSet.add(parseTag(tagName));
            }
        }
        return tagSet;
    }
    /**
     * Parses a {@code String budgetMin} into a {@code BudgetMin}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code budgetMin} is not a valid integer or violates constraints.
     */
    public static BudgetMin parseBudgetMin(String budgetMin) throws ParseException {
        String trimmedBudgetMin = sanitiseNull(budgetMin, DEFAULT_BUDGET_MIN).trim();

        if (!BudgetMin.isValidBudgetMin(trimmedBudgetMin)) {
            throw new ParseException(BudgetMin.MESSAGE_CONSTRAINTS);
        }
        return new BudgetMin(trimmedBudgetMin);
    }

    /**
     * Parses a {@code String budgetMax} into a {@code BudgetMax}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code budgetMax} is not a valid integer or violates constraints.
     */
    public static BudgetMax parseBudgetMax(String budgetMax) throws ParseException {
        String trimmedBudgetMax = sanitiseNull(budgetMax, DEFAULT_BUDGET_MAX).trim();

        if (!BudgetMax.isValidBudgetMax(trimmedBudgetMax)) {
            throw new ParseException(BudgetMax.MESSAGE_CONSTRAINTS);
        }
        return new BudgetMax(trimmedBudgetMax);
    }

    /**
     * Parses a {@code String notes} into a {@code Notes}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code notes} is invalid (e.g., null).
     */
    public static Notes parseNotes(String notes) throws ParseException {
        String trimmedNotes = sanitiseNull(notes, "").trim();
        return new Notes(trimmedNotes); //TODO add exception?
    }

    /**
     * Parses a {@code String status} into a {@code ContactStatus}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code status} is invalid (fails {@link ContactStatus#isValidStatus}).
     */
    public static ContactStatus parseContactStatus(String status) throws ParseException {
        String trimmedStatus = sanitiseNull(status, "").trim();
        if (!ContactStatus.isValidStatus(trimmedStatus)) {
            throw new ParseException(String.format(ContactStatus.MESSAGE_CONSTRAINTS, trimmedStatus));
        }
        return new ContactStatus(trimmedStatus);
    }
    // ================ Property parsing methods ================

    /**
     * Parses a {@code String address} into a property {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static PropertyAddress parsePropertyAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!PropertyAddress.isValidPropertyAddress(trimmedAddress)) {
            throw new ParseException(PropertyAddress.MESSAGE_CONSTRAINTS);
        }
        return new PropertyAddress(trimmedAddress);
    }

    /**
     * Parses {@code propertyId} into a {@code Uuid} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified propertyId is invalid (not non-zero unsigned integer).
     */
    public static Uuid parsePropertyId(String propertyId) throws ParseException {
        requireNonNull(propertyId);
        String trimmedpropertyId = propertyId.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedpropertyId)) {
            throw new ParseException(MESSAGE_INVALID_UUID);
        }
        return new Uuid(Integer.parseInt(trimmedpropertyId), PROPERTY);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Uuid>},
     * both representing a collection of property ids, and returns it.
     */
    public static Set<Uuid> parsePropertyIds(Collection<String> propertyIds) throws ParseException {
        requireNonNull(propertyIds);
        final Set<Uuid> propertyIdsSet = new HashSet<>();
        for (String propertyId : propertyIds) {
            propertyIdsSet.add(parsePropertyId(propertyId));
        }
        return propertyIdsSet;
    }

    /**
     * Parses a {@code String postal} into a {@code Postal}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code postal} is invalid.
     */
    public static Postal parsePostal(String postal) throws ParseException {
        requireNonNull(postal);
        String trimmedPostal = postal.trim();
        if (!Postal.isValidPostal(trimmedPostal)) {
            throw new ParseException(Postal.MESSAGE_CONSTRAINTS);
        }
        return new Postal(trimmedPostal);
    }

    /**
     * Parses a {@code String price} into a {@code Price}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code price} is invalid.
     */
    public static Price parsePrice(String price) throws ParseException {
        requireNonNull(price);
        String trimmedPrice = price.trim();
        if (!Price.isValidPrice(trimmedPrice)) {
            throw new ParseException(Price.MESSAGE_CONSTRAINTS);
        }
        return new Price(trimmedPrice);
    }

    /**
     * Parses a {@code String type} into a {@code Type}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code type} is invalid.
     */
    public static Type parseType(String type) throws ParseException {
        requireNonNull(type);
        String trimmedType = type.trim();
        if (!Type.isValidType(trimmedType)) {
            throw new ParseException(String.format(Type.MESSAGE_CONSTRAINTS, trimmedType));
        }
        return new Type(trimmedType);
    }

    /**
     * Parses a {@code String status} into a {@code Status}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code status} is invalid.
     */
    public static Status parseStatus(String status) throws ParseException {
        requireNonNull(status);
        String trimmedStatus = status.trim();
        if (!Status.isValidStatus(trimmedStatus)) {
            throw new ParseException(String.format(Status.MESSAGE_CONSTRAINTS, trimmedStatus));
        }
        return new Status(trimmedStatus);
    }

    /**
     * Parses a {@code String bedroom} into a {@code Bedroom}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code bedroom} is invalid.
     */
    public static Bedroom parseBedroom(String bedroom) throws ParseException {
        requireNonNull(bedroom);
        String trimmedBedroom = bedroom.trim();
        if (!Bedroom.isValidBedroom(trimmedBedroom)) {
            throw new ParseException(Bedroom.MESSAGE_CONSTRAINTS);
        }
        return new Bedroom(trimmedBedroom);
    }

    /**
     * Parses a {@code String bathroom} into a {@code Bathroom}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code bathroom} is invalid.
     */
    public static Bathroom parseBathroom(String bathroom) throws ParseException {
        requireNonNull(bathroom);
        String trimmedBathroom = bathroom.trim();
        if (!Bathroom.isValidBathroom(trimmedBathroom)) {
            throw new ParseException(Bathroom.MESSAGE_CONSTRAINTS);
        }
        return new Bathroom(trimmedBathroom);
    }

    /**
     * Parses a {@code String floorArea} into a {@code FloorArea}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code floorArea} is invalid.
     */
    public static FloorArea parseFloorArea(String floorArea) throws ParseException {
        requireNonNull(floorArea);
        String trimmedFloorArea = floorArea.trim();
        if (!FloorArea.isValidFloorArea(trimmedFloorArea)) {
            throw new ParseException(FloorArea.MESSAGE_CONSTRAINTS);
        }
        return new FloorArea(trimmedFloorArea);
    }

    /**
     * Parses a {@code String listing} into a {@code Listing}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code listing} is invalid.
     */
    public static Listing parseListing(String listing) throws ParseException {
        requireNonNull(listing);
        String trimmedListing = listing.trim();
        if (!Listing.isValidListing(trimmedListing)) {
            throw new ParseException(String.format(Listing.MESSAGE_CONSTRAINTS, trimmedListing));
        }
        return new Listing(trimmedListing);
    }

    /**
     * Parses a {@code String owner} into a {@code Owner}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code owner} is invalid.
     */
    public static Owner parseOwner(String owner) throws ParseException {
        requireNonNull(owner);
        String trimmedOwner = owner.trim();
        if (!Owner.isValidOwner(trimmedOwner)) {
            throw new ParseException(Owner.MESSAGE_CONSTRAINTS);
        }
        return new Owner(trimmedOwner);
    }
}
