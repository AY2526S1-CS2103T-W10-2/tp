package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedProperty.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
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

class JsonAdaptedPropertyTest {

    private static final Property VALID_PROPERTY = new Property(
            new PropertyAddress("123 Main St 5"), new Bathroom("2"), new Bedroom("3"),
            new FloorArea("120"), new Listing("sale"), new Postal("123456"), new Price("500000"),
            new Status("sold"), new Type("HDB"), new Owner("owner123"));

    private static final String VALID_ID = VALID_PROPERTY.getId();
    private static final String VALID_ADDRESS = VALID_PROPERTY.getPropertyAddress().value;
    private static final String VALID_BATHROOM = VALID_PROPERTY.getBathroom().value;
    private static final String VALID_BEDROOM = VALID_PROPERTY.getBedroom().value;
    private static final String VALID_FLOOR_AREA = VALID_PROPERTY.getFloorArea().value;
    private static final String VALID_LISTING = VALID_PROPERTY.getListing().value;
    private static final String VALID_POSTAL = VALID_PROPERTY.getPostal().value;
    private static final String VALID_PRICE = VALID_PROPERTY.getPrice().value;
    private static final String VALID_STATUS = VALID_PROPERTY.getStatus().value;
    private static final String VALID_TYPE = VALID_PROPERTY.getType().value;
    private static final String VALID_OWNER = VALID_PROPERTY.getOwner().value;

    private static final String INVALID_ADDRESS = "Main Street"; // Missing digit
    private static final String INVALID_BATHROOM = "21"; // Above permitted range
    private static final String INVALID_BEDROOM = "-1"; // Below permitted range
    private static final String INVALID_FLOOR_AREA = "49"; // Below minimum
    private static final String INVALID_LISTING = "lease"; // Not an allowed value
    private static final String INVALID_POSTAL = "12345"; // Not 6 digits
    private static final String INVALID_PRICE = "0"; // Not positive
    private static final String INVALID_STATUS = "pending"; // Not an allowed value
    private static final String INVALID_TYPE = "castle"; // Not an allowed value
    private static final String INVALID_OWNER = "owner 123"; // Contains space

    @Test
    void toModelType_validPropertyDetails_returnsProperty() throws Exception {
        JsonAdaptedProperty property = new JsonAdaptedProperty(VALID_PROPERTY);
        assertEquals(VALID_PROPERTY, property.toModelType());
    }

