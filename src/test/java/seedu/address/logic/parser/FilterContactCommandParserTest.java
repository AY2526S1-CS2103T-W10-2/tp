package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.BUDGET_MAX_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.BUDGET_MIN_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.STATUS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BUDGET_MAX_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BUDGET_MIN_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATUS_BOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FilterContactCommand;
import seedu.address.model.contact.BudgetMax;
import seedu.address.model.contact.BudgetMin;
import seedu.address.model.contact.ContactStatus;
import seedu.address.model.contact.Email;
import seedu.address.model.contact.FilterContactPredicate;
import seedu.address.model.contact.Name;
import seedu.address.model.contact.Phone;

public class FilterContactCommandParserTest {

    private final FilterContactCommandParser parser = new FilterContactCommandParser();

    @Test
    public void parse_validArgs_returnsFilterContactCommand() {
        FilterContactCommand expectedCommand =
                new FilterContactCommand(new FilterContactPredicate(
                        Optional.of(Arrays.asList("Alice", "Bob")),
                        Optional.of(Arrays.asList("12345678")),
                        Optional.of(Arrays.asList("alice@example.com")),
                        Optional.empty(),
                        Optional.empty(),
                        Optional.empty(),
                        Optional.empty(),
                        Optional.empty()
                ));

        assertParseSuccess(parser, " "
                + PREFIX_NAME + "Alice Bob "
                + PREFIX_PHONE + "12345678 "
                + PREFIX_EMAIL + "alice@example.com",
                expectedCommand);

        // Test with multiple prefixes and whitespace
        assertParseSuccess(parser, " \n "
                + PREFIX_NAME + "Alice Bob \n "
                + PREFIX_PHONE + "12345678 \t "
                + PREFIX_EMAIL + "alice@example.com \t",
                expectedCommand);
    }

    @Test
    public void parse_invalidPrefix_throwsParseException() {
        assertParseFailure(parser, " abc/foo",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterContactCommand.MESSAGE_USAGE));

        assertParseFailure(parser, PREFIX_NAME + "Bob abc/",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterContactCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_extraPreamble_throwsParseException() {
        assertParseFailure(parser, "extra " + PREFIX_NAME + "Bob",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterContactCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "randomtext",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterContactCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_valuesContainingPrefixLikeStrings_failure() {
        String prefixLikeValue = "abc/like";

        // Name containing prefix-like value
        assertParseFailure(parser,
                NAME_DESC_BOB.replace(VALID_NAME_BOB, prefixLikeValue) + PHONE_DESC_BOB,
                Name.MESSAGE_CONSTRAINTS);

        // Phone containing prefix-like value
        assertParseFailure(parser,
                NAME_DESC_BOB + PHONE_DESC_BOB.replace(VALID_PHONE_BOB, prefixLikeValue),
                Phone.MESSAGE_CONSTRAINTS);

        // Email containing prefix-like value
        assertParseFailure(parser,
                NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB.replace(VALID_EMAIL_BOB, prefixLikeValue),
                Email.MESSAGE_CONSTRAINTS);

        // Budget Min containing prefix-like value
        assertParseFailure(parser,
                NAME_DESC_BOB + PHONE_DESC_BOB
                        + BUDGET_MIN_DESC_BOB.replace(VALID_BUDGET_MIN_BOB, prefixLikeValue),
                BudgetMin.MESSAGE_CONSTRAINTS);
        // Budget Max containing prefix-like value
        assertParseFailure(parser,
                NAME_DESC_BOB + PHONE_DESC_BOB
                        + BUDGET_MAX_DESC_BOB.replace(VALID_BUDGET_MAX_BOB, prefixLikeValue),
                BudgetMax.MESSAGE_CONSTRAINTS);
        // Status containing prefix-like value
        assertParseFailure(parser,
                NAME_DESC_BOB + PHONE_DESC_BOB
                        + STATUS_DESC_BOB.replace(VALID_STATUS_BOB, prefixLikeValue),
                String.format(ContactStatus.MESSAGE_CONSTRAINTS, prefixLikeValue));
    }

    @Test
    public void parse_notesContainingPrefixLikeStrings_success() throws Exception {
        String input = " "
                + PREFIX_NAME + "Alice "
                + PREFIX_PHONE + "12345678 "
                + PREFIX_NOTES + " some/note/with/slashes";

        FilterContactPredicate predicate = new FilterContactPredicate(
                Optional.of(Arrays.asList("Alice")),
                Optional.of(Arrays.asList("12345678")),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.of(Arrays.asList("some/note/with/slashes")),
                Optional.empty()
        );
        FilterContactCommand expectedCommand = new FilterContactCommand(predicate);

        assertParseSuccess(parser, input, expectedCommand);
    }

}
