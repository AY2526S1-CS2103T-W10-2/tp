package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_BATHROOM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_BEDROOM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_FLOOR_AREA;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_OWNER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_POSTAL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_TYPE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddPropertyCommand;

class AddPropertyCommandParserTest {

    private static final String ADDRESS_DESC = " " + PREFIX_PROPERTY_ADDRESS + "123 Main St 5";
    private static final String POSTAL_DESC = " " + PREFIX_PROPERTY_POSTAL + "123456";
    private static final String PRICE_DESC = " " + PREFIX_PROPERTY_PRICE + "500000";
    private static final String TYPE_DESC = " " + PREFIX_PROPERTY_TYPE + "HDB";
    private static final String STATUS_DESC = " " + PREFIX_PROPERTY_STATUS + "unavailable";
    private static final String BEDROOM_DESC = " " + PREFIX_PROPERTY_BEDROOM + "3";
    private static final String BATHROOM_DESC = " " + PREFIX_PROPERTY_BATHROOM + "2";
    private static final String FLOOR_DESC = " " + PREFIX_PROPERTY_FLOOR_AREA + "120";
    private static final String OWNER_DESC = " " + PREFIX_PROPERTY_OWNER + "owner123";

    private final AddPropertyCommandParser parser = new AddPropertyCommandParser();

    @Test
    void parse_missingPostalPrefix_failure() {
        String args = ADDRESS_DESC + PRICE_DESC + TYPE_DESC + STATUS_DESC
                + BEDROOM_DESC + BATHROOM_DESC + FLOOR_DESC + OWNER_DESC;
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddPropertyCommandParser.POSTAL_MISSING + AddPropertyCommand.MESSAGE_USAGE);

        assertParseFailure(parser, args, expectedMessage);
    }

    @Test
    void parse_missingPricePrefix_failure() {
        String args = ADDRESS_DESC + POSTAL_DESC + TYPE_DESC + STATUS_DESC
                + BEDROOM_DESC + BATHROOM_DESC + FLOOR_DESC + OWNER_DESC;
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddPropertyCommandParser.PRICE_MISSING + AddPropertyCommand.MESSAGE_USAGE);

        assertParseFailure(parser, args, expectedMessage);
    }

    @Test
    void parse_missingTypePrefix_failure() {
        String args = ADDRESS_DESC + POSTAL_DESC + PRICE_DESC + STATUS_DESC
                + BEDROOM_DESC + BATHROOM_DESC + FLOOR_DESC + OWNER_DESC;
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddPropertyCommandParser.TYPE_MISSING + AddPropertyCommand.MESSAGE_USAGE);

        assertParseFailure(parser, args, expectedMessage);
    }

    @Test
    void parse_missingStatusPrefix_failure() {
        String args = ADDRESS_DESC + POSTAL_DESC + PRICE_DESC + TYPE_DESC
                + BEDROOM_DESC + BATHROOM_DESC + FLOOR_DESC + OWNER_DESC;
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddPropertyCommandParser.STATUS_MISSING + AddPropertyCommand.MESSAGE_USAGE);

        assertParseFailure(parser, args, expectedMessage);
    }

    @Test
    void parse_missingBedroomPrefix_failure() {
        String args = ADDRESS_DESC + POSTAL_DESC + PRICE_DESC + TYPE_DESC + STATUS_DESC
                + BATHROOM_DESC + FLOOR_DESC + OWNER_DESC;
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddPropertyCommandParser.BEDROOM_MISSING + AddPropertyCommand.MESSAGE_USAGE);

        assertParseFailure(parser, args, expectedMessage);
    }

    @Test
    void parse_missingBathroomPrefix_failure() {
        String args = ADDRESS_DESC + POSTAL_DESC + PRICE_DESC + TYPE_DESC + STATUS_DESC
                + BEDROOM_DESC + FLOOR_DESC + OWNER_DESC;
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddPropertyCommandParser.BATHROOM_MISSING + AddPropertyCommand.MESSAGE_USAGE);

        assertParseFailure(parser, args, expectedMessage);
    }

    @Test
    void parse_missingFloorAreaPrefix_failure() {
        String args = ADDRESS_DESC + POSTAL_DESC + PRICE_DESC + TYPE_DESC + STATUS_DESC
                + BEDROOM_DESC + BATHROOM_DESC + OWNER_DESC;
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddPropertyCommandParser.FLOOR_AREA_MISSING + AddPropertyCommand.MESSAGE_USAGE);

        assertParseFailure(parser, args, expectedMessage);
    }

    @Test
    void parse_missingOwnerPrefix_failure() {
        String args = ADDRESS_DESC + POSTAL_DESC + PRICE_DESC + TYPE_DESC + STATUS_DESC
                + BEDROOM_DESC + BATHROOM_DESC + FLOOR_DESC;
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddPropertyCommandParser.OWNER_MISSING + AddPropertyCommand.MESSAGE_USAGE);

        assertParseFailure(parser, args, expectedMessage);
    }
}
