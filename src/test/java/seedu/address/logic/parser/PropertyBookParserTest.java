package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddPropertyCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.property.Bathroom;
import seedu.address.model.property.Bedroom;
import seedu.address.model.property.FloorArea;
import seedu.address.model.property.Listing;
import seedu.address.model.property.Owner;
import seedu.address.model.property.Postal;
import seedu.address.model.property.Price;
import seedu.address.model.property.Property;
import seedu.address.model.property.PropertyAddress;
import seedu.address.model.property.Status;
import seedu.address.model.property.Type;

class PropertyBookParserTest {

    private static final String VALID_ADD_PROPERTY_COMMAND = String.join(" ",
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

    private final PropertyBookParser parser = new PropertyBookParser();

    @Test
    void parseCommand_addProperty() throws Exception {
        Property expectedProperty = new Property(new PropertyAddress("123 Main St 5"), new Bathroom("2"),
                new Bedroom("3"), new FloorArea("120"), new Listing("sale"), new Postal("123456"),
                new Price("500000"), new Status("sold"), new Type("HDB"), new Owner("owner123"));
        AddPropertyCommand expectedCommand = new AddPropertyCommand(expectedProperty);

        AddPropertyCommand command = (AddPropertyCommand) parser.parseCommand(VALID_ADD_PROPERTY_COMMAND);
        assertTrue(command.equals(expectedCommand));
    }

    @Test
    void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("random"));
    }

    @Test
    void parseCommand_invalidArguments_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parseCommand(AddPropertyCommand.COMMAND_WORD));
    }

    @Test
    void parseCommand_conflictingStatusListing_throwsParseException() {
        assertThrows(ParseException.class,
                AddPropertyCommand.MESSAGE_CONFLICT_STATUS_LISTING, () ->
                        parser.parseCommand(CONFLICTING_ADD_PROPERTY_COMMAND));
    }
}
