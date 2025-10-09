package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.MarkUnsoldCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Contains unit tests for {@code MarkUnsoldCommandParser}.
 */
public class MarkUnsoldCommandParserTest {

    private final MarkUnsoldCommandParser parser = new MarkUnsoldCommandParser();

    @Test
    public void parse_validSingleId_success() {
        String userInput = " p/1";
        List<String> expectedIds = List.of("1");
        MarkUnsoldCommand expectedCommand = new MarkUnsoldCommand(expectedIds);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_validMultipleIds_success() {
        String userInput = " p/100 p/200";
        List<String> expectedIds = Arrays.asList("100", "200");
        MarkUnsoldCommand expectedCommand = new MarkUnsoldCommand(expectedIds);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingPrefix_failure() {
        String userInput = " 100 200";
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkUnsoldCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyArgs_failure() {
        String userInput = "";
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkUnsoldCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_onlyPrefixNoValue_failure() {
        String userInput = " p/ ";
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkUnsoldCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_withExtraSpaces_success() {
        String userInput = "   p/55   p/77   ";
        List<String> expectedIds = Arrays.asList("55", "77");
        MarkUnsoldCommand expectedCommand = new MarkUnsoldCommand(expectedIds);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_duplicateIds_failure() {
        String input = "p/aaa p/bbb p/aaa"; // duplicate aaa
        ParseException exception = assertThrows(ParseException.class, () -> parser.parse(input));
        assertEquals("Duplicate property ID detected: aaa", exception.getMessage());
    }
}
