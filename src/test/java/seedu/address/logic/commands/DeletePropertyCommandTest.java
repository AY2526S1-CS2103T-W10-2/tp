package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.uuid.Uuid.StoredItem.PROPERTY;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.PropertyBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.property.Bathroom;
import seedu.address.model.property.Bedroom;
import seedu.address.model.property.FloorArea;
import seedu.address.model.property.Owner;
import seedu.address.model.property.Postal;
import seedu.address.model.property.Price;
import seedu.address.model.property.Property;
import seedu.address.model.property.PropertyAddress;
import seedu.address.model.property.Status;
import seedu.address.model.property.Type;
import seedu.address.model.uuid.Uuid;

class DeletePropertyCommandTest {

    @Test
    void constructor_nullPropertyId_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DeletePropertyCommand(null));
    }

    @Test
    void execute_validIdUnfilteredList_success() throws Exception {
        Property propertyToDelete = buildProperty("123 Main St");
        PropertyBook propertyBook = new PropertyBook();
        propertyBook.addProperty(propertyToDelete);
        Model model = new ModelManager(new AddressBook(), propertyBook, new UserPrefs());
        DeletePropertyCommand deletePropertyCommand = new DeletePropertyCommand(propertyToDelete.getUuid());

        String expectedMessage = String.format(DeletePropertyCommand.MESSAGE_DELETE_PROPERTY_SUCCESS,
                Messages.format(propertyToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new PropertyBook(model.getPropertyBook()),
                new UserPrefs());
        expectedModel.deleteProperty(propertyToDelete);

        CommandResult result = deletePropertyCommand.execute(model);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(expectedModel, model);
    }

    @Test
    void execute_invalidIdUnfilteredList_throwsCommandException() {
        Property property = buildProperty("123 Main St");
        PropertyBook propertyBook = new PropertyBook();
        propertyBook.addProperty(property);
        Model model = new ModelManager(new AddressBook(), propertyBook, new UserPrefs());
        Uuid absentId = deriveDifferentId(property.getUuid());
        DeletePropertyCommand deletePropertyCommand = new DeletePropertyCommand(absentId);

        PropertyBook expectedPropertyBook = new PropertyBook(model.getPropertyBook());

        assertThrows(CommandException.class, Messages.MESSAGE_INVALID_PROPERTY_DISPLAYED_ID, () ->
            deletePropertyCommand.execute(model));
        assertEquals(expectedPropertyBook, model.getPropertyBook());
    }

    @Test
    void equals() {
        DeletePropertyCommand deleteAlpha = new DeletePropertyCommand(new Uuid(1, PROPERTY));
        DeletePropertyCommand deleteBeta = new DeletePropertyCommand(new Uuid(2, PROPERTY));

        assertTrue(deleteAlpha.equals(deleteAlpha));
        assertFalse(deleteAlpha.equals(1));
        assertFalse(deleteAlpha.equals(deleteBeta));
    }

    @Test
    void toStringMethod() {
        Uuid id = new Uuid(1, PROPERTY);
        DeletePropertyCommand command = new DeletePropertyCommand(id);
        String expected = DeletePropertyCommand.class.getCanonicalName() + "{targetPropertyId=" + id + "}";
        assertEquals(expected, command.toString());
    }

    private static Property buildProperty(String address) {
        return new Property(new Uuid(1, PROPERTY), new PropertyAddress(address), new Bathroom("2"), new Bedroom("3"),
                new FloorArea("120"), new Postal("123456"), new Price("500000"),
                new Status("unavailable"), new Type("HDB"), new Owner("owner123"), new HashSet<>(), new HashSet<>());
    }

    private static Uuid deriveDifferentId(Uuid propertyId) {
        requireNonNull(propertyId);
        int candidate = propertyId.getValue();
        int replacement = candidate + 1;
        return new Uuid(replacement, PROPERTY);
    }
}
