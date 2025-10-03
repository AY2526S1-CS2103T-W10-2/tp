package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Show all the cleints associated to a property
 */
public class ShowClientsCommand extends Command {

    public static final String COMMAND_WORD = "showclients";
    private final String propertyId;

    public ShowClientsCommand(String propertyId) {
        this.propertyId = propertyId;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        // TODO: implement when Property and Link classes are ready
        return new CommandResult("Feature coming up soon. Property ID: " + propertyId);
    }
}
