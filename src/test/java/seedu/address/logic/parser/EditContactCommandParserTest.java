package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_BUYER;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_TENANT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_BUYER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_TENANT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalContacts.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditContactCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.PropertyBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Email;
import seedu.address.model.contact.Name;
import seedu.address.model.contact.Phone;
import seedu.address.model.contact.Tag;
import seedu.address.model.uuid.Uuid;
import seedu.address.testutil.EditContactDescriptorBuilder;

public class EditContactCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditContactCommand.MESSAGE_USAGE);

    private EditContactCommandParser parser = new EditContactCommandParser();
    private Model model = new ModelManager(getTypicalAddressBook(), new PropertyBook(), new UserPrefs());

    @Test
    public void parse_missingParts_failure() {
        // no UUID
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        Contact firstContact = model.getFilteredContactList().get(0);
        assertParseFailure(parser, firstContact.getUuid().toString().replace(" (CONTACT)", ""),
                           EditContactCommand.MESSAGE_NOT_EDITED);

        // no UUID and no fields
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative uuid
        assertParseFailure(parser, "-5" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        Contact firstContact = model.getFilteredContactList().get(0);
        String uuid = firstContact.getUuid().toString().replace(" (CONTACT)", "");

        assertParseFailure(parser, uuid + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, uuid + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, uuid + INVALID_EMAIL_DESC, Email.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, uuid + INVALID_TAG_DESC, String.format(Tag.MESSAGE_CONSTRAINTS, INVALID_TAG));

        // invalid phone followed by valid email
        assertParseFailure(parser, uuid + INVALID_PHONE_DESC + EMAIL_DESC_AMY, Phone.MESSAGE_CONSTRAINTS);

        // invalid + valid tag combinations
        assertParseFailure(parser, uuid + TAG_DESC_BUYER + TAG_DESC_TENANT + INVALID_TAG_DESC,
                                    String.format(Tag.MESSAGE_CONSTRAINTS, INVALID_TAG));
        assertParseFailure(parser, uuid + INVALID_TAG_DESC, String.format(Tag.MESSAGE_CONSTRAINTS, INVALID_TAG));

        // multiple invalid values — first error message expected
        assertParseFailure(parser, uuid + INVALID_NAME_DESC + INVALID_EMAIL_DESC + VALID_ADDRESS_AMY + VALID_PHONE_AMY,
                Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Contact targetContact = model.getFilteredContactList().get(0);
        Integer uuid = Integer.parseInt(targetContact.getUuid().toString().replace(" (CONTACT)", ""));

        String userInput = uuid + PHONE_DESC_BOB + TAG_DESC_TENANT
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + NAME_DESC_AMY + TAG_DESC_BUYER;

        EditContactCommand.EditContactDescriptor descriptor = new EditContactDescriptorBuilder()
                .withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_AMY)
                .withAddress(VALID_ADDRESS_AMY)
                .withTags(VALID_TAG_BUYER, VALID_TAG_TENANT)
                .build();

        EditContactCommand expectedCommand = new EditContactCommand(
                                    new Uuid(uuid, Uuid.StoredItem.CONTACT), descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Contact targetContact = model.getFilteredContactList().get(0);
        Integer uuid = Integer.parseInt(targetContact.getUuid().toString().replace(" (CONTACT)", ""));

        String userInput = uuid + PHONE_DESC_BOB + EMAIL_DESC_AMY;

        EditContactCommand.EditContactDescriptor descriptor = new EditContactDescriptorBuilder()
                .withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_AMY)
                .build();

        EditContactCommand expectedCommand = new EditContactCommand(
                                    new Uuid(uuid, Uuid.StoredItem.CONTACT), descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        Contact targetContact = model.getFilteredContactList().get(0);
        Integer uuid = Integer.parseInt(targetContact.getUuid().toString().replace(" (CONTACT)", ""));

        // name
        String userInput = uuid + NAME_DESC_AMY;
        EditContactCommand.EditContactDescriptor descriptor =
                new EditContactDescriptorBuilder().withName(VALID_NAME_AMY).build();
        assertParseSuccess(parser, userInput,
                new EditContactCommand(new Uuid(uuid, Uuid.StoredItem.CONTACT), descriptor));

        // phone
        userInput = uuid + PHONE_DESC_AMY;
        descriptor = new EditContactDescriptorBuilder().withPhone(VALID_PHONE_AMY).build();
        assertParseSuccess(parser, userInput,
                new EditContactCommand(new Uuid(uuid, Uuid.StoredItem.CONTACT), descriptor));

        // email
        userInput = uuid + EMAIL_DESC_AMY;
        descriptor = new EditContactDescriptorBuilder().withEmail(VALID_EMAIL_AMY).build();
        assertParseSuccess(parser, userInput,
                new EditContactCommand(new Uuid(uuid, Uuid.StoredItem.CONTACT), descriptor));

        // address
        userInput = uuid + ADDRESS_DESC_AMY;
        descriptor = new EditContactDescriptorBuilder().withAddress(VALID_ADDRESS_AMY).build();
        assertParseSuccess(parser, userInput,
                new EditContactCommand(new Uuid(uuid, Uuid.StoredItem.CONTACT), descriptor));

        // tags
        userInput = uuid + TAG_DESC_BUYER;
        descriptor = new EditContactDescriptorBuilder().withTags(VALID_TAG_BUYER).build();
        assertParseSuccess(parser, userInput,
                new EditContactCommand(new Uuid(uuid, Uuid.StoredItem.CONTACT), descriptor));
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        // More extensive testing of duplicate parameter detections is done in
        // AddContactCommandParserTest#parse_repeatedNonTagValue_failure()
        Contact targetContact = model.getFilteredContactList().get(0);
        String uuid = targetContact.getUuid().toString().replace(" (CONTACT)", "");

        // valid followed by invalid
        String userInput = uuid + INVALID_PHONE_DESC + PHONE_DESC_BOB;
        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid followed by valid
        userInput = uuid + PHONE_DESC_BOB + INVALID_PHONE_DESC;
        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // multiple valid fields repeated
        userInput = uuid + PHONE_DESC_AMY + ADDRESS_DESC_AMY + EMAIL_DESC_AMY
                + TAG_DESC_BUYER + PHONE_DESC_AMY + ADDRESS_DESC_AMY + EMAIL_DESC_AMY + TAG_DESC_BUYER
                + PHONE_DESC_BOB + ADDRESS_DESC_BOB + EMAIL_DESC_BOB + TAG_DESC_TENANT;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS));

        // multiple invalid values
        userInput = uuid + INVALID_PHONE_DESC + INVALID_EMAIL_DESC
                + INVALID_PHONE_DESC + INVALID_EMAIL_DESC;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE, PREFIX_EMAIL));
    }


    @Test
    public void parse_resetTags_success() {
        Contact targetContact = model.getFilteredContactList().get(0);
        Integer uuid = Integer.parseInt(targetContact.getUuid().toString().replace(" (CONTACT)", ""));

        String userInput = uuid + TAG_EMPTY;
        EditContactCommand.EditContactDescriptor descriptor = new EditContactDescriptorBuilder().withTags().build();
        EditContactCommand expectedCommand = new EditContactCommand(
                                    new Uuid(uuid, Uuid.StoredItem.CONTACT), descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
