package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.uuid.Uuid.StoredItem.CONTACT;
import static seedu.address.testutil.TypicalContacts.getTypicalAddressBook;
import static seedu.address.testutil.TypicalProperties.getTypicalPropertyBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.property.Property;
import seedu.address.model.uuid.Uuid;
import seedu.address.testutil.PropertyBuilderUtil;

public class ShowPropertiesCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalPropertyBook(), new UserPrefs());

    @Test
    public void execute_validUuid_success() throws Exception {
        Uuid validUuid = new Uuid(1, CONTACT);
        ShowPropertiesCommand command = new ShowPropertiesCommand(validUuid);

        CommandResult result = command.execute(model);

        assertTrue(result.getFeedbackToUser().contains("Listed")
                || result.getFeedbackToUser().contains("No properties found"));
    }

    @Test
    public void execute_noPropertiesFound_showsMessage() throws Exception {
        Uuid uuidWithNoProperties = new Uuid(999, CONTACT);
        ShowPropertiesCommand command = new ShowPropertiesCommand(uuidWithNoProperties);

        CommandResult result = command.execute(model);

        assertTrue(result.getFeedbackToUser().contains("No properties found"));
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        Uuid uuid = new Uuid(1, CONTACT);
        ShowPropertiesCommand command = new ShowPropertiesCommand(uuid);

        assertEquals(command, command);
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        Uuid uuid = new Uuid(1, CONTACT);
        ShowPropertiesCommand command1 = new ShowPropertiesCommand(uuid);
        ShowPropertiesCommand command2 = new ShowPropertiesCommand(uuid);

        assertEquals(command1, command2);
    }

    @Test
    public void equals_differentValues_returnsFalse() {
        Uuid uuid1 = new Uuid(1, CONTACT);
        Uuid uuid2 = new Uuid(2, CONTACT);

        ShowPropertiesCommand command1 = new ShowPropertiesCommand(uuid1);
        ShowPropertiesCommand command2 = new ShowPropertiesCommand(uuid2);

        assertNotEquals(command1, command2);
    }

    @Test
    public void equals_differentType_returnsFalse() {
        Uuid uuid = new Uuid(1, CONTACT);
        ShowPropertiesCommand command = new ShowPropertiesCommand(uuid);

        assertNotEquals("not a command", command);
    }

    @Test
    public void equals_null_returnsFalse() {
        Uuid uuid = new Uuid(1, CONTACT);
        ShowPropertiesCommand command = new ShowPropertiesCommand(uuid);

        assertNotEquals(null, command);
    }

    @Test
    public void toString_containsUuid() {
        Uuid uuid = new Uuid(1, CONTACT);
        ShowPropertiesCommand command = new ShowPropertiesCommand(uuid);

        String result = command.toString();
        System.out.println(command);
        assertTrue(result.contains("contactUuid"));
    }

    public void execute_propertiesFoundWithNumericOwner_returnsSuccessMessage() throws Exception {
        // Create a property with numeric owner that matches a UUID
        Property propertyWithNumericOwner = new PropertyBuilderUtil()
                .withUuid(100)
                .withPropertyAddress("789 Test St")
                .withBathroom("2")
                .withBedroom("3")
                .withFloorArea("100")
                .withPostal("111111")
                .withPrice("400000")
                .withStatus("unsold")
                .withType("HDB")
                .withOwner("1")
                .build();

        // Add this property to the model
        model.addProperty(propertyWithNumericOwner);

        // Search for properties owned by UUID 1
        Uuid ownerUuid = new Uuid(1, CONTACT);
        ShowPropertiesCommand command = new ShowPropertiesCommand(ownerUuid);

        CommandResult result = command.execute(model);

        // Should show success message (not "No properties found")
        assertTrue(result.getFeedbackToUser().contains("Listed"));
        assertFalse(result.getFeedbackToUser().contains("No properties found"));
        assertTrue(result.getFeedbackToUser().contains("owned by client UUID: 1"));
    }

    @Test
    public void execute_multiplePropertiesFound_returnsPluralForm() throws Exception {
        // Create multiple properties with same numeric owner
        Property property1 = new PropertyBuilderUtil()
                .withUuid(101)
                .withPropertyAddress("100 Test Ave")
                .withOwner("5")
                .build();

        Property property2 = new PropertyBuilderUtil()
                .withUuid(102)
                .withPropertyAddress("200 Test Ave")
                .withOwner("5")
                .build();

        model.addProperty(property1);
        model.addProperty(property2);

        Uuid ownerUuid = new Uuid(5, CONTACT);
        ShowPropertiesCommand command = new ShowPropertiesCommand(ownerUuid);

        CommandResult result = command.execute(model);

        // Should show plural form
        assertTrue(result.getFeedbackToUser().contains("Listed"));
        assertTrue(result.getFeedbackToUser().contains("2 properties"));
    }
}
