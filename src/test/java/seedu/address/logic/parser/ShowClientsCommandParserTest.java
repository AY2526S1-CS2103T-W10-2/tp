package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ShowClientsCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class ShowClientsCommandParserTest {

    private ShowClientsCommandParser parser = new ShowClientsCommandParser();

    @Test
    public void parse_validArgs_success() throws Exception {
        // Test that parser correctly creates command with valid property ID
        ShowClientsCommand expectedCommand = new ShowClientsCommand("abc123");
        ShowClientsCommand actualCommand = parser.parse(" p/abc123");

        assert(expectedCommand.equals(actualCommand));
    }

    @Test
    public void parse_validArgsWithExtraSpaces_success() throws Exception {
        // Test that parser handles extra leading/trailing spaces correctly
        ShowClientsCommand expectedCommand = new ShowClientsCommand("xyz789");
        ShowClientsCommand actualCommand = parser.parse("  p/xyz789  ");

        assert(expectedCommand.equals(actualCommand));
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        // Test that empty input throws appropriate error
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowClientsCommand.MESSAGE_USAGE), () ->
                        parser.parse(""));
    }

    @Test
    public void parse_missingPrefix_throwsParseException() {
        // Test that property ID without 'p/' prefix is rejected
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowClientsCommand.MESSAGE_USAGE), () ->
                        parser.parse("abc123"));
    }

    @Test
    public void parse_emptyPropertyId_throwsParseException() {
        // Test that 'p/' with no property ID is rejected
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowClientsCommand.MESSAGE_USAGE), () ->
                        parser.parse(" p/"));
    }

    @Test
    public void parse_emptyPropertyIdWithSpaces_throwsParseException() {
        // Test that 'p/' with only spaces is rejected
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowClientsCommand.MESSAGE_USAGE), () ->
                        parser.parse(" p/   "));
    }

    @Test
    public void parse_preamblePresent_throwsParseException() {
        // Test that text before the prefix is rejected
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowClientsCommand.MESSAGE_USAGE), () ->
                        parser.parse("some preamble p/abc123"));
    }

    @Test
    public void parse_duplicatePrefix_throwsParseException() {
        // Test that multiple 'p/' prefixes are rejected
        assertThrows(ParseException.class, () -> parser.parse(" p/abc123 p/def456"));
    }
}
