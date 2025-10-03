package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.BudgetMax;
import seedu.address.model.person.BudgetMin;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Notes;
import seedu.address.model.person.PersonAddress;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Status;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

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
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
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
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static PersonAddress parseAddress(String address) throws ParseException {
        if (address == null) { //TODO abstract out null check?
            return new PersonAddress("");
        }

        String trimmedAddress = address.trim();
        if (!PersonAddress.isValidAddress(trimmedAddress)) {
            throw new ParseException(PersonAddress.MESSAGE_CONSTRAINTS);
        }
        return new PersonAddress(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        if (email == null) { //TODO abstract out null check?
            return new Email("");
        }
        String trimmedEmail = email.trim();
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
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
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
            tagSet.add(parseTag(tagName));
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
        if (budgetMin == null) { //TODO abstract out null check?
            return new BudgetMin("0");
        }

        String trimmedBudgetMin = budgetMin.trim();

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
        if (budgetMax == null) { //TODO abstract out null check?
            return new BudgetMax(String.valueOf(200_000_000_000L));
        }

        String trimmedBudgetMax = budgetMax.trim();

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
        if (notes == null) { //TODO abstract out null check?
            return new Notes("");
        }
        String trimmedNotes = notes.trim();
        return new Notes(trimmedNotes); //TODO add exception?
    }

    /**
     * Parses a {@code String status} into a {@code Status}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code status} is invalid (fails {@link Status#isValidStatus}).
     */
    public static Status parseStatus(String status) throws ParseException {
        if (status == null) { //TODO abstract out null check?
            return new Status("");
        }
        String trimmedStatus = status.trim();
        if (!Status.isValidStatus(trimmedStatus)) {
            throw new ParseException(Status.MESSAGE_CONSTRAINTS);
        }
        return new Status(trimmedStatus);
    }
}
