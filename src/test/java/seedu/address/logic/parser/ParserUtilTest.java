package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.PersonAddress;
import seedu.address.model.person.Phone;
import seedu.address.model.property.Bathroom;
import seedu.address.model.property.Bedroom;
import seedu.address.model.property.FloorArea;
import seedu.address.model.property.Listing;
import seedu.address.model.property.Owner;
import seedu.address.model.property.Postal;
import seedu.address.model.property.Price;
import seedu.address.model.property.PropertyAddress;
import seedu.address.model.property.Status;
import seedu.address.model.property.Type;
import seedu.address.model.tag.Tag;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_PROPERTY_ADDRESS = "Main Street";
    private static final String INVALID_POSTAL = "12345";
    private static final String INVALID_PRICE = "0";
    private static final String INVALID_TYPE = "villa";
    private static final String INVALID_STATUS = "pending";
    private static final String INVALID_BEDROOM = "-1";
    private static final String INVALID_BATHROOM = "-1";
    private static final String INVALID_FLOOR_AREA = "49";
    private static final String INVALID_LISTING = "lease";
    private static final String INVALID_OWNER = "owner 1";
    private static final String INVALID_PROPERTY_ID_SHORT = "abc12";
    private static final String INVALID_PROPERTY_ID_CHAR = "abc12#";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";
    private static final String VALID_PROPERTY_ADDRESS = "123 Main St 5";
    private static final String VALID_POSTAL = "123456";
    private static final String VALID_PRICE = "500000";
    private static final String VALID_TYPE = "HDB";
    private static final String VALID_STATUS = "listed";
    private static final String VALID_BEDROOM = "3";
    private static final String VALID_BATHROOM = "2";
    private static final String VALID_FLOOR_AREA = "120";
    private static final String VALID_LISTING = "sale";
    private static final String VALID_OWNER = "owner123";
    private static final String VALID_PROPERTY_ID = "abc123";

    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, ()
            -> ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseName((String) null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(VALID_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parsePhone_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePhone((String) null));
    }

    @Test
    public void parsePhone_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePhone(INVALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithoutWhitespace_returnsPhone() throws Exception {
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(VALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithWhitespace_returnsTrimmedPhone() throws Exception {
        String phoneWithWhitespace = WHITESPACE + VALID_PHONE + WHITESPACE;
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(phoneWithWhitespace));
    }

    @Test
    public void parseAddress_null_returnsEmptyAddress() throws Exception {
        PersonAddress emptyAddress = ParserUtil.parseAddress(null);
        assertEquals(new PersonAddress(""), emptyAddress);
    }

    @Test
    public void parseAddress_blankValue_returnsEmptyAddress() throws Exception {
        PersonAddress emptyAddress = ParserUtil.parseAddress(" ");
        assertEquals(new PersonAddress(""), emptyAddress);
    }

    @Test
    public void parseAddress_validValueWithoutWhitespace_returnsAddress() throws Exception {
        PersonAddress expectedAddress = new PersonAddress(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(VALID_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithWhitespace_returnsTrimmedAddress() throws Exception {
        String addressWithWhitespace = WHITESPACE + VALID_ADDRESS + WHITESPACE;
        PersonAddress expectedAddress = new PersonAddress(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(addressWithWhitespace));
    }

    @Test
    public void parseEmail_null_returnsEmptyEmail() throws Exception {
        Email emptyEmail = ParserUtil.parseEmail(null);
        assertEquals(new Email(""), emptyEmail);
    }

    @Test
    public void parseEmail_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseEmail(INVALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithoutWhitespace_returnsEmail() throws Exception {
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(VALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithWhitespace_returnsTrimmedEmail() throws Exception {
        String emailWithWhitespace = WHITESPACE + VALID_EMAIL + WHITESPACE;
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(emailWithWhitespace));
    }

    @Test
    public void parseTag_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTag(null));
    }

    @Test
    public void parseTag_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTag(INVALID_TAG));
    }

    @Test
    public void parseTag_validValueWithoutWhitespace_returnsTag() throws Exception {
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(VALID_TAG_1));
    }

    @Test
    public void parseTag_validValueWithWhitespace_returnsTrimmedTag() throws Exception {
        String tagWithWhitespace = WHITESPACE + VALID_TAG_1 + WHITESPACE;
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(tagWithWhitespace));
    }

    @Test
    public void parsePropertyId_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePropertyId(null));
    }

    @Test
    public void parsePropertyId_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePropertyId(INVALID_PROPERTY_ID_SHORT));
        assertThrows(ParseException.class, () -> ParserUtil.parsePropertyId(INVALID_PROPERTY_ID_CHAR));
    }

    @Test
    public void parsePropertyId_validValueWithoutWhitespace_returnsId() throws Exception {
        assertEquals(VALID_PROPERTY_ID, ParserUtil.parsePropertyId(VALID_PROPERTY_ID));
    }

    @Test
    public void parsePropertyId_validValueWithWhitespace_returnsTrimmedId() throws Exception {
        String propertyIdWithWhitespace = WHITESPACE + VALID_PROPERTY_ID + WHITESPACE;
        assertEquals(VALID_PROPERTY_ID, ParserUtil.parsePropertyId(propertyIdWithWhitespace));
    }

    @Test
    public void parseTags_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTags(null));
    }

    @Test
    public void parseTags_collectionWithInvalidTags_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, INVALID_TAG)));
    }

    @Test
    public void parseTags_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseTags(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseTags_collectionWithValidTags_returnsTagSet() throws Exception {
        Set<Tag> actualTagSet = ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, VALID_TAG_2));
        Set<Tag> expectedTagSet = new HashSet<Tag>(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));

        assertEquals(expectedTagSet, actualTagSet);
    }

    @Test
    public void parsePropertyAddress_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePropertyAddress(null));
    }

    @Test
    public void parsePropertyAddress_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePropertyAddress(INVALID_PROPERTY_ADDRESS));
    }

    @Test
    public void parsePropertyAddress_validValue_returnsAddress() throws Exception {
        PropertyAddress expectedAddress = new PropertyAddress(VALID_PROPERTY_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parsePropertyAddress(VALID_PROPERTY_ADDRESS));
    }

    @Test
    public void parsePropertyAddress_validValueWithWhitespace_returnsTrimmedAddress() throws Exception {
        String withWhitespace = WHITESPACE + VALID_PROPERTY_ADDRESS + WHITESPACE;
        PropertyAddress expectedAddress = new PropertyAddress(VALID_PROPERTY_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parsePropertyAddress(withWhitespace));
    }

    @Test
    public void parsePostal_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePostal(null));
    }

    @Test
    public void parsePostal_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePostal(INVALID_POSTAL));
    }

    @Test
    public void parsePostal_validValue_returnsPostal() throws Exception {
        Postal expectedPostal = new Postal(VALID_POSTAL);
        assertEquals(expectedPostal, ParserUtil.parsePostal(VALID_POSTAL));
    }

    @Test
    public void parsePostal_validValueWithWhitespace_returnsTrimmedPostal() throws Exception {
        String withWhitespace = WHITESPACE + VALID_POSTAL + WHITESPACE;
        Postal expectedPostal = new Postal(VALID_POSTAL);
        assertEquals(expectedPostal, ParserUtil.parsePostal(withWhitespace));
    }

    @Test
    public void parsePrice_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePrice(null));
    }

    @Test
    public void parsePrice_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePrice(INVALID_PRICE));
    }

    @Test
    public void parsePrice_validValue_returnsPrice() throws Exception {
        Price expectedPrice = new Price(VALID_PRICE);
        assertEquals(expectedPrice, ParserUtil.parsePrice(VALID_PRICE));
    }

    @Test
    public void parsePrice_validValueWithWhitespace_returnsTrimmedPrice() throws Exception {
        String withWhitespace = WHITESPACE + VALID_PRICE + WHITESPACE;
        Price expectedPrice = new Price(VALID_PRICE);
        assertEquals(expectedPrice, ParserUtil.parsePrice(withWhitespace));
    }

    @Test
    public void parseType_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseType(null));
    }

    @Test
    public void parseType_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseType(INVALID_TYPE));
    }

    @Test
    public void parseType_validValue_returnsType() throws Exception {
        Type expectedType = new Type(VALID_TYPE);
        assertEquals(expectedType, ParserUtil.parseType(VALID_TYPE));
    }

    @Test
    public void parseType_validValueWithWhitespace_returnsTrimmedType() throws Exception {
        String withWhitespace = WHITESPACE + VALID_TYPE + WHITESPACE;
        Type expectedType = new Type(VALID_TYPE);
        assertEquals(expectedType, ParserUtil.parseType(withWhitespace));
    }

    @Test
    public void parseStatus_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseStatus(null));
    }

    @Test
    public void parseStatus_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseStatus(INVALID_STATUS));
    }

    @Test
    public void parseStatus_validValue_returnsStatus() throws Exception {
        Status expectedStatus = new Status(VALID_STATUS);
        assertEquals(expectedStatus, ParserUtil.parseStatus(VALID_STATUS));
    }

    @Test
    public void parseStatus_validValueWithWhitespace_returnsTrimmedStatus() throws Exception {
        String withWhitespace = WHITESPACE + VALID_STATUS + WHITESPACE;
        Status expectedStatus = new Status(VALID_STATUS);
        assertEquals(expectedStatus, ParserUtil.parseStatus(withWhitespace));
    }

    @Test
    public void parseBedroom_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseBedroom(null));
    }

    @Test
    public void parseBedroom_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseBedroom(INVALID_BEDROOM));
    }

    @Test
    public void parseBedroom_validValue_returnsBedroom() throws Exception {
        Bedroom expectedBedroom = new Bedroom(VALID_BEDROOM);
        assertEquals(expectedBedroom, ParserUtil.parseBedroom(VALID_BEDROOM));
    }

    @Test
    public void parseBedroom_validValueWithWhitespace_returnsTrimmedBedroom() throws Exception {
        String withWhitespace = WHITESPACE + VALID_BEDROOM + WHITESPACE;
        Bedroom expectedBedroom = new Bedroom(VALID_BEDROOM);
        assertEquals(expectedBedroom, ParserUtil.parseBedroom(withWhitespace));
    }

    @Test
    public void parseBathroom_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseBathroom(null));
    }

    @Test
    public void parseBathroom_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseBathroom(INVALID_BATHROOM));
    }

    @Test
    public void parseBathroom_validValue_returnsBathroom() throws Exception {
        Bathroom expectedBathroom = new Bathroom(VALID_BATHROOM);
        assertEquals(expectedBathroom, ParserUtil.parseBathroom(VALID_BATHROOM));
    }

    @Test
    public void parseBathroom_validValueWithWhitespace_returnsTrimmedBathroom() throws Exception {
        String withWhitespace = WHITESPACE + VALID_BATHROOM + WHITESPACE;
        Bathroom expectedBathroom = new Bathroom(VALID_BATHROOM);
        assertEquals(expectedBathroom, ParserUtil.parseBathroom(withWhitespace));
    }

    @Test
    public void parseFloorArea_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseFloorArea(null));
    }

    @Test
    public void parseFloorArea_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseFloorArea(INVALID_FLOOR_AREA));
    }

    @Test
    public void parseFloorArea_validValue_returnsFloorArea() throws Exception {
        FloorArea expectedFloorArea = new FloorArea(VALID_FLOOR_AREA);
        assertEquals(expectedFloorArea, ParserUtil.parseFloorArea(VALID_FLOOR_AREA));
    }

    @Test
    public void parseFloorArea_validValueWithWhitespace_returnsTrimmedFloorArea() throws Exception {
        String withWhitespace = WHITESPACE + VALID_FLOOR_AREA + WHITESPACE;
        FloorArea expectedFloorArea = new FloorArea(VALID_FLOOR_AREA);
        assertEquals(expectedFloorArea, ParserUtil.parseFloorArea(withWhitespace));
    }

    @Test
    public void parseListing_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseListing(null));
    }

    @Test
    public void parseListing_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseListing(INVALID_LISTING));
    }

    @Test
    public void parseListing_validValue_returnsListing() throws Exception {
        Listing expectedListing = new Listing(VALID_LISTING);
        assertEquals(expectedListing, ParserUtil.parseListing(VALID_LISTING));
    }

    @Test
    public void parseListing_validValueWithWhitespace_returnsTrimmedListing() throws Exception {
        String withWhitespace = WHITESPACE + VALID_LISTING + WHITESPACE;
        Listing expectedListing = new Listing(VALID_LISTING);
        assertEquals(expectedListing, ParserUtil.parseListing(withWhitespace));
    }

    @Test
    public void parseOwner_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseOwner(null));
    }

    @Test
    public void parseOwner_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseOwner(INVALID_OWNER));
    }

    @Test
    public void parseOwner_validValue_returnsOwner() throws Exception {
        Owner expectedOwner = new Owner(VALID_OWNER);
        assertEquals(expectedOwner, ParserUtil.parseOwner(VALID_OWNER));
    }

    @Test
    public void parseOwner_validValueWithWhitespace_returnsTrimmedOwner() throws Exception {
        String withWhitespace = WHITESPACE + VALID_OWNER + WHITESPACE;
        Owner expectedOwner = new Owner(VALID_OWNER);
        assertEquals(expectedOwner, ParserUtil.parseOwner(withWhitespace));
    }
}
