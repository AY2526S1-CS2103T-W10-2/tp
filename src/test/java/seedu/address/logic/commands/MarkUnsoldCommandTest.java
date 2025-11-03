package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyPropertyBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.contact.Contact;
import seedu.address.model.property.Property;
import seedu.address.model.property.Status;
import seedu.address.model.uuid.Uuid;
import seedu.address.testutil.PropertyBuilderUtil;

/**
 * Unit tests for {@code MarkUnsoldCommand}.
 */
public class MarkUnsoldCommandTest {

    private ModelStub modelStub;
    private Property property1;

    @BeforeEach
    public void setUp() {
        property1 = new PropertyBuilderUtil()
                .withPropertyAddress("Blk 999 Orchard Rd")
                .withStatus("unavailable")
                .build();
        modelStub = new ModelStub();
        modelStub.addProperty(property1);
    }

    @Test
    public void execute_validId_marksAsUnsold() throws Exception {
        Set<Uuid> ids = Set.of(property1.getUuid());
        MarkUnsoldCommand command = new MarkUnsoldCommand(ids);

        CommandResult result = command.execute(modelStub);

        assertEquals(String.format(MarkUnsoldCommand.MESSAGE_MARK_UNSOLD_SUCCESS, 1), result.getFeedbackToUser());
        assertEquals(new Status("available"), modelStub.getPropertyById(property1.getUuid()).getStatus());
    }

    @Test
    public void execute_invalidId_throwsCommandException() {
        Uuid invalidId = new Uuid(999999, Uuid.StoredItem.PROPERTY);
        Set<Uuid> ids = Set.of(invalidId);
        MarkUnsoldCommand command = new MarkUnsoldCommand(ids);

        CommandException thrown = assertThrows(CommandException.class, () -> command.execute(modelStub));
        assertTrue(thrown.getMessage().contains(invalidId.getValue() + ""));
    }

    @Test
    public void execute_mixedValidAndInvalidIds_throwsCommandExceptionWithAllInvalids() {
        Uuid invalidId1 = new Uuid(999999, Uuid.StoredItem.PROPERTY);
        Uuid invalidId2 = new Uuid(999998, Uuid.StoredItem.PROPERTY);
        Set<Uuid> ids = Set.of(property1.getUuid(), invalidId1, invalidId2);
        MarkUnsoldCommand command = new MarkUnsoldCommand(ids);

        CommandException thrown = assertThrows(CommandException.class, () -> command.execute(modelStub));

        // Check message contains both invalid IDs
        String message = thrown.getMessage();
        assertTrue(message.contains(String.valueOf(invalidId1.getValue())));
        assertTrue(message.contains(String.valueOf(invalidId2.getValue())));

        // Also ensure the valid ID is not marked (status unchanged)
        assertEquals(new Status("unavailable"), modelStub.getPropertyById(property1.getUuid()).getStatus());
    }

    private static class ModelStub implements Model {
        private final Map<Uuid, Property> propertyMap = new HashMap<>();

        @Override
        public void addProperty(Property property) {
            propertyMap.put(property.getUuid(), property);
        }

        @Override
        public Property getPropertyById(Uuid id) {
            return propertyMap.get(id);
        }

        @Override
        public void setProperty(Property target, Property editedProperty) {
            Uuid id = target.getUuid();
            Property updatedWithSameId = new Property(
                    id,
                    editedProperty.getPropertyAddress(),
                    editedProperty.getBathroom(),
                    editedProperty.getBedroom(),
                    editedProperty.getFloorArea(),
                    editedProperty.getPostal(),
                    editedProperty.getPrice(),
                    editedProperty.getStatus(),
                    editedProperty.getType(),
                    editedProperty.getOwner(),
                    editedProperty.getBuyingContactIds(),
                    editedProperty.getSellingContactIds()
            );
            propertyMap.put(id, updatedWithSameId);
        }

        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addContact(Contact contact) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasContact(Contact contact) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteContact(Contact target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setContact(Contact target, Contact editedContact) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Contact> getFilteredContactList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredContactList(Predicate<Contact> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getPropertyBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPropertyBookFilePath(Path propertyBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPropertyBook(ReadOnlyPropertyBook propertyBook) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyPropertyBook getPropertyBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasProperty(Property property) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteProperty(Property target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Property> getFilteredPropertyList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPropertyList(Predicate<Property> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }
}
