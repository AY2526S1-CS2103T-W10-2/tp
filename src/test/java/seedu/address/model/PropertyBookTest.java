package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import seedu.address.model.property.exceptions.DuplicatePropertyException;

public class PropertyBookTest {

    private static final Property PROPERTY_ALPHA = new Property(new PropertyAddress("123 Main St 5"),
            new Bathroom("2"),
            new Bedroom("3"), new FloorArea("120"), new Listing("sale"), new Postal("123456"),
            new Price("500000"), new Status("sold"), new Type("HDB"), new Owner("owner123"));

    private static final Property PROPERTY_BETA = new Property(new PropertyAddress("456 Market Ave 9"),
            new Bathroom("1"),
            new Bedroom("2"), new FloorArea("80"), new Listing("rent"), new Postal("654321"),
            new Price("3500"), new Status("unsold"), new Type("apartment"), new Owner("owner456"));

    private static final Property PROPERTY_ALPHA_VARIANT = new Property(new PropertyAddress("123 Main St 5"),
            new Bathroom("2"), new Bedroom("3"), new FloorArea("120"), new Listing("rent"), new Postal("123456"),
            new Price("510000"), new Status("sold"), new Type("HDB"), new Owner("owner789"));

    private final PropertyBook propertyBook = new PropertyBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), propertyBook.getPropertyList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> propertyBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyPropertyBook_replacesData() {
        PropertyBook newData = new PropertyBook();
        newData.addProperty(PROPERTY_ALPHA);
        newData.addProperty(PROPERTY_BETA);
        propertyBook.resetData(newData);
        assertEquals(newData, propertyBook);
    }

    @Test
    public void resetData_withDuplicateProperties_throwsDuplicatePropertyException() {
        List<Property> newProperties = Arrays.asList(PROPERTY_ALPHA, PROPERTY_ALPHA_VARIANT);
        PropertyBookStub newData = new PropertyBookStub(newProperties);
        assertThrows(DuplicatePropertyException.class, () -> propertyBook.resetData(newData));
    }

    @Test
    public void hasProperty_nullProperty_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> propertyBook.hasProperty(null));
    }

    @Test
    public void hasProperty_propertyNotInPropertyBook_returnsFalse() {
        assertFalse(propertyBook.hasProperty(PROPERTY_ALPHA));
    }

    @Test
    public void hasProperty_propertyInPropertyBook_returnsTrue() {
        propertyBook.addProperty(PROPERTY_ALPHA);
        assertTrue(propertyBook.hasProperty(PROPERTY_ALPHA));
    }

    @Test
    public void hasProperty_propertyWithSameIdentityFieldsInPropertyBook_returnsTrue() {
        propertyBook.addProperty(PROPERTY_ALPHA);
        assertTrue(propertyBook.hasProperty(PROPERTY_ALPHA_VARIANT));
    }

    @Test
    public void getPropertyList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> propertyBook.getPropertyList().remove(0));
    }

    @Test
    public void toStringMethod() {
        String expected = PropertyBook.class.getCanonicalName() + "{properties=" + propertyBook.getPropertyList()
                + "}";
        assertEquals(expected, propertyBook.toString());
    }

    /**
     * A stub ReadOnlyPropertyBook whose properties list can violate interface constraints.
     */
    private static class PropertyBookStub implements ReadOnlyPropertyBook {
        private final ObservableList<Property> properties = FXCollections.observableArrayList();

        PropertyBookStub(Collection<Property> properties) {
            this.properties.setAll(properties);
        }

        @Override
        public ObservableList<Property> getPropertyList() {
            return properties;
        }
    }
}
