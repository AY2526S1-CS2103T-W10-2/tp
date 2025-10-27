package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.BUDGET_MAX_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.BUDGET_MIN_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.STATUS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BUDGET_MAX_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BUDGET_MIN_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATUS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_BUYER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTES;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FilterContactCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.contact.BudgetMax;
import seedu.address.model.contact.BudgetMin;
import seedu.address.model.contact.ContactStatus;
import seedu.address.model.contact.Email;
import seedu.address.model.contact.FilterContactPredicate;
import seedu.address.model.contact.Name;
import seedu.address.model.contact.Phone;
import seedu.address.model.contact.Tag;

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
                        Optional.empty(),
                        Optional.empty(),
                        Optional.empty(), Optional.empty()
                ));

        assertParseSuccess(parser, " n/Alice Bob p/12345678 e/alice@example.com", expectedCommand);

        // Test with multiple prefixes and whitespace
        assertParseSuccess(parser, " \n n/Alice Bob \n p/12345678 \t e/alice@example.com \t", expectedCommand);
    }

    @Test
    public void parse_invalidPrefix_throwsParseException() {
        assertParseFailure(parser, " abc/foo",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterContactCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "n/Bob abc/",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterContactCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_extraPreamble_throwsParseException() {
        assertParseFailure(parser, "extra n/Bob",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterContactCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "randomtext",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterContactCommand.MESSAGE_USAGE));
    }

    @Test
    public void parseLimit_validValues_success() throws Exception {
        FilterContactCommandParser parser = new FilterContactCommandParser();

        Optional<Integer> result = parser.parseLimit(Optional.of("5"));
        assertTrue(result.isPresent());
        assertEquals(5, result.get());
    }

    @Test
    public void parsePositiveInteger_invalidValues_throwsParseException() {
        FilterContactCommandParser parser = new FilterContactCommandParser();

        assertThrows(ParseException.class, () -> parser.parseLimit(Optional.of("0")));
        assertThrows(ParseException.class, () -> parser.parseLimit(Optional.of("-2")));
        assertThrows(ParseException.class, () -> parser.parseLimit(Optional.of("abc")));
    }

    @Test
    public void parseOffset_validValues_success() throws Exception {
        FilterContactCommandParser parser = new FilterContactCommandParser();

        Optional<Integer> zero = parser.parseOffset(Optional.of("0"));
        Optional<Integer> positive = parser.parseOffset(Optional.of("10"));

        assertTrue(zero.isPresent());
        assertEquals(0, zero.get());
        assertTrue(positive.isPresent());
        assertEquals(10, positive.get());
    }

    @Test
    public void parseNonNegativeInteger_invalidValues_throwsParseException() {
        FilterContactCommandParser parser = new FilterContactCommandParser();

        assertThrows(ParseException.class, () -> parser.parseOffset(Optional.of("-1")));
        assertThrows(ParseException.class, () -> parser.parseOffset(Optional.of("abc")));
    }

    @Test
    public void parse_withValidLimitAndOffset_success() {
        FilterContactCommandParser parser = new FilterContactCommandParser();

        FilterContactPredicate predicate = new FilterContactPredicate(
                Optional.of(Arrays.asList("Alice")),
                Optional.empty(), Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty(),
                Optional.of(5), Optional.of(10)
        );

        FilterContactCommand expectedCommand = new FilterContactCommand(predicate);
        assertParseSuccess(parser, " n/Alice limit/5 offset/10", expectedCommand);
    }

    @Test
    public void parse_withInvalidLimitOrOffset_throwsParseException() {
        FilterContactCommandParser parser = new FilterContactCommandParser();

        // invalid limit
        assertParseFailure(parser, " limit/0", "Limit must be greater than 0.");
        assertParseFailure(parser, " limit/-1", "Limit must be greater than 0.");
        assertParseFailure(parser, " limit/abc", "Invalid number for limit: Optional[abc]");

        // invalid offset
        assertParseFailure(parser, " offset/-5", "Offset cannot be negative.");
        assertParseFailure(parser, " offset/xyz", "Invalid number for offset: Optional[xyz]");
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

        // Tag containing prefix-like value
        assertParseFailure(parser,
                NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + TAG_DESC_BOB.replace(VALID_TAG_BUYER, prefixLikeValue),
                String.format(Tag.MESSAGE_CONSTRAINTS, prefixLikeValue));
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
        String input = " n/Alice p/12345678 " + PREFIX_NOTES + " some/note/with/slashes";

        FilterContactPredicate predicate = new FilterContactPredicate(
                Optional.of(Arrays.asList("Alice")),
                Optional.of(Arrays.asList("12345678")),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.of(Arrays.asList("some/note/with/slashes")),
                Optional.empty(),
                Optional.empty(),
                Optional.empty()
        );
        FilterContactCommand expectedCommand = new FilterContactCommand(predicate);

        assertParseSuccess(parser, input, expectedCommand);
    }

}
