package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BUDGET_MAX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BUDGET_MAX_RAW;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BUDGET_MIN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BUDGET_MIN_RAW;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LIMIT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OFFSET;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.logic.commands.FilterContactCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.contact.FilterContactPredicate;

/**
 * Parses input arguments and creates a new {@link FilterContactCommand}.
 * <p>
 * This parser handles multiple optional filters such as name, phone, email, address,
 * tags, budgets, notes, and status. Each filter keyword is tokenised and stored
 * inside a {@link FilterContactPredicate} to be used for filtering contacts.
 * All prefixes are optional, and an empty value will not filter that category.
 */
public class FilterContactCommandParser implements Parser<FilterContactCommand> {

    private static final String INTEGER_PARSE_ERROR = "Invalid prefix for integer parsing: ";
    private static final String INVALID_LIMIT_ERROR = "Limit must be greater than 0.";
    private static final String LIMIT_PARSE_ERROR = "Invalid number for limit: ";
    private static final String INVALID_OFFSET_ERROR = "Offset cannot be negative.";
    private static final String OFFSET_PARSE_ERROR = "Invalid number for offset: ";

    // Define the set of allowed prefixes
    private static final Set<Prefix> VALID_PREFIXES = new HashSet<>(Arrays.asList(
            PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
            PREFIX_ADDRESS,
            PREFIX_BUDGET_MIN, PREFIX_BUDGET_MAX,
            PREFIX_NOTES, PREFIX_STATUS,
            PREFIX_LIMIT, PREFIX_OFFSET
    ));

