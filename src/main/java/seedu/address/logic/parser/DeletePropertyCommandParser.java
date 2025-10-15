package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.DeletePropertyCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeletePropertyCommand object
 */
public class DeletePropertyCommandParser implements Parser<DeletePropertyCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteContactCommand
     * and returns a DeleteContactCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeletePropertyCommand parse(String args) throws ParseException {
        try {
            String id = ParserUtil.parsePropertyId(args);
            return new DeletePropertyCommand(id);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeletePropertyCommand.MESSAGE_USAGE), pe);
        }
    }

}