    @Test
    void toModelType_nullId_throwsIllegalValueException() {
        JsonAdaptedProperty property = new JsonAdaptedProperty(null, VALID_ADDRESS, VALID_BATHROOM, VALID_BEDROOM,
                VALID_FLOOR_AREA, VALID_LISTING, VALID_POSTAL, VALID_PRICE, VALID_STATUS, VALID_TYPE, VALID_OWNER);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Property.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, property::toModelType);
    }

    @Test
    void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedProperty property = new JsonAdaptedProperty(VALID_ID, INVALID_ADDRESS, VALID_BATHROOM,
                VALID_BEDROOM, VALID_FLOOR_AREA, VALID_LISTING, VALID_POSTAL, VALID_PRICE, VALID_STATUS,
                VALID_TYPE, VALID_OWNER);
        String expectedMessage = PropertyAddress.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, property::toModelType);
    }

    @Test
    void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedProperty property = new JsonAdaptedProperty(VALID_ID, null, VALID_BATHROOM, VALID_BEDROOM,
                VALID_FLOOR_AREA, VALID_LISTING, VALID_POSTAL, VALID_PRICE, VALID_STATUS, VALID_TYPE, VALID_OWNER);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, PropertyAddress.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, property::toModelType);
    }

    @Test
    void toModelType_invalidBathroom_throwsIllegalValueException() {
        JsonAdaptedProperty property = new JsonAdaptedProperty(VALID_ID, VALID_ADDRESS, INVALID_BATHROOM,
                VALID_BEDROOM, VALID_FLOOR_AREA, VALID_LISTING, VALID_POSTAL, VALID_PRICE, VALID_STATUS,
                VALID_TYPE, VALID_OWNER);
        String expectedMessage = Bathroom.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, property::toModelType);
    }

    @Test
    void toModelType_nullBathroom_throwsIllegalValueException() {
        JsonAdaptedProperty property = new JsonAdaptedProperty(VALID_ID, VALID_ADDRESS, null,
                VALID_BEDROOM, VALID_FLOOR_AREA, VALID_LISTING, VALID_POSTAL, VALID_PRICE, VALID_STATUS,
                VALID_TYPE, VALID_OWNER);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Bathroom.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, property::toModelType);
    }

    @Test
    void toModelType_invalidBedroom_throwsIllegalValueException() {
        JsonAdaptedProperty property = new JsonAdaptedProperty(VALID_ID, VALID_ADDRESS, VALID_BATHROOM,
                INVALID_BEDROOM, VALID_FLOOR_AREA, VALID_LISTING, VALID_POSTAL, VALID_PRICE, VALID_STATUS,
                VALID_TYPE, VALID_OWNER);
        String expectedMessage = Bedroom.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, property::toModelType);
    }

    @Test
    void toModelType_nullBedroom_throwsIllegalValueException() {
        JsonAdaptedProperty property = new JsonAdaptedProperty(VALID_ID, VALID_ADDRESS, VALID_BATHROOM,
                null, VALID_FLOOR_AREA, VALID_LISTING, VALID_POSTAL, VALID_PRICE, VALID_STATUS,
                VALID_TYPE, VALID_OWNER);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Bedroom.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, property::toModelType);
    }

    @Test
    void toModelType_invalidFloorArea_throwsIllegalValueException() {
        JsonAdaptedProperty property = new JsonAdaptedProperty(VALID_ID, VALID_ADDRESS, VALID_BATHROOM,
                VALID_BEDROOM, INVALID_FLOOR_AREA, VALID_LISTING, VALID_POSTAL, VALID_PRICE, VALID_STATUS,
                VALID_TYPE, VALID_OWNER);
        String expectedMessage = FloorArea.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, property::toModelType);
    }

    @Test
    void toModelType_nullFloorArea_throwsIllegalValueException() {
        JsonAdaptedProperty property = new JsonAdaptedProperty(VALID_ID, VALID_ADDRESS, VALID_BATHROOM,
                VALID_BEDROOM, null, VALID_LISTING, VALID_POSTAL, VALID_PRICE, VALID_STATUS, VALID_TYPE, VALID_OWNER);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, FloorArea.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, property::toModelType);
    }

    @Test
    void toModelType_invalidListing_throwsIllegalValueException() {
        JsonAdaptedProperty property = new JsonAdaptedProperty(VALID_ID, VALID_ADDRESS, VALID_BATHROOM,
                VALID_BEDROOM, VALID_FLOOR_AREA, INVALID_LISTING, VALID_POSTAL, VALID_PRICE, VALID_STATUS,
                VALID_TYPE, VALID_OWNER);
        String expectedMessage = Listing.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, property::toModelType);
    }

    @Test
    void toModelType_nullListing_throwsIllegalValueException() {
        JsonAdaptedProperty property = new JsonAdaptedProperty(VALID_ID, VALID_ADDRESS, VALID_BATHROOM,
                VALID_BEDROOM, VALID_FLOOR_AREA, null, VALID_POSTAL, VALID_PRICE, VALID_STATUS, VALID_TYPE,
                VALID_OWNER);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Listing.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, property::toModelType);
    }

    @Test
    void toModelType_invalidPostal_throwsIllegalValueException() {
        JsonAdaptedProperty property = new JsonAdaptedProperty(VALID_ID, VALID_ADDRESS, VALID_BATHROOM,
                VALID_BEDROOM, VALID_FLOOR_AREA, VALID_LISTING, INVALID_POSTAL, VALID_PRICE, VALID_STATUS,
                VALID_TYPE, VALID_OWNER);
        String expectedMessage = Postal.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, property::toModelType);
    }

    @Test
    void toModelType_nullPostal_throwsIllegalValueException() {
        JsonAdaptedProperty property = new JsonAdaptedProperty(VALID_ID, VALID_ADDRESS, VALID_BATHROOM,
                VALID_BEDROOM, VALID_FLOOR_AREA, VALID_LISTING, null, VALID_PRICE, VALID_STATUS, VALID_TYPE,
                VALID_OWNER);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Postal.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, property::toModelType);
    }

    @Test
    void toModelType_invalidPrice_throwsIllegalValueException() {
        JsonAdaptedProperty property = new JsonAdaptedProperty(VALID_ID, VALID_ADDRESS, VALID_BATHROOM,
                VALID_BEDROOM, VALID_FLOOR_AREA, VALID_LISTING, VALID_POSTAL, INVALID_PRICE, VALID_STATUS,
                VALID_TYPE, VALID_OWNER);
        String expectedMessage = Price.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, property::toModelType);
    }

    @Test
    void toModelType_nullPrice_throwsIllegalValueException() {
        JsonAdaptedProperty property = new JsonAdaptedProperty(VALID_ID, VALID_ADDRESS, VALID_BATHROOM,
                VALID_BEDROOM, VALID_FLOOR_AREA, VALID_LISTING, VALID_POSTAL, null, VALID_STATUS, VALID_TYPE,
                VALID_OWNER);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Price.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, property::toModelType);
    }

    @Test
    void toModelType_invalidStatus_throwsIllegalValueException() {
        JsonAdaptedProperty property = new JsonAdaptedProperty(VALID_ID, VALID_ADDRESS, VALID_BATHROOM,
                VALID_BEDROOM, VALID_FLOOR_AREA, VALID_LISTING, VALID_POSTAL, VALID_PRICE, INVALID_STATUS,
                VALID_TYPE, VALID_OWNER);
        String expectedMessage = Status.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, property::toModelType);
    }

    @Test
    void toModelType_nullStatus_throwsIllegalValueException() {
        JsonAdaptedProperty property = new JsonAdaptedProperty(VALID_ID, VALID_ADDRESS, VALID_BATHROOM,
                VALID_BEDROOM, VALID_FLOOR_AREA, VALID_LISTING, VALID_POSTAL, VALID_PRICE, null,
                VALID_TYPE, VALID_OWNER);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Status.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, property::toModelType);
    }

    @Test
    void toModelType_invalidType_throwsIllegalValueException() {
        JsonAdaptedProperty property = new JsonAdaptedProperty(VALID_ID, VALID_ADDRESS, VALID_BATHROOM,
                VALID_BEDROOM, VALID_FLOOR_AREA, VALID_LISTING, VALID_POSTAL, VALID_PRICE, VALID_STATUS,
                INVALID_TYPE, VALID_OWNER);
        String expectedMessage = Type.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, property::toModelType);
    }

    @Test
    void toModelType_nullType_throwsIllegalValueException() {
        JsonAdaptedProperty property = new JsonAdaptedProperty(VALID_ID, VALID_ADDRESS, VALID_BATHROOM,
                VALID_BEDROOM, VALID_FLOOR_AREA, VALID_LISTING, VALID_POSTAL, VALID_PRICE, VALID_STATUS,
                null, VALID_OWNER);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Type.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, property::toModelType);
    }

    @Test
    void toModelType_invalidOwner_throwsIllegalValueException() {
        JsonAdaptedProperty property = new JsonAdaptedProperty(VALID_ID, VALID_ADDRESS, VALID_BATHROOM,
                VALID_BEDROOM, VALID_FLOOR_AREA, VALID_LISTING, VALID_POSTAL, VALID_PRICE, VALID_STATUS,
                VALID_TYPE, INVALID_OWNER);
        String expectedMessage = Owner.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, property::toModelType);
    }

    @Test
    void toModelType_nullOwner_throwsIllegalValueException() {
        JsonAdaptedProperty property = new JsonAdaptedProperty(VALID_ID, VALID_ADDRESS, VALID_BATHROOM,
                VALID_BEDROOM, VALID_FLOOR_AREA, VALID_LISTING, VALID_POSTAL, VALID_PRICE, VALID_STATUS,
                VALID_TYPE, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Owner.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, property::toModelType);
    }
}
