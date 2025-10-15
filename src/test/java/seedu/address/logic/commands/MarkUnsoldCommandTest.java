package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import seedu.address.model.person.Person;
import seedu.address.model.property.Property;
import seedu.address.model.property.Status;
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
                .withStatus("sold")
                .build();
        modelStub = new ModelStub();
        modelStub.addProperty(property1);
    }

    @Test
    public void execute_validId_marksAsUnsold() throws Exception {
        List<String> ids = Arrays.asList(property1.getId());
        MarkUnsoldCommand command = new MarkUnsoldCommand(ids);

        CommandResult result = command.execute(modelStub);

        assertEquals(String.format(MarkUnsoldCommand.MESSAGE_MARK_UNSOLD_SUCCESS, 1), result.getFeedbackToUser());
        assertEquals(new Status("unsold"), modelStub.getPropertyById(property1.getId()).getStatus());
    }

    @Test
    public void execute_invalidId_throwsCommandException() {
        List<String> ids = List.of("NOT_FOUND");
        MarkUnsoldCommand command = new MarkUnsoldCommand(ids);

        CommandException thrown = assertThrows(CommandException.class, () -> command.execute(modelStub));
        assertEquals(String.format(MarkUnsoldCommand.MESSAGE_PROPERTY_NOT_FOUND, "NOT_FOUND"), thrown.getMessage());
    }

    @Test
    public void execute_mixOfValidAndInvalidIds_throwsCommandException() {
        List<String> ids = Arrays.asList(property1.getId(), "INVALID_ID");
        MarkUnsoldCommand command = new MarkUnsoldCommand(ids);

        CommandException thrown = assertThrows(CommandException.class, () -> command.execute(modelStub));

        assertEquals(String.format(MarkSoldCommand.MESSAGE_PROPERTY_NOT_FOUND, "INVALID_ID"), thrown.getMessage());

        // Verify that valid property remains unchanged (not partially executed)
        assertEquals(new Status("sold"), modelStub.getPropertyById(property1.getId()).getStatus());
    }


    private static class ModelStub implements Model {
        private final Map<String, Property> propertyMap = new HashMap<>();

        @Override
        public void addProperty(Property property) {
            propertyMap.put(property.getId(), property);
        }

        @Override
        public Property getPropertyById(String id) {
            return propertyMap.get(id);
        }

        @Override
        public void setProperty(Property target, Property editedProperty) {
            String id = target.getId();
            Property updatedWithSameId = new Property(
                    id,
                    editedProperty.getPropertyAddress(),
                    editedProperty.getBathroom(),
                    editedProperty.getBedroom(),
                    editedProperty.getFloorArea(),
                    editedProperty.getListing(),
                    editedProperty.getPostal(),
                    editedProperty.getPrice(),
                    editedProperty.getStatus(),
                    editedProperty.getType(),
                    editedProperty.getOwner(),
                    editedProperty.getBuyingPersonIds(),
                    editedProperty.getSellingPersonIds()
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
        public void addPerson(Person person) {
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
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
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
