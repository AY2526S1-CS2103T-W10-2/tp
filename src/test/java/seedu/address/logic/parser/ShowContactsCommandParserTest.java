package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.model.uuid.Uuid.StoredItem.PROPERTY;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ShowContactsCommand;
import seedu.address.model.uuid.Uuid;

public class ShowContactsCommandParserTest {

    private final ShowContactsCommandParser parser = new ShowContactsCommandParser();

    @Test
    public void parse_validArgs_returnsShowContactsCommand() {
        Uuid expectedUuid = new Uuid(1, PROPERTY);
        assertParseSuccess(parser, " 1", new ShowContactsCommand(expectedUuid));
    }

    @Test
    public void parse_largeValidArgs_returnsShowContactsCommand() {
        Uuid expectedUuid = new Uuid(999, PROPERTY);
        assertParseSuccess(parser, " 999", new ShowContactsCommand(expectedUuid));
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowContactsCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_whitespaceOnly_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowContactsCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidUuidNonNumeric_throwsParseException() {
        assertParseFailure(parser, " abc", Uuid.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidUuidNegative_throwsParseException() {
        assertParseFailure(parser, " -1", Uuid.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidUuidZero_throwsParseException() {
        assertParseFailure(parser, " 0", Uuid.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_validArgsWithExtraWhitespace_returnsShowContactsCommand() {
        Uuid expectedUuid = new Uuid(5, PROPERTY);
        assertParseSuccess(parser, "   5   ", new ShowContactsCommand(expectedUuid));
    }
}
