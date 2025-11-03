package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.BUDGET_MAX_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.BUDGET_MIN_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.STATUS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BUDGET_MAX_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BUDGET_MIN_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATUS_BOB;
import static seedu.address.logic.parser.AddContactCommandParser.NAME_AND_PHONE_MISSING;
import static seedu.address.logic.parser.AddContactCommandParser.NAME_MISSING;
import static seedu.address.logic.parser.AddContactCommandParser.PHONE_MISSING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BUDGET_MAX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BUDGET_MIN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalContacts.AMY;
import static seedu.address.testutil.TypicalContacts.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddContactCommand;
import seedu.address.model.contact.BudgetMax;
import seedu.address.model.contact.BudgetMin;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.ContactStatus;
import seedu.address.model.contact.Email;
import seedu.address.model.contact.Name;
import seedu.address.model.contact.Phone;
import seedu.address.testutil.ContactBuilderUtil;

public class AddContactCommandParserTest {
    private AddContactCommandParser parser = new AddContactCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Contact expectedContact = new ContactBuilderUtil(BOB).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB, new AddContactCommand(expectedContact));
    }

    @Test
    public void parse_repeatedNonTagValue_failure() {
        String validExpectedContactString = NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB;

        // multiple names
        assertParseFailure(parser, NAME_DESC_AMY + validExpectedContactString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple phones
        assertParseFailure(parser, PHONE_DESC_AMY + validExpectedContactString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // multiple emails
        assertParseFailure(parser, EMAIL_DESC_AMY + validExpectedContactString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // multiple addresses
        assertParseFailure(parser, ADDRESS_DESC_AMY + validExpectedContactString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // multiple fields repeated
        assertParseFailure(parser,
                validExpectedContactString + PHONE_DESC_AMY + EMAIL_DESC_AMY + NAME_DESC_AMY + ADDRESS_DESC_AMY
                        + validExpectedContactString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_ADDRESS, PREFIX_EMAIL, PREFIX_PHONE));

        // invalid value followed by valid value

        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + validExpectedContactString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, INVALID_EMAIL_DESC + validExpectedContactString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid phone
        assertParseFailure(parser, INVALID_PHONE_DESC + validExpectedContactString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // valid value followed by invalid value

        // invalid name
        assertParseFailure(parser, validExpectedContactString + INVALID_NAME_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, validExpectedContactString + INVALID_EMAIL_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid phone
        assertParseFailure(parser, validExpectedContactString + INVALID_PHONE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Contact expectedContact = new ContactBuilderUtil(AMY)
                .withEmail("")
                .withAddress("")
                .withBudgetMin("0")
                .withBudgetMax("200000000000")
                .withNotes("")
                .withStatus("Active")
                .build();
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY,
                new AddContactCommand(expectedContact));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddContactCommand.MESSAGE_USAGE);
        String expectedMissingName = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                "Name parameter (" + PREFIX_NAME + "NAME) is missing.\n" + AddContactCommand.MESSAGE_USAGE);
        String expectedMissingPhone = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                "Phone parameter (" + PREFIX_PHONE + "PHONE) is missing.\n" + AddContactCommand.MESSAGE_USAGE);

        // missing name prefix -> should fail
        assertParseFailure(parser, PHONE_DESC_BOB + VALID_NAME_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB,
                expectedMissingName);

        // missing phone prefix -> should fail
        assertParseFailure(parser, NAME_DESC_BOB + VALID_PHONE_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB,
                expectedMissingPhone);

        // missing email prefix -> should succeed, so test for success instead
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + ADDRESS_DESC_BOB,
                new AddContactCommand(new ContactBuilderUtil()
                        .withName(VALID_NAME_BOB)
                        .withPhone(VALID_PHONE_BOB)
                        .withEmail("")
                        .withAddress(VALID_ADDRESS_BOB)
                        .withBudgetMin("0")
                        .withBudgetMax("200000000000")
                        .withNotes("")
                        .withStatus("")
                        .build()));

        // missing address prefix -> should succeed, so test for success instead
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB,
                new AddContactCommand(new ContactBuilderUtil()
                        .withName(VALID_NAME_BOB)
                        .withPhone(VALID_PHONE_BOB)
                        .withEmail(VALID_EMAIL_BOB)
                        .withAddress("")// default empty address for missing prefix
                        .build()));

        // all prefixes missing -> should fail (name & phone missing)
        assertParseFailure(parser, VALID_NAME_BOB + VALID_PHONE_BOB + VALID_EMAIL_BOB + VALID_ADDRESS_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB,
                Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB + ADDRESS_DESC_BOB,
                Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC + ADDRESS_DESC_BOB,
                Email.MESSAGE_CONSTRAINTS);

        // one invalid value
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddContactCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_budgetMaxLessThanBudgetMin_failure() {
        String input = NAME_DESC_BOB + PHONE_DESC_BOB
                + " " + PREFIX_BUDGET_MIN + "5000"
                + " " + PREFIX_BUDGET_MAX + "1000"; // max < min
        System.out.println(input);
        assertParseFailure(parser, input, "Budget maximum cannot be lesser than budget minimum.");
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
        // Notes can contain slashes, no exception expected
        String notesWithPrefixLike = "some/note/with/slashes";
        Contact expectedContact = new ContactBuilderUtil(BOB).withNotes("some/note/with/slashes").build();

        assertParseSuccess(parser,
                NAME_DESC_BOB + PHONE_DESC_BOB + " " + PREFIX_NOTES + notesWithPrefixLike,
                new AddContactCommand(expectedContact));
    }

    @Test
    public void validateRequiredPrefixesPresent_missingBothNameAndPhone_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                NAME_AND_PHONE_MISSING + AddContactCommand.MESSAGE_USAGE);

        // Has email but missing both name and phone
        assertParseFailure(parser, EMAIL_DESC_BOB + ADDRESS_DESC_BOB, expectedMessage);
    }

    @Test
    public void validateRequiredPrefixesPresent_missingNameOnly_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                NAME_MISSING + AddContactCommand.MESSAGE_USAGE);

        // Has phone but missing name
        assertParseFailure(parser, PHONE_DESC_BOB + EMAIL_DESC_BOB, expectedMessage);
    }

    @Test
    public void validateRequiredPrefixesPresent_missingPhoneOnly_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                PHONE_MISSING + AddContactCommand.MESSAGE_USAGE);

        // Has name but missing phone
        assertParseFailure(parser, NAME_DESC_BOB + EMAIL_DESC_BOB, expectedMessage);
    }

    @Test
    public void validateRequiredPrefixesPresent_noParameters_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddContactCommand.MESSAGE_USAGE);

        // Empty input (no parameters at all)
        assertParseFailure(parser, "", expectedMessage);

        // Only whitespace
        assertParseFailure(parser, "   ", expectedMessage);
    }
}
