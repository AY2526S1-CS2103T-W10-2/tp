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
import seedu.address.model.person.BudgetMax;
import seedu.address.model.person.BudgetMin;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Notes;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonAddress;
import seedu.address.model.person.PersonStatus;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Tag;
import seedu.address.model.uuid.Uuid;

/**
 * Parses input arguments and creates a new AddContactCommand object
 */
public class AddContactCommandParser implements Parser<AddContactCommand> {

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

        if (!argMultimap.arePrefixesPresent(PREFIX_NAME, PREFIX_PHONE) // only name and phone are compulsory prefixes
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddContactCommand.MESSAGE_USAGE));
        }

        // Check for any unrecognised prefixes inside values
        for (Prefix prefix : argMultimap.getAllPrefixes()) {
            // Skip checking for notes prefix, since '/' is valid in notes
            if (prefix.equals(PREFIX_NOTES)) {
                continue;
            }
            for (String value : argMultimap.getAllValues(prefix)) {
                if (ParserUtil.looksLikePrefix(value)) {
                    throw new ParseException(String.format(
                            MESSAGE_INVALID_COMMAND_FORMAT, AddContactCommand.MESSAGE_USAGE));
                }
            }
        }

        argMultimap.verifyNoDuplicatePrefixesFor(
                PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                PREFIX_BUDGET_MIN, PREFIX_BUDGET_MAX, PREFIX_NOTES, PREFIX_STATUS
        );

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).orElse(null));
        PersonAddress address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).orElse(null));
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        BudgetMin budgetMin = ParserUtil.parseBudgetMin(argMultimap.getValue(PREFIX_BUDGET_MIN).orElse(null));
        BudgetMax budgetMax = ParserUtil.parseBudgetMax(argMultimap.getValue(PREFIX_BUDGET_MAX).orElse(null));
        Notes notes = ParserUtil.parseNotes(argMultimap.getValue(PREFIX_NOTES).orElse(null));
        PersonStatus status = ParserUtil.parsePersonStatus(argMultimap.getValue(PREFIX_STATUS).orElse(null));
        Set<Uuid> emptyBuyingPropertyIds = new HashSet<>();
        Set<Uuid> emptySellingPropertyIds = new HashSet<>();

        // Validate budget range
        if (Long.parseLong(budgetMax.toString()) < Long.parseLong(budgetMin.toString())) {
            throw new ParseException("Budget max cannot be less than budget min.");
        }

        // Correct UUID will be made in AddContactCommand
        Person person = new Person(name, phone, email, address, tagList,
                                   budgetMin, budgetMax, notes, status,
                                   emptyBuyingPropertyIds, emptySellingPropertyIds);

        return new AddContactCommand(person);
    }
}
