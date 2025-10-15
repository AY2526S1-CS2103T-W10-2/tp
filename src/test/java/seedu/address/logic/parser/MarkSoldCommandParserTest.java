package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.MarkSoldCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Contains unit tests for {@code MarkSoldCommandParser}.
 */
public class MarkSoldCommandParserTest {

    private final MarkSoldCommandParser parser = new MarkSoldCommandParser();

    @Test
    public void parse_validSingleId_success() {
        String userInput = " p/123";
        List<String> expectedIds = List.of("123");
        MarkSoldCommand expectedCommand = new MarkSoldCommand(expectedIds);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_validMultipleIds_success() {
        String userInput = " p/10 p/20 p/30";
        List<String> expectedIds = Arrays.asList("10", "20", "30");
        MarkSoldCommand expectedCommand = new MarkSoldCommand(expectedIds);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingPrefix_failure() {
        String userInput = " 10 20";
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkSoldCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyArgs_failure() {
        String userInput = "";
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkSoldCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_onlyPrefixNoValue_failure() {
        String userInput = " p/ ";
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkSoldCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_withExtraSpaces_success() {
        String userInput = "   p/5    p/42   ";
        List<String> expectedIds = Arrays.asList("5", "42");
        MarkSoldCommand expectedCommand = new MarkSoldCommand(expectedIds);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_duplicateIds_failure() {
        String input = "p/111 p/222 p/111"; // duplicate 111
        ParseException exception = assertThrows(ParseException.class, () -> parser.parse(input));
        assertEquals("Duplicate property ID detected: 111", exception.getMessage());
    }
}
