package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyPropertyBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.property.Property;
import seedu.address.testutil.PropertyBuilderUtil;

public class ShowClientsCommandTest {

    @Test
    public void constructor_nullPropertyId_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ShowClientsCommand(null));
    }

    @Test
    public void execute_propertyNotFound_throwsCommandException() {
        String nonExistentId = "nonexistent";
        ShowClientsCommand command = new ShowClientsCommand(nonExistentId);
        ModelStubWithEmptyPropertyList modelStub = new ModelStubWithEmptyPropertyList();

        String expectedMessage = String.format(ShowClientsCommand.MESSAGE_PROPERTY_NOT_FOUND,
                nonExistentId);
        assertThrows(CommandException.class, expectedMessage, () -> command.execute(modelStub));
    }

    @Test
    public void execute_propertyWithOwnerOnly_success() throws Exception {
        // Property owned by ALICE (UUID: 1)
        Property propertyWithOwner = new PropertyBuilderUtil()
                .withId("test123")
                .withOwner("1")
                .withBuyingPersonIds()
                .withSellingPersonIds()
                .build();

        ModelStubWithPropertyAndPersons modelStub = new ModelStubWithPropertyAndPersons(
                propertyWithOwner,
                Arrays.asList(ALICE)
        );

        CommandResult result = new ShowClientsCommand("test123").execute(modelStub);

        assertTrue(result.getFeedbackToUser().contains("Owner: Alice Pauline (UUID: 1)"));
        assertFalse(result.getFeedbackToUser().contains("Buyers:"));
        assertFalse(result.getFeedbackToUser().contains("Sellers:"));
    }

    @Test
    public void execute_propertyWithBuyersAndSellers_success() throws Exception {
        // Property owned by ALICE, with BENSON as buyer and CARL as seller
        Property propertyWithLinks = new PropertyBuilderUtil()
                .withId("test456")
                .withOwner("1")
                .withBuyingPersonIds(2)
                .withSellingPersonIds(3)
                .build();

        ModelStubWithPropertyAndPersons modelStub = new ModelStubWithPropertyAndPersons(
                propertyWithLinks,
                Arrays.asList(ALICE, BENSON, CARL)
        );

        CommandResult result = new ShowClientsCommand("test456").execute(modelStub);

        String feedback = result.getFeedbackToUser();
        assertTrue(feedback.contains("Owner: Alice Pauline (UUID: 1)"));
        assertTrue(feedback.contains("Buyers:"));
        assertTrue(feedback.contains("Benson Meier (UUID: 2)"));
        assertTrue(feedback.contains("Sellers:"));
        assertTrue(feedback.contains("Carl Kurz (UUID: 3)"));
    }

    @Test
    public void execute_propertyWithNoClients_returnsNoClientsMessage() throws Exception {
        // Property with owner ID that does not match any person
        Property propertyNoClients = new PropertyBuilderUtil()
                .withId("test789")
                .withOwner("999")
                .withBuyingPersonIds()
                .withSellingPersonIds()
                .build();

        ModelStubWithPropertyAndPersons modelStub = new ModelStubWithPropertyAndPersons(
                propertyNoClients,
                new ArrayList<>()
        );

        CommandResult result = new ShowClientsCommand("test789").execute(modelStub);

        assertTrue(result.getFeedbackToUser().contains("No clients are currently associated"));
    }

    @Test
    public void equals() {
        ShowClientsCommand showFirstCommand = new ShowClientsCommand("abc123");
        ShowClientsCommand showSecondCommand = new ShowClientsCommand("def456");

        // same object -> returns true
        assertTrue(showFirstCommand.equals(showFirstCommand));

        // same values -> returns true
        ShowClientsCommand showFirstCommandCopy = new ShowClientsCommand("abc123");
        assertTrue(showFirstCommand.equals(showFirstCommandCopy));

        // different types -> returns false
        assertFalse(showFirstCommand.equals(1));

        // null -> returns false
        assertFalse(showFirstCommand.equals(null));

        // different property id -> returns false
        assertFalse(showFirstCommand.equals(showSecondCommand));
    }

    @Test
    public void toStringMethod() {
        ShowClientsCommand command = new ShowClientsCommand("abc123");
        String expected = ShowClientsCommand.class.getCanonicalName()
                + "{propertyId=abc123}";
        assertEquals(expected, command.toString());
    }

    /**
     * A Model stub that always returns an empty property list.
     */
    private class ModelStubWithEmptyPropertyList extends ModelStub {
        @Override
        public ObservableList<Property> getFilteredPropertyList() {
            return FXCollections.observableArrayList();
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            return FXCollections.observableArrayList();
        }
    }

    /**
     * A Model stub that contains a single property and a list of people
     */
    private class ModelStubWithPropertyAndPersons extends ModelStub {
        private final Property property;
        private final ObservableList<Person> persons;

        ModelStubWithPropertyAndPersons(Property property, java.util.List<Person> persons) {
            requireNonNull(property);
            this.property = property;
            this.persons = FXCollections.observableArrayList(persons);
        }

        @Override
        public ObservableList<Property> getFilteredPropertyList() {
            return FXCollections.observableArrayList(property);
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            return persons;
        }
    }

    /**
     * A default model stub that has all of the methods failing.
     */
    private class ModelStub implements Model {
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
        public Property getPropertyById(String id) {
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
        public void addProperty(Property property) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setProperty(Property target, Property editedProperty) {
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
