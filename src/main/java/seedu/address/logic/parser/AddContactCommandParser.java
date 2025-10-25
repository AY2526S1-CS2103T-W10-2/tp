package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BUDGET_MAX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BUDGET_MIN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.HashSet;
import java.util.Set;

import seedu.address.logic.commands.AddContactCommand;
import seedu.address.logic.parser.exceptions.ParseException;
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
 * Parses input arguments and creates a new AddContactCommand object
 */
public class AddContactCommandParser implements Parser<AddContactCommand> {
    public static final String NAME_AND_PHONE_MISSING = "Name (n/NAME) and Phone (p/PHONE) parameters are missing.\n";
    public static final String NAME_MISSING = "Name parameter (n/NAME) is missing.\n";
    public static final String PHONE_MISSING = "Phone parameter (p/PHONE) is missing.\n";
    /**
     * Parses the given {@code String} of arguments in the context of the AddContactCommand
     * and returns an AddContactCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public AddContactCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE,
                                                                  PREFIX_EMAIL, PREFIX_ADDRESS,
                                                                  PREFIX_TAG, PREFIX_BUDGET_MIN,
                                                                  PREFIX_BUDGET_MAX, PREFIX_NOTES,
                                                                  PREFIX_STATUS);

        validateRequiredPrefixesPresent(argMultimap);
        validateNoInvalidPrefixesPresent(argMultimap);

        argMultimap.verifyNoDuplicatePrefixesFor(
                PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                PREFIX_BUDGET_MIN, PREFIX_BUDGET_MAX, PREFIX_NOTES, PREFIX_STATUS
        );

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).orElse(null));
        ContactAddress address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).orElse(null));
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        BudgetMin budgetMin = ParserUtil.parseBudgetMin(argMultimap.getValue(PREFIX_BUDGET_MIN).orElse(null));
        BudgetMax budgetMax = ParserUtil.parseBudgetMax(argMultimap.getValue(PREFIX_BUDGET_MAX).orElse(null));
        Notes notes = ParserUtil.parseNotes(argMultimap.getValue(PREFIX_NOTES).orElse(null));
        ContactStatus status = ParserUtil.parseContactStatus(argMultimap.getValue(PREFIX_STATUS).orElse(null));
        Set<Uuid> emptyBuyingPropertyIds = new HashSet<>();
        Set<Uuid> emptySellingPropertyIds = new HashSet<>();

        // Validate budget range
        if (Long.parseLong(budgetMax.toString()) < Long.parseLong(budgetMin.toString())) {
            throw new ParseException("Budget maximum cannot be lesser than budget minimum.");
        }

        // Correct UUID will be made in AddContactCommand
        Contact contact = new Contact(name, phone, email, address, tagList,
                                   budgetMin, budgetMax, notes, status,
                                   emptyBuyingPropertyIds, emptySellingPropertyIds);

        return new AddContactCommand(contact);
    }

    /**
     * Validates that all required prefixes are present (PREFIX_NAME, PREFIX_PHONE)
     * and that the preamble is empty.
     *
     * @param argMultimap The argument multimap containing parsed arguments.
     * @throws ParseException if required prefixes (name and phone) are missing
     *                        or if the preamble is not empty.
     */
    private void validateRequiredPrefixesPresent(ArgumentMultimap argMultimap) throws ParseException {
        boolean hasName = argMultimap.arePrefixesPresent(PREFIX_NAME);
        boolean hasPhone = argMultimap.arePrefixesPresent(PREFIX_PHONE);
        boolean hasAnyParameter = argMultimap.getAllPrefixes()
                                    .stream()
                                    .anyMatch(prefix -> !prefix.getPrefix().isEmpty());

        // Check if preamble is not empty
        // for commands like addcontact abc
        // If no parameters at all, show only the normal error
        if (!argMultimap.getPreamble().isEmpty() || !hasAnyParameter) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddContactCommand.MESSAGE_USAGE));
        }

        // Check for missing compulsory parameters
        if (!hasName && !hasPhone) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    NAME_AND_PHONE_MISSING + AddContactCommand.MESSAGE_USAGE));
        } else if (!hasName) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    NAME_MISSING + AddContactCommand.MESSAGE_USAGE));
        } else if (!hasPhone) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    PHONE_MISSING + AddContactCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Validates that prefix values do not contain unrecognised prefixes.
     * Skips validation for notes prefix as '/' is valid in notes.
     *
     * @param argMultimap The argument multimap containing parsed arguments.
     * @throws ParseException if a value contains what looks like a prefix,
     *                        with a specific error message for that field type.
     */
    private void validateNoInvalidPrefixesPresent(ArgumentMultimap argMultimap) throws ParseException {
        for (Prefix prefix : argMultimap.getAllPrefixes()) {
            // Skip checking for notes prefix, since '/' is valid in notes
            if (prefix.equals(PREFIX_NOTES)) {
                continue;
            }

            for (String value : argMultimap.getAllValues(prefix)) {
                if (ParserUtil.looksLikePrefix(value)) {
                    String errorMessage = getErrorMessageForPrefix(prefix, value);
                    throw new ParseException(errorMessage);
                }
            }
        }
    }

    /**
     * Returns the appropriate error message for a given prefix.
     *
     * @param prefix The prefix to get the error message for.
     * @return The specific MESSAGE_CONSTRAINTS for that field type,
     *         or a generic message if the prefix is not recognized.
     */
    private String getErrorMessageForPrefix(Prefix prefix, String value) {
        if (prefix.equals(PREFIX_NAME)) {
            return String.format(MESSAGE_INVALID_COMMAND_FORMAT, Name.MESSAGE_CONSTRAINTS);
        } else if (prefix.equals(PREFIX_PHONE)) {
            return String.format(MESSAGE_INVALID_COMMAND_FORMAT, Phone.MESSAGE_CONSTRAINTS);
        } else if (prefix.equals(PREFIX_EMAIL)) {
            return String.format(MESSAGE_INVALID_COMMAND_FORMAT, Email.MESSAGE_CONSTRAINTS);
        } else if (prefix.equals(PREFIX_ADDRESS)) {
            return String.format(MESSAGE_INVALID_COMMAND_FORMAT, ContactAddress.MESSAGE_CONSTRAINTS);
        } else if (prefix.equals(PREFIX_TAG)) {
            return String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    String.format(Tag.MESSAGE_CONSTRAINTS, value));
        } else if (prefix.equals(PREFIX_BUDGET_MIN)) {
            return String.format(MESSAGE_INVALID_COMMAND_FORMAT, BudgetMin.MESSAGE_CONSTRAINTS);
        } else if (prefix.equals(PREFIX_BUDGET_MAX)) {
            return String.format(MESSAGE_INVALID_COMMAND_FORMAT, BudgetMax.MESSAGE_CONSTRAINTS);
        } else if (prefix.equals(PREFIX_STATUS)) {
            return String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    String.format(ContactStatus.MESSAGE_CONSTRAINTS, value));
        } else {
            return String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddContactCommand.MESSAGE_USAGE);
        }
    }

}
