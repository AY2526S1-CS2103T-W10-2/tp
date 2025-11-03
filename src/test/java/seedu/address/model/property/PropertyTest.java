package seedu.address.model.property;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.uuid.Uuid.StoredItem.PROPERTY;
import static seedu.address.testutil.TypicalProperties.PROPERTY_ALPHA;
import static seedu.address.testutil.TypicalProperties.PROPERTY_BETA;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

import seedu.address.model.uuid.Uuid;

class PropertyTest {

    @Test
    void constructor_fieldsAreAccessible() {
        Property property = PROPERTY_ALPHA;

        assertEquals(new PropertyAddress("123 Main St 5"), property.getPropertyAddress());
        assertEquals(new Bathroom("2"), property.getBathroom());
        assertEquals(new Bedroom("3"), property.getBedroom());
        assertEquals(new FloorArea("120"), property.getFloorArea());
        assertEquals(new Listing("sale"), property.getListing());
        assertEquals(new Postal("123456"), property.getPostal());
        assertEquals(new Price("500000"), property.getPrice());
        assertEquals(new Status("unavailable"), property.getStatus());
        assertEquals(new Type("HDB"), property.getType());
        assertEquals(new Owner("owner123"), property.getOwner());
        assertEquals(new Uuid(1, PROPERTY), property.getUuid());
    }

    @Test
    void isSameProperty_sameInstance_returnsTrue() {
        Property property = PROPERTY_ALPHA;
        assertTrue(property.isSameProperty(property));
    }

    @Test
    void isSameProperty_nullReference_returnsFalse() {
        Property property = PROPERTY_ALPHA;
        assertFalse(property.isSameProperty(null));
    }

    @Test
    void isSameProperty_sameAddressAndPostal_returnsTrue() {
        Property property = PROPERTY_ALPHA;
        Property duplicateIdentity = new Property(null, new PropertyAddress("123 Main St 5"),
                new Bathroom("1"), new Bedroom("4"),
                new FloorArea("150"), new Listing("rent"), new Postal("123456"), new Price("600000"),
                new Status("unavailable"), new Type("hdb"), new Owner("owner789"), new HashSet<>(), new HashSet<>());
        assertTrue(property.isSameProperty(duplicateIdentity));
    }

    @Test
    void isSameProperty_differentIdentity_returnsFalse() {
        Property property = PROPERTY_ALPHA;
        Property differentProperty = PROPERTY_BETA;
        assertFalse(property.isSameProperty(differentProperty));
    }

    @Test
    void equals_sameValues_returnsTrue() {
        Property property = PROPERTY_ALPHA;
        Property copy = PROPERTY_ALPHA;
        assertTrue(property.equals(copy));
    }

    @Test
    void equals_differentValues_returnsFalse() {
        Property property = PROPERTY_ALPHA;
        Property different = PROPERTY_BETA;
        assertFalse(property.equals(different));
    }

    @Test
    void equals_differentType_returnsFalse() {
        Property property = PROPERTY_ALPHA;
        assertFalse(property.equals("not a property"));
    }

    @Test
    void hashCode_sameValues_match() {
        Property property = PROPERTY_ALPHA;
        Property copy = PROPERTY_ALPHA;
        assertEquals(property.hashCode(), copy.hashCode());
    }

    @Test
    void toString_containsKeyDetails() {
        Property property = PROPERTY_ALPHA;
        String representation = property.toString();
        assertTrue(representation.contains("Id="));
        assertTrue(representation.contains("Address=123 Main St 5"));
        assertTrue(representation.contains("Postal=123456"));
        assertTrue(representation.contains("Price=500000"));
    }

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Property property = PROPERTY_ALPHA;
        assertThrows(UnsupportedOperationException.class, () -> property.getBuyingContactIds().remove(0));
        assertThrows(UnsupportedOperationException.class, () -> property.getSellingContactIds().remove(0));
    }
}
