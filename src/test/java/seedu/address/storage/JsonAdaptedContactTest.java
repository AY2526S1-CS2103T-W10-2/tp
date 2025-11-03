package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedContact.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalContacts.BENSON;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.contact.ContactAddress;
import seedu.address.model.contact.ContactStatus;
import seedu.address.model.contact.Email;
import seedu.address.model.contact.Name;
import seedu.address.model.contact.Phone;
import seedu.address.model.uuid.Uuid;

public class JsonAdaptedContactTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_BUDGETMIN = "-1";
    private static final String INVALID_BUDGETMAX = "-1";
    private static final String INVALID_STATUS = "abc";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = "test@gmail.com";
    private static final String VALID_BUDGETMIN = BENSON.getBudgetMin().toString();
    private static final String VALID_BUDGETMAX = BENSON.getBudgetMax().toString();
    private static final String VALID_NOTES = BENSON.getNotes().toString();
    private static final String VALID_STATUS = BENSON.getStatus().toString();
    private static final List<Integer> VALID_BUYING_PROPERTY_IDS =
            BENSON.getBuyingPropertyIds().stream().map(Uuid::getValue).toList();
    private static final List<Integer> VALID_SELLING_PROPERTY_IDS =
            BENSON.getSellingPropertyIds().stream().map(Uuid::getValue).toList();

    @Test
    public void toModelType_validContactDetails_returnsContact() throws Exception {
        JsonAdaptedContact contact = new JsonAdaptedContact(BENSON);
        assertEquals(BENSON, contact.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedContact contact =
                new JsonAdaptedContact(1, INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                                        VALID_BUDGETMIN, VALID_BUDGETMAX,
                                        VALID_NOTES, VALID_STATUS, VALID_BUYING_PROPERTY_IDS,
                                        VALID_SELLING_PROPERTY_IDS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, contact::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedContact contact = new JsonAdaptedContact(1, null, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                                        VALID_BUDGETMIN, VALID_BUDGETMAX,
                                        VALID_NOTES, VALID_STATUS, VALID_BUYING_PROPERTY_IDS,
                                        VALID_SELLING_PROPERTY_IDS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, contact::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedContact contact =
                new JsonAdaptedContact(1, VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_BUDGETMIN, VALID_BUDGETMAX, VALID_NOTES, VALID_STATUS,
                        VALID_BUYING_PROPERTY_IDS, VALID_SELLING_PROPERTY_IDS);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, contact::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedContact contact =
                new JsonAdaptedContact(1, VALID_NAME, null, VALID_EMAIL, VALID_ADDRESS,
                        VALID_BUDGETMIN, VALID_BUDGETMAX, VALID_NOTES, VALID_STATUS,
                        VALID_BUYING_PROPERTY_IDS, VALID_SELLING_PROPERTY_IDS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, contact::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedContact contact =
                new JsonAdaptedContact(1, VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_ADDRESS,
                        VALID_BUDGETMIN, VALID_BUDGETMAX, VALID_NOTES, VALID_STATUS,
                        VALID_BUYING_PROPERTY_IDS, VALID_SELLING_PROPERTY_IDS);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, contact::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedContact contact =
                new JsonAdaptedContact(1, VALID_NAME, VALID_PHONE, null, VALID_ADDRESS,
                        VALID_BUDGETMIN, VALID_BUDGETMAX, VALID_NOTES, VALID_STATUS,
                        VALID_BUYING_PROPERTY_IDS, VALID_SELLING_PROPERTY_IDS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, contact::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedContact contact =
                new JsonAdaptedContact(1, VALID_NAME, VALID_PHONE, VALID_EMAIL, INVALID_ADDRESS,
                        VALID_BUDGETMIN, VALID_BUDGETMAX, VALID_NOTES, VALID_STATUS,
                        VALID_BUYING_PROPERTY_IDS, VALID_SELLING_PROPERTY_IDS);
        String expectedMessage = ContactAddress.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, contact::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedContact contact =
                new JsonAdaptedContact(1, VALID_NAME, VALID_PHONE, VALID_EMAIL, null,
                        VALID_BUDGETMIN, VALID_BUDGETMAX, VALID_NOTES, VALID_STATUS,
                        VALID_BUYING_PROPERTY_IDS, VALID_SELLING_PROPERTY_IDS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, ContactAddress.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, contact::toModelType);
    }

    @Test
    public void toModelType_invalidBudgetMin_throwsIllegalValueException() {
        JsonAdaptedContact contact =
                new JsonAdaptedContact(1, VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        INVALID_BUDGETMIN, VALID_BUDGETMAX, VALID_NOTES, VALID_STATUS,
                        VALID_BUYING_PROPERTY_IDS, VALID_SELLING_PROPERTY_IDS);
        assertThrows(IllegalValueException.class, contact::toModelType);
    }

    @Test
    public void toModelType_nullBudgetMin_throwsIllegalValueException() {
        JsonAdaptedContact contact =
                new JsonAdaptedContact(1, VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        null, VALID_BUDGETMAX, VALID_NOTES, VALID_STATUS,
                        VALID_BUYING_PROPERTY_IDS, VALID_SELLING_PROPERTY_IDS);
        assertThrows(IllegalValueException.class, contact::toModelType);
    }

    @Test
    public void toModelType_invalidBudgetMax_throwsIllegalValueException() {
        JsonAdaptedContact contact =
                new JsonAdaptedContact(1, VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_BUDGETMIN, INVALID_BUDGETMAX, VALID_NOTES, VALID_STATUS,
                        VALID_BUYING_PROPERTY_IDS, VALID_SELLING_PROPERTY_IDS);
        assertThrows(IllegalValueException.class, contact::toModelType);
    }

    @Test
    public void toModelType_nullBudgetMax_throwsIllegalValueException() {
        JsonAdaptedContact contact =
                new JsonAdaptedContact(1, VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_BUDGETMIN, null, VALID_NOTES, VALID_STATUS,
                        VALID_BUYING_PROPERTY_IDS, VALID_SELLING_PROPERTY_IDS);
        assertThrows(IllegalValueException.class, contact::toModelType);
    }

    @Test
    public void toModelType_nullNotes_throwsIllegalValueException() {
        JsonAdaptedContact contact =
                new JsonAdaptedContact(1, VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_BUDGETMIN, VALID_BUDGETMAX, null, VALID_STATUS,
                        VALID_BUYING_PROPERTY_IDS, VALID_SELLING_PROPERTY_IDS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "Notes");
        assertThrows(IllegalValueException.class, expectedMessage, contact::toModelType);
    }

    @Test
    public void toModelType_nullStatus_throwsIllegalValueException() {
        JsonAdaptedContact contact =
                new JsonAdaptedContact(1, VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_BUDGETMIN, VALID_BUDGETMAX, VALID_NOTES, null,
                        VALID_BUYING_PROPERTY_IDS, VALID_SELLING_PROPERTY_IDS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, ContactStatus.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, contact::toModelType);
    }

    @Test
    public void toModelType_invalidStatus_throwsIllegalValueException() {
        JsonAdaptedContact contact =
                new JsonAdaptedContact(1, VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_BUDGETMIN, VALID_BUDGETMAX, VALID_NOTES, INVALID_STATUS,
                        VALID_BUYING_PROPERTY_IDS, VALID_SELLING_PROPERTY_IDS);
        assertThrows(IllegalValueException.class, contact::toModelType);
    }

}
