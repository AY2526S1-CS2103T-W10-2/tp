package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.List;
import java.util.Objects;

import seedu.address.logic.commands.Command;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.exceptions.UnknownCommandParseException;

/**
 * Attempts to parse commands using each delegate parser in sequence until one succeeds.
 */
public class UnifiedCommandParser implements CommandSetParser {
    private final List<CommandSetParser> commandParsers;

    public UnifiedCommandParser(List<CommandSetParser> commandParsers) {
        this.commandParsers = List.copyOf(Objects.requireNonNull(commandParsers));
    }

    @Override
    public Command parseCommand(String userInput) throws ParseException {
        UnknownCommandParseException lastUnknown = null;

        for (CommandSetParser parser : commandParsers) {
            try {
                return parser.parseCommand(userInput);
            } catch (UnknownCommandParseException unknownCommand) {
                lastUnknown = unknownCommand;
            }
        }

        if (lastUnknown != null) {
            throw lastUnknown;
        }
        throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
    }
}
