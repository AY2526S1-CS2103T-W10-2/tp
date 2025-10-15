package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalProperties.PROPERTY_ALPHA;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddPropertyCommand;
import seedu.address.logic.commands.DeletePropertyCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.property.Property;

class PropertyBookParserTest {

    private static final String VALID_ADD_PROPERTY_COMMAND = String.join(" ",
            AddPropertyCommand.COMMAND_WORD,
            "address/123 Main St 5",
            "postal/123456",
            "price/500000",
            "type/HDB",
            "status/unsold",
            "bedroom/3",
            "bathroom/2",
            "floorarea/120",
            "owner/owner123",
            "listing/sale");

    private static final String CONFLICTING_ADD_PROPERTY_COMMAND = String.join(" ",
            AddPropertyCommand.COMMAND_WORD,
            "address/123 Main St 5",
            "postal/123456",
            "price/500000",
            "type/HDB",
            "status/sold",
            "bedroom/3",
            "bathroom/2",
            "floorarea/120",
            "owner/owner123",
            "listing/rent");

    private static final String VALID_DELETE_PROPERTY_COMMAND = String.join(" ",
            DeletePropertyCommand.COMMAND_WORD,
            "abc123");

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
        DeletePropertyCommand expectedCommand = new DeletePropertyCommand("abc123");
        DeletePropertyCommand command = (DeletePropertyCommand) parser.parseCommand(VALID_DELETE_PROPERTY_COMMAND);
        assertTrue(command.equals(expectedCommand));
    }

    @Test
    void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("random"));
    }

    @Test
    void parseCommand_invalidArguments_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parseCommand(AddPropertyCommand.COMMAND_WORD));
        assertThrows(ParseException.class, () -> parser.parseCommand(DeletePropertyCommand.COMMAND_WORD));
    }

    @Test
    void parseCommand_conflictingStatusListing_throwsParseException() {
        assertThrows(ParseException.class,
                AddPropertyCommand.MESSAGE_CONFLICT_STATUS_LISTING, () ->
                        parser.parseCommand(CONFLICTING_ADD_PROPERTY_COMMAND));
    }
}
