package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BUDGET_MAX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BUDGET_MIN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LIMIT; // TODO
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OFFSET; // TODO
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.logic.commands.FilterContactCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.FilterContactPredicate;

/**
 * Parses input arguments and creates a new {@link FilterContactCommand}.
 * <p>
 * This parser handles multiple optional filters such as name, phone, email, address,
 * tags, budgets, notes, and status. Each filter keyword is tokenised and stored
 * inside a {@link FilterContactPredicate} to be used for filtering contacts.
 * All prefixes are optional, and an empty value will not filter that category.
 */
public class FilterContactCommandParser implements Parser<FilterContactCommand> {

    // Define the set of allowed prefixes
    private static final Set<Prefix> VALID_PREFIXES = new HashSet<>(Arrays.asList(
            PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
            PREFIX_ADDRESS, PREFIX_TAG,
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

        // Check inside all values for invalid prefixes
        // for invalid prefixes -> filtercontact n/brian abc/
        // the argMultimap will have key = "n/" and value = "brian abc/"
        for (Prefix prefix : argMultimap.getAllPrefixes()) {
            for (String value : argMultimap.getAllValues(prefix)) {
                if (looksLikePrefix(value)) {
                    throw new ParseException(String.format(
                            MESSAGE_INVALID_COMMAND_FORMAT, FilterContactCommand.MESSAGE_USAGE));
                }
            }
        }

        FilterContactPredicate predicate = new FilterContactPredicate(
                getKeywords(argMultimap.getValue(PREFIX_NAME)),
                getKeywords(argMultimap.getValue(PREFIX_PHONE)),
                getKeywords(argMultimap.getValue(PREFIX_EMAIL)),
                getKeywords(argMultimap.getValue(PREFIX_ADDRESS)),
                getKeywords(argMultimap.getValue(PREFIX_TAG)),
                argMultimap.getValue(PREFIX_BUDGET_MIN).map(this::parseInteger),
                argMultimap.getValue(PREFIX_BUDGET_MAX).map(this::parseInteger),
                getKeywords(argMultimap.getValue(PREFIX_NOTES)),
                getKeywords(argMultimap.getValue(PREFIX_STATUS))
        );

        return new FilterContactCommand(predicate);
    }

    /**
     * Returns true if the given string looks like an unrecognized prefix (e.g., "x/foo").
     */
    private boolean looksLikePrefix(String s) {
        s = s.trim();
        return s.contains("/"); // naive checking TODO: could result in error if notes has /
    }


    /**
     * Splits a value string into a list of keywords if present.
     *
     * @param value The optional string value.
     * @return An {@link Optional} containing a list of trimmed keywords, or empty if not present.
     */
    private Optional<List<String>> getKeywords(Optional<String> value) {
        return value
                .map(String::trim)
                .filter(v -> !v.isEmpty())
                .map(v -> Arrays.asList(v.split("\\s+")));
    }

    /**
     * Parses a string into an integer.
     *
     * @param value The string to parse.
     * @return The parsed integer.
     * @throws NumberFormatException If the string cannot be parsed as an integer.
     */
    private Integer parseInteger(String value) throws NumberFormatException {
        return Integer.parseInt(value.trim());
    }
}
