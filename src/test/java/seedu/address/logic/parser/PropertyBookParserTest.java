package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_BATHROOM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_BEDROOM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_FLOOR_AREA;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_LISTING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_OWNER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_POSTAL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_TYPE;
import static seedu.address.model.uuid.Uuid.StoredItem.PROPERTY;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalProperties.PROPERTY_ALPHA;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddPropertyCommand;
import seedu.address.logic.commands.DeletePropertyCommand;
import seedu.address.logic.commands.FilterPropertyCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.property.Property;
import seedu.address.model.uuid.Uuid;

class PropertyBookParserTest {

    private static final String VALID_ADD_PROPERTY_COMMAND = String.join(" ",
            AddPropertyCommand.COMMAND_WORD,
            " " + PREFIX_PROPERTY_ADDRESS + "123 Main St 5",
            " " + PREFIX_PROPERTY_POSTAL + "123456",
            " " + PREFIX_PROPERTY_PRICE + "500000",
            " " + PREFIX_PROPERTY_TYPE + "HDB",
            " " + PREFIX_PROPERTY_STATUS + "unavailable",
            " " + PREFIX_PROPERTY_BEDROOM + "3",
            " " + PREFIX_PROPERTY_BATHROOM + "2",
            " " + PREFIX_PROPERTY_FLOOR_AREA + "120",
            " " + PREFIX_PROPERTY_LISTING + "sale",
            " " + PREFIX_PROPERTY_OWNER + "owner123");

    private static final String VALID_DELETE_PROPERTY_COMMAND = String.join(" ",
            DeletePropertyCommand.COMMAND_WORD,
            "123");

    private final PropertyBookParser parser = new PropertyBookParser();

    @Test
    void parseCommand_addProperty() throws Exception {
        Property expectedProperty = PROPERTY_ALPHA;
        AddPropertyCommand expectedCommand = new AddPropertyCommand(expectedProperty);

        AddPropertyCommand command = (AddPropertyCommand) parser.parseCommand(VALID_ADD_PROPERTY_COMMAND);
        assertTrue(command.equals(expectedCommand));
    }

    @Test
    void parseCommand_deleteProperty() throws Exception {
        DeletePropertyCommand expectedCommand = new DeletePropertyCommand(new Uuid(123, PROPERTY));
        DeletePropertyCommand command = (DeletePropertyCommand) parser.parseCommand(VALID_DELETE_PROPERTY_COMMAND);
        assertTrue(command.equals(expectedCommand));
    }

    @Test
    void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("random"));
    }

    @Test
    void parseCommand_blankInput_throwsParseException() {
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), () ->
                    parser.parseCommand("   "));
    }

    @Test
    void parseCommand_invalidArguments_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parseCommand(AddPropertyCommand.COMMAND_WORD));
        assertThrows(ParseException.class, () -> parser.parseCommand(DeletePropertyCommand.COMMAND_WORD));
    }

    @Test
    public void parseCommand_filterProperty() throws Exception {
        assertTrue(parser.parseCommand("filterproperty " + PREFIX_PROPERTY_TYPE + "hdb")
                instanceof FilterPropertyCommand);
    }
}
