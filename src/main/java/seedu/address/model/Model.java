package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.person.Person;
import seedu.address.model.property.Property;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Property> PREDICATE_SHOW_ALL_PROPERTIES = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    // =========== AddressBook ================================================================================
    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setPerson(Person target, Person editedPerson);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    //=========== PropertyBook ================================================================================
    /**
     * Returns the user prefs' property book file path.
     */
    Path getPropertyBookFilePath();

    /**
     * Sets the user prefs' property book file path.
     */
    void setPropertyBookFilePath(Path propertyBookFilePath);

    /**
     * Replaces property book data with the data in {@code propertyBook}.
     */
    void setPropertyBook(ReadOnlyPropertyBook propertyBook);

    /** Returns the PropertyBook */
    ReadOnlyPropertyBook getPropertyBook();

    /**
     * Returns true if a property with the same identity as {@code property} exists in the property book.
     */
    boolean hasProperty(Property property);

    /**
     * Deletes the given property.
     * The property must exist in the property book.
     */
    void deleteProperty(Property target);

    /**
     * Adds the given property.
     * {@code property} must not already exist in the property book.
     */
    void addProperty(Property property);

    /**
     * Replaces the given property {@code target} with {@code editedProperty}.
     * {@code target} must exist in the property book.
     * The property identity of {@code editedProperty} must not be the same as another existing property
     * in the property book.
     */
    void setProperty(Property target, Property editedProperty);

    /** Returns an unmodifiable view of the filtered property list */
    ObservableList<Property> getFilteredPropertyList();

    /**
     * Updates the filter of the filtered property list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPropertyList(Predicate<Property> predicate);

    Property getPropertyById(String id);
}
