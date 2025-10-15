package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.DeleteContactCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Uuid;

/**
 * Parses input arguments and creates a new DeleteContactCommand object
 */
public class DeleteContactCommandParser implements Parser<DeleteContactCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteContactCommand
     * and returns a DeleteContactCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format.
     */
    @Override
    public DeleteContactCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        if (!trimmedArgs.startsWith("c/") || trimmedArgs.length() <= 2) {
            throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, DeleteContactCommand.MESSAGE_USAGE));
        }

        String idString = trimmedArgs.substring(2).trim();
        int id;

        try {
            id = Integer.parseInt(idString);
        } catch (NumberFormatException e) {
            throw new ParseException("UUID must be a positive integer.");
        }

        if (!Uuid.isValidUuid(id)) {
            throw new ParseException(Uuid.MESSAGE_CONSTRAINTS);
        }

        return new DeleteContactCommand(new Uuid(id));
    }
}