    /**
     * Parses the given {@code String} of arguments and returns a {@link FilterContactCommand}
     * for execution.
     *
     * @param args The user input arguments.
     * @return A {@link FilterContactCommand} containing the constructed {@link FilterContactPredicate}.
     * @throws ParseException If an unrecognized prefix is found.
     */
    @Override
    public FilterContactCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, VALID_PREFIXES.toArray(new Prefix[0]));

        // if there is extra words before the first prefix or wrong prefix is the first
        // example: filtercontact abc n/bob
        //          filtercontact abc
        //          filtercontact abc/
        String preamble = argMultimap.getPreamble();
        if (!preamble.isEmpty()) {
            throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, FilterContactCommand.MESSAGE_USAGE));
        }

        Optional<Integer> limit = parseLimit(argMultimap.getValue(PREFIX_LIMIT));
        Optional<Integer> offset = parseOffset(argMultimap.getValue(PREFIX_OFFSET));

        Optional<Long> budgetMin = Optional.empty();
        if (argMultimap.getValue(PREFIX_BUDGET_MIN).isPresent()) {
            budgetMin = Optional.of(parseMinOrMax(argMultimap.getValue(PREFIX_BUDGET_MIN).get(), PREFIX_BUDGET_MIN));
        }

        Optional<Long> budgetMax = Optional.empty();
        if (argMultimap.getValue(PREFIX_BUDGET_MAX).isPresent()) {
            budgetMax = Optional.of(parseMinOrMax(argMultimap.getValue(PREFIX_BUDGET_MAX).get(), PREFIX_BUDGET_MAX));
        }

        // validates inputs based on the prefix constraints and creates the predicate to test
        FilterContactPredicate predicate = new FilterContactPredicate(
                getKeywords(argMultimap.getValue(PREFIX_NAME), PREFIX_NAME),
                getKeywords(argMultimap.getValue(PREFIX_PHONE), PREFIX_PHONE),
                getKeywords(argMultimap.getValue(PREFIX_EMAIL), PREFIX_EMAIL),
                getKeywords(argMultimap.getValue(PREFIX_ADDRESS), PREFIX_ADDRESS),
                budgetMin,
                budgetMax,
                getKeywords(argMultimap.getValue(PREFIX_NOTES), PREFIX_NOTES),
                getKeywords(argMultimap.getValue(PREFIX_STATUS), PREFIX_STATUS),
                limit,
                offset
        );

        return new FilterContactCommand(predicate);
    }

    /**
     * Splits a value string into a list of keywords if present, then validates each keyword
     * according to the prefix context.
     *
     * @param value  The optional string value.
     * @param prefix The prefix indicating how to validate each keyword.
     * @return An {@link Optional} containing a list of validated keywords or empty if no input.
     * @throws ParseException If any keyword is invalid for the given prefix.
     */
    private Optional<List<String>> getKeywords(Optional<String> value, Prefix prefix) throws ParseException {
        if (value.isEmpty()) {
            return Optional.empty();
        }

        String trimmed = value.get().trim();
        if (trimmed.isEmpty()) {
            return Optional.empty();
        }

        List<String> keywords = Arrays.asList(trimmed.split("\\s+"));

        for (String keyword : keywords) {
            switch (prefix.getPrefix()) {
            case CliSyntax.PREFIX_NAME_RAW:
                ParserUtil.parseName(keyword);
                break;
            case CliSyntax.PREFIX_PHONE_RAW:
                ParserUtil.parsePhone(keyword);
                break;
            case CliSyntax.PREFIX_EMAIL_RAW:
                ParserUtil.parseEmail(keyword);
                break;
            case CliSyntax.PREFIX_ADDRESS_RAW:
                ParserUtil.parseAddress(keyword);
                break;
            case CliSyntax.PREFIX_BUDGET_MIN_RAW:
                ParserUtil.parseBudgetMin(keyword);
                break;
            case CliSyntax.PREFIX_BUDGET_MAX_RAW:
                ParserUtil.parseBudgetMax(keyword);
                break;
            case CliSyntax.PREFIX_STATUS_RAW:
                ParserUtil.parseContactStatus(keyword);
                break;
            case CliSyntax.PREFIX_NOTES_RAW:
                ParserUtil.parseNotes(keyword);
                break;
            default:
                // No validation needed for unknown prefix
                break;
            }
        }

        return Optional.of(keywords);
    }

    /**
     * Parses a string into an integer using prefix-specific parsing logic.
     * Calls {@link ParserUtil#parseBudgetMin} or {@link ParserUtil#parseBudgetMax}
     * based on the provided prefix.
     *
     * @param value The string to parse.
     * @param prefix The prefix identifying the type of budget to parse.
     * @return The parsed integer.
     * @throws ParseException If the string cannot be parsed as a valid budget or prefix is invalid.
     */
    private Long parseMinOrMax(String value, Prefix prefix) throws ParseException {
        switch (prefix.getPrefix()) {
        case PREFIX_BUDGET_MIN_RAW:
            return Long.parseLong(ParserUtil.parseBudgetMin(value).toString());

        case PREFIX_BUDGET_MAX_RAW:
            return Long.parseLong(ParserUtil.parseBudgetMax(value).toString());

        default:
            throw new ParseException(INTEGER_PARSE_ERROR + prefix);
        }
    }

    /**
     * Parses a positive integer value (limit must be > 0).
     *
     * @throws ParseException If the string is not a valid positive integer.
     */
    Optional<Integer> parseLimit(Optional<String> value) throws ParseException {
        if (value.isEmpty() || value.get().isEmpty()) {
            return Optional.empty();
        }
        try {
            int num = Integer.parseInt(value.get().trim());
            if (num <= 0) {
                throw new ParseException(INVALID_LIMIT_ERROR);
            }
            return Optional.of(num);
        } catch (NumberFormatException e) {
            throw new ParseException(LIMIT_PARSE_ERROR + value);
        }
    }

    /**
     * Parses a non-negative integer value (offset must be ≥ 0).
     *
     * @throws ParseException If the string is not a valid non-negative integer.
     */
    Optional<Integer> parseOffset(Optional<String> value) throws ParseException {
        if (value.isEmpty() || value.get().isEmpty()) {
            return Optional.empty();
        }
        try {
            int num = Integer.parseInt(value.get().trim());
            if (num < 0) {
                throw new ParseException(INVALID_OFFSET_ERROR);
            }
            return Optional.of(num);
        } catch (NumberFormatException e) {
            throw new ParseException(OFFSET_PARSE_ERROR + value);
        }
    }
}
