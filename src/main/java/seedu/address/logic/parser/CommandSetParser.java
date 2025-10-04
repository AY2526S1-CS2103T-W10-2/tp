package seedu.address.logic.parser;

import seedu.address.logic.commands.Command;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input into a high-level {@link Command}, typically by dispatching
 * to command-specific parsers within a feature area.
 */
public interface CommandSetParser {
    Command parseCommand(String userInput) throws ParseException;
}
