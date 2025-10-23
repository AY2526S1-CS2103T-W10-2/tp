package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalProperties.PROPERTY_GAMMA;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddPropertyCommand;
import seedu.address.logic.commands.EditContactCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.property.Property;

class UnifiedCommandParserTest {

    private static final String VALID_ADD_PROPERTY_COMMAND = String.join(" ",
            AddPropertyCommand.COMMAND_WORD,
            "address/321 Market St 9",
            "postal/654321",
            "price/750000",
            "type/Condo",
            "status/unavailable",
            "bedroom/4",
            "bathroom/3",
            "floorarea/150",
            "listing/rent",
            "owner/owner321");

    private final UnifiedCommandParser parser = new UnifiedCommandParser(List.of(
            new AddressBookParser(),
            new PropertyBookParser()
    ));

    @Test
    void parseCommand_addressBookCommand_success() throws ParseException {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
    }

    @Test
    void parseCommand_propertyBookCommand_success() throws ParseException {
        Property expectedProperty = PROPERTY_GAMMA;
        AddPropertyCommand expectedCommand = new AddPropertyCommand(expectedProperty);

        AddPropertyCommand command = (AddPropertyCommand) parser.parseCommand(VALID_ADD_PROPERTY_COMMAND);
        assertTrue(command.equals(expectedCommand));
    }

    @Test
    void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("nonsense"));
    }

    @Test
    void parseCommand_addressBookSpecificError_propagatesParseException() {
        String invalidEdit = EditContactCommand.COMMAND_WORD; // Missing index and fields
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditContactCommand.MESSAGE_USAGE), () -> parser.parseCommand(invalidEdit));
    }
}
