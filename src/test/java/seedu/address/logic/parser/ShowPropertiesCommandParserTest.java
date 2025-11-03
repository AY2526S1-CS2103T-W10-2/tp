package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.model.uuid.Uuid.StoredItem.CONTACT;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ShowPropertiesCommand;
import seedu.address.model.uuid.Uuid;

public class ShowPropertiesCommandParserTest {

    private ShowPropertiesCommandParser parser = new ShowPropertiesCommandParser();

    @Test
    public void parse_validArgs_returnsShowPropertiesCommand() {
        Uuid expectedUuid = new Uuid(1, CONTACT);
        assertParseSuccess(parser, " 1", new ShowPropertiesCommand(expectedUuid));
    }

    @Test
    public void parse_validArgsLargeNumber_returnsShowPropertiesCommand() {
        Uuid expectedUuid = new Uuid(123, CONTACT);
        assertParseSuccess(parser, " 123", new ShowPropertiesCommand(expectedUuid));
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowPropertiesCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_whitespaceOnly_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowPropertiesCommand.MESSAGE_USAGE));
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

}
