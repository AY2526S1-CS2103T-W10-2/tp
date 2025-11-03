package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CONTACTS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PROPERTIES;
import static seedu.address.model.uuid.Uuid.StoredItem.PROPERTY;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalContacts.ALICE;
import static seedu.address.testutil.TypicalContacts.BENSON;
import static seedu.address.testutil.TypicalProperties.PROPERTY_ALPHA;
import static seedu.address.testutil.TypicalProperties.PROPERTY_BETA;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.contact.FilterContactPredicate;
import seedu.address.model.uuid.Uuid;
import seedu.address.testutil.AddressBookBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new AddressBook(), new AddressBook(modelManager.getAddressBook()));
        assertEquals(new PropertyBook(), new PropertyBook(modelManager.getPropertyBook()));
    }

    @Test
    public void getPropertyById_existingId_returnsProperty() {
        modelManager.addProperty(PROPERTY_ALPHA);
        modelManager.addProperty(PROPERTY_BETA);

        assertEquals(PROPERTY_ALPHA, modelManager.getPropertyById(PROPERTY_ALPHA.getUuid()));
        assertEquals(PROPERTY_BETA, modelManager.getPropertyById(PROPERTY_BETA.getUuid()));
    }

    @Test
    public void getPropertyById_nonExistingId_returnsNull() {
        assertNull(modelManager.getPropertyById(new Uuid(999999, PROPERTY)));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setAddressBookFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setAddressBookFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setAddressBookFilePath(null));
    }

    @Test
    public void setAddressBookFilePath_validPath_setsAddressBookFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setAddressBookFilePath(path);
        assertEquals(path, modelManager.getAddressBookFilePath());
    }

    @Test
    public void setPropertyBookFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setPropertyBookFilePath(null));
    }

    @Test
    public void setPropertyBookFilePath_validPath_setsPropertyBookFilePath() {
        Path path = Paths.get("property/book/file/path");
        modelManager.setPropertyBookFilePath(path);
        assertEquals(path, modelManager.getPropertyBookFilePath());
    }

    @Test
    public void hasContact_nullContact_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasContact(null));
    }

    @Test
    public void hasContact_contactNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasContact(ALICE));
    }

    @Test
    public void hasContact_contactInAddressBook_returnsTrue() {
        modelManager.addContact(ALICE);
        assertTrue(modelManager.hasContact(ALICE));
    }

    @Test
    public void getFilteredContactList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredContactList().remove(0));
    }

    @Test
    public void hasProperty_nullProperty_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasProperty(null));
    }

    @Test
    public void hasProperty_propertyNotInPropertyBook_returnsFalse() {
        assertFalse(modelManager.hasProperty(PROPERTY_ALPHA));
    }

    @Test
    public void hasProperty_propertyInPropertyBook_returnsTrue() {
        modelManager.addProperty(PROPERTY_ALPHA);
        assertTrue(modelManager.hasProperty(PROPERTY_ALPHA));
    }

    @Test
    public void getFilteredPropertyList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredPropertyList().remove(0));
    }

    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder().withContact(ALICE).withContact(BENSON).build();
        AddressBook differentAddressBook = new AddressBook();
        PropertyBook propertyBook = new PropertyBook();
        propertyBook.addProperty(PROPERTY_ALPHA);
        PropertyBook propertyBookWithDifferentProperty = new PropertyBook();
        propertyBookWithDifferentProperty.addProperty(PROPERTY_BETA);
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(addressBook, propertyBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(addressBook, propertyBook, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentAddressBook, propertyBook, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredContactList(new FilterContactPredicate(
                Optional.of(Arrays.asList(keywords)),
                Optional.empty(), Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty(),
                Optional.empty()
        ));
        assertFalse(modelManager.equals(new ModelManager(addressBook, propertyBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredContactList(PREDICATE_SHOW_ALL_CONTACTS);

        // different filtered property list -> returns false
        modelManager.updateFilteredPropertyList(property -> false);
        assertFalse(modelManager.equals(new ModelManager(addressBook, propertyBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPropertyList(PREDICATE_SHOW_ALL_PROPERTIES);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(addressBook, propertyBook, differentUserPrefs)));

        // different propertyBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(addressBook, propertyBookWithDifferentProperty, userPrefs)));
    }
}
