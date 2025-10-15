package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
import seedu.address.model.property.Listing;
import seedu.address.model.property.Owner;
import seedu.address.model.property.Postal;
import seedu.address.model.property.Price;
import seedu.address.model.property.Property;
import seedu.address.model.property.PropertyAddress;
import seedu.address.model.property.Status;
import seedu.address.model.property.Type;

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
        DeletePropertyCommand deletePropertyCommand = new DeletePropertyCommand(propertyToDelete.getId());

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
        String absentId = deriveDifferentId(property.getId());
        DeletePropertyCommand deletePropertyCommand = new DeletePropertyCommand(absentId);

        PropertyBook expectedPropertyBook = new PropertyBook(model.getPropertyBook());

        assertThrows(CommandException.class, Messages.MESSAGE_INVALID_PROPERTY_DISPLAYED_ID, () ->
            deletePropertyCommand.execute(model));
        assertEquals(expectedPropertyBook, model.getPropertyBook());
    }

    @Test
    void equals() {
        DeletePropertyCommand deleteAlpha = new DeletePropertyCommand("abc123");
        DeletePropertyCommand deleteAlphaUpper = new DeletePropertyCommand("ABC123");
        DeletePropertyCommand deleteBeta = new DeletePropertyCommand("def456");

        assertTrue(deleteAlpha.equals(deleteAlpha));
        assertTrue(deleteAlpha.equals(deleteAlphaUpper));
        assertFalse(deleteAlpha.equals(1));
        assertFalse(deleteAlpha.equals(deleteBeta));
    }

    @Test
    void toStringMethod() {
        DeletePropertyCommand command = new DeletePropertyCommand("abc123");
        String expected = DeletePropertyCommand.class.getCanonicalName() + "{targetPropertyId=abc123}";
        assertEquals(expected, command.toString());
    }

    private static Property buildProperty(String address) {
        return new Property("randomId", new PropertyAddress(address), new Bathroom("2"), new Bedroom("3"),
                new FloorArea("120"), new Listing("sale"), new Postal("123456"), new Price("500000"),
                new Status("sold"), new Type("HDB"), new Owner("owner123"), new HashSet<>(), new HashSet<>());
    }

    private static String deriveDifferentId(String propertyId) {
        requireNonNull(propertyId);
        char candidate = propertyId.charAt(0);
        char replacement = candidate == 'a' || candidate == 'A' ? 'b' : 'a';
        return replacement + propertyId.substring(1);
    }
}
