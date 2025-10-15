package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddContactCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteContactCommand;
import seedu.address.logic.commands.EditContactCommand;
import seedu.address.logic.commands.EditContactCommand.EditPersonDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FilterContactCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.FilterContactPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.Uuid;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilderUtil;
import seedu.address.testutil.PersonUtil;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilderUtil().build();
        AddContactCommand command = (AddContactCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new AddContactCommand(person), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteContactCommand command = (DeleteContactCommand) parser.parseCommand(
                DeleteContactCommand.COMMAND_WORD + " c/1");
        assertEquals(new DeleteContactCommand(new Uuid(1)), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilderUtil().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        Uuid targetUuid = person.getUuid();
        String commandString = EditContactCommand.COMMAND_WORD + " "
                + targetUuid.toString() + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor);
        EditContactCommand command = (EditContactCommand) parser.parseCommand(commandString);

        assertEquals(new EditContactCommand(targetUuid, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    public void parseCommand_filterContact() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");

        FilterContactCommand command = (FilterContactCommand) parser.parseCommand(
                FilterContactCommand.COMMAND_WORD + " n/" + String.join(" ", keywords));

        FilterContactPredicate expectedPredicate = new FilterContactPredicate(
                Optional.of(keywords), Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty(), Optional.empty());

        assertEquals(new FilterContactCommand(expectedPredicate), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
