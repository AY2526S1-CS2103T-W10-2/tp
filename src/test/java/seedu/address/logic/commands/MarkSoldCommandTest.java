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
import seedu.address.testutil.PropertyBuilder;


/**
 * Unit tests for {@code MarkSoldCommand}.
 */
public class MarkSoldCommandTest {

    private ModelStub modelStub;
    private Property property1;
    private Property property2;

    @BeforeEach
    public void setUp() {
        property1 = new PropertyBuilder()
                .withAddress("Blk 123 Clementi Ave 3")
                .withStatus("unsold")
                .build();
        property2 = new PropertyBuilder()
                .withAddress("Blk 456 Tampines St 21")
                .withStatus("unsold")
                .build();

        modelStub = new ModelStub();
        modelStub.addProperty(property1);
        modelStub.addProperty(property2);
    }

    @Test
    public void execute_validIds_marksAsSold() throws Exception {
        List<String> ids = Arrays.asList(property1.getId(), property2.getId());
        MarkSoldCommand command = new MarkSoldCommand(ids);

        CommandResult result = command.execute(modelStub);

        assertEquals(String.format(MarkSoldCommand.MESSAGE_MARK_SOLD_SUCCESS, 2), result.getFeedbackToUser());
        assertEquals(new Status("sold"), modelStub.getPropertyById(property1.getId()).getStatus());
        assertEquals(new Status("sold"), modelStub.getPropertyById(property2.getId()).getStatus());
    }

    @Test
    public void execute_invalidId_throwsCommandException() {
        List<String> ids = List.of("INVALID_ID");
        MarkSoldCommand command = new MarkSoldCommand(ids);

        CommandException thrown = assertThrows(CommandException.class, () -> command.execute(modelStub));
        assertEquals(String.format(MarkSoldCommand.MESSAGE_PROPERTY_NOT_FOUND, "INVALID_ID"), thrown.getMessage());
    }

    /**
     * A default model stub that has all of its methods failing.
     */
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
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setProperty(Property target, Property editedProperty) {
            String id = target.getId();
            Property updatedWithSameId = new Property(
                    editedProperty.getPropertyAddress(),
                    editedProperty.getBathroom(),
                    editedProperty.getBedroom(),
                    editedProperty.getFloorArea(),
                    editedProperty.getListing(),
                    editedProperty.getPostal(),
                    editedProperty.getPrice(),
                    editedProperty.getStatus(),
                    editedProperty.getType(),
                    editedProperty.getOwner()
            );
            propertyMap.put(id, updatedWithSameId);
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
