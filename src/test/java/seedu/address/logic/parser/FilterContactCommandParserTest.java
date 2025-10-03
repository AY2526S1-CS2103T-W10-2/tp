package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FilterContactCommand;
import seedu.address.model.person.FilterContactPredicate;

public class FilterContactCommandParserTest {

    private final FilterContactCommandParser parser = new FilterContactCommandParser();

    @Test
    public void parse_emptyArgs_returnsFilterContactCommand() {
        FilterContactCommand expectedCommand =
                new FilterContactCommand(new FilterContactPredicate(
                        Optional.empty(), Optional.empty(), Optional.empty(),
                        Optional.empty(), Optional.empty(), Optional.empty(),
                        Optional.empty(), Optional.empty(), Optional.empty()));

        assertParseSuccess(parser, "", expectedCommand);
        assertParseSuccess(parser, "    ", expectedCommand);
    }

    @Test
    public void parse_validArgs_returnsFilterContactCommand() {
        FilterContactCommand expectedCommand =
                new FilterContactCommand(new FilterContactPredicate(
                        Optional.of(Arrays.asList("Alice", "Bob")),
                        Optional.of(Arrays.asList("12345678")),
                        Optional.of(Arrays.asList("alice@example.com")),
                        Optional.empty(),
                        Optional.empty(),
                        Optional.empty(),
                        Optional.empty(),
                        Optional.empty(),
                        Optional.empty()
                ));

        assertParseSuccess(parser, " n/Alice Bob p/12345678 e/alice@example.com", expectedCommand);

        // Test with multiple prefixes and whitespace
        assertParseSuccess(parser, " \n n/Alice Bob \n p/12345678 \t e/alice@example.com \t", expectedCommand);
    }

    @Test
    public void parse_invalidPrefix_throwsParseException() {
        assertParseFailure(parser, " abc/foo",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterContactCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "n/Bob abc/",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterContactCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_extraPreamble_throwsParseException() {
        assertParseFailure(parser, "extra n/Bob",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterContactCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "randomtext",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterContactCommand.MESSAGE_USAGE));
    }
}
