package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LINK_PROPERTY_ID;

import java.util.stream.Stream;

import seedu.address.logic.commands.ShowClientsCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ShowClientsCommand object.
 */
public class ShowClientsCommandParser implements Parser<ShowClientsCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ShowClientsCommand
     * and returns a ShowClientsCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public ShowClientsCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_LINK_PROPERTY_ID);

        if (!arePrefixesPresent(argMultimap, PREFIX_LINK_PROPERTY_ID)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ShowClientsCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_LINK_PROPERTY_ID);

        String propertyId = argMultimap.getValue(PREFIX_LINK_PROPERTY_ID).get().trim();

        if (propertyId.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ShowClientsCommand.MESSAGE_USAGE));
        }

        return new ShowClientsCommand(propertyId);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
