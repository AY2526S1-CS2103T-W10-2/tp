package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BUDGET_MAX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BUDGET_MIN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.logic.commands.EditContactCommand;
import seedu.address.logic.commands.EditContactCommand.EditPersonDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Uuid;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditContactCommand object
 */
public class EditContactCommandParser implements Parser<EditContactCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditContactCommand
     * and returns an EditContactCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditContactCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
                args,
                PREFIX_NAME,
                PREFIX_PHONE,
                PREFIX_EMAIL,
                PREFIX_ADDRESS,
                PREFIX_TAG,
                PREFIX_BUDGET_MIN,
                PREFIX_BUDGET_MAX,
                PREFIX_NOTES,
                PREFIX_STATUS
        );

        String preamble = argMultimap.getPreamble().trim();
        if (preamble.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditContactCommand.MESSAGE_USAGE));
        }

        Uuid targetUuid;

        try {
            targetUuid = ParserUtil.parseUuid(preamble);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditContactCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(
                PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                PREFIX_BUDGET_MIN, PREFIX_BUDGET_MAX, PREFIX_NOTES, PREFIX_STATUS
        );

        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editPersonDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            editPersonDescriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            editPersonDescriptor.setEmail(ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get()));
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            editPersonDescriptor.setAddress(ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get()));
        }
        if (argMultimap.getValue(PREFIX_BUDGET_MIN).isPresent()) {
            editPersonDescriptor.setBudgetMin(ParserUtil.parseBudgetMin(argMultimap.getValue(PREFIX_BUDGET_MIN).get()));
        }
        if (argMultimap.getValue(PREFIX_BUDGET_MAX).isPresent()) {
            editPersonDescriptor.setBudgetMax(ParserUtil.parseBudgetMax(argMultimap.getValue(PREFIX_BUDGET_MAX).get()));
        }
        if (argMultimap.getValue(PREFIX_NOTES).isPresent()) {
            editPersonDescriptor.setNotes(ParserUtil.parseNotes(argMultimap.getValue(PREFIX_NOTES).get()));
        }
        if (argMultimap.getValue(PREFIX_STATUS).isPresent()) {
            editPersonDescriptor.setStatus(ParserUtil.parsePersonStatus(argMultimap.getValue(PREFIX_STATUS).get()));
        }
        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editPersonDescriptor::setTags);

        if (!editPersonDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditContactCommand.MESSAGE_NOT_EDITED);
        }

        return new EditContactCommand(targetUuid, editPersonDescriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

}
