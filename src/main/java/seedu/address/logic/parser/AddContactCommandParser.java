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
import seedu.address.model.uuid.Uuid;

/**
 * Parses input arguments and creates a new AddContactCommand object
 */
public class AddContactCommandParser implements Parser<AddContactCommand> {
    public static final String NAME_AND_PHONE_MISSING =
            "Name (" + PREFIX_NAME + "NAME) and Phone (" + PREFIX_PHONE + "PHONE) parameters are missing.\n";
    public static final String NAME_MISSING = "Name parameter (" + PREFIX_NAME + "NAME) is missing.\n";
    public static final String PHONE_MISSING = "Phone parameter (" + PREFIX_PHONE + "PHONE) is missing.\n";
    public static final String BUDGET_MAX_MORE_THAN_MIN_ERROR = "Budget maximum cannot be lesser than budget minimum.";
    /**
     * Parses the given {@code String} of arguments in the context of the AddContactCommand
     * and returns an AddContactCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public AddContactCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE,
                                                                  PREFIX_EMAIL, PREFIX_ADDRESS,
                                                                  PREFIX_BUDGET_MIN,
                                                                  PREFIX_BUDGET_MAX, PREFIX_NOTES,
                                                                  PREFIX_STATUS);

        validateRequiredPrefixesPresent(argMultimap);

        argMultimap.verifyNoDuplicatePrefixesFor(
                PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                PREFIX_BUDGET_MIN, PREFIX_BUDGET_MAX, PREFIX_NOTES, PREFIX_STATUS
        );

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).orElse(null));
        ContactAddress address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).orElse(null));
        BudgetMin budgetMin = ParserUtil.parseBudgetMin(argMultimap.getValue(PREFIX_BUDGET_MIN).orElse(null));
        BudgetMax budgetMax = ParserUtil.parseBudgetMax(argMultimap.getValue(PREFIX_BUDGET_MAX).orElse(null));
        Notes notes = ParserUtil.parseNotes(argMultimap.getValue(PREFIX_NOTES).orElse(null));
        ContactStatus status = ParserUtil.parseContactStatus(argMultimap.getValue(PREFIX_STATUS).orElse(null));
        Set<Uuid> emptyBuyingPropertyIds = new HashSet<>();
        Set<Uuid> emptySellingPropertyIds = new HashSet<>();

        // Validate budget range
        if (Long.parseLong(budgetMax.toString()) < Long.parseLong(budgetMin.toString())) {
            throw new ParseException(BUDGET_MAX_MORE_THAN_MIN_ERROR);
        }

        // Correct UUID will be made in AddContactCommand
        Contact contact = new Contact(name, phone, email, address,
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
}
