package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.logic.commands.MarkSoldCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code MarkSoldCommand}.
 */
public class MarkSoldCommandParser implements Parser<MarkSoldCommand> {

    @Override
    public MarkSoldCommand parse(String args) throws ParseException {
        String[] tokens = args.trim().split("\\s+");

        if (tokens.length == 0 || !tokens[0].startsWith("p/")) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkSoldCommand.MESSAGE_USAGE));
        }

        List<String> ids = new ArrayList<>();
        for (String token : tokens) {
            if (token.startsWith("p/")) {
                String id = token.substring(2).trim();
                if (!id.isEmpty()) {
                    ids.add(id);
                }
            }
        }

        Set<String> seen = new HashSet<>();
        for (String id : ids) {
            if (!seen.add(id)) {
                throw new ParseException("Duplicate property ID detected: " + id);
            }
        }

        if (ids.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkSoldCommand.MESSAGE_USAGE));
        }

        return new MarkSoldCommand(ids);
    }
}
