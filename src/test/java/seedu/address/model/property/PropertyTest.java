package seedu.address.model.property;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class PropertyTest {

    @Test
    void constructor_fieldsAreAccessible() {
        Property property = buildAlphaProperty();

        assertEquals(new PropertyAddress("123 Main St 5"), property.getPropertyAddress());
        assertEquals(new Bathroom("2"), property.getBathroom());
        assertEquals(new Bedroom("3"), property.getBedroom());
        assertEquals(new FloorArea("120"), property.getFloorArea());
        assertEquals(new Listing("sale"), property.getListing());
        assertEquals(new Postal("123456"), property.getPostal());
        assertEquals(new Price("500000"), property.getPrice());
        assertEquals(new Status("listed"), property.getStatus());
        assertEquals(new Type("HDB"), property.getType());
        assertEquals(new Owner("owner123"), property.getOwner());
        assertNotNull(property.getId());
    }

    @Test
    void isSameProperty_sameInstance_returnsTrue() {
        Property property = buildAlphaProperty();
        assertTrue(property.isSameProperty(property));
    }

    @Test
    void isSameProperty_nullReference_returnsFalse() {
        Property property = buildAlphaProperty();
        assertFalse(property.isSameProperty(null));
    }

    @Test
    void isSameProperty_sameAddressAndPostal_returnsTrue() {
        Property property = buildAlphaProperty();
        Property duplicateIdentity = new Property(new PropertyAddress("123 Main St 5"),
                new Bathroom("1"), new Bedroom("4"),
                new FloorArea("150"), new Listing("rent"), new Postal("123456"), new Price("600000"),
                new Status("listed"), new Type("hdb"), new Owner("owner789"));
        assertTrue(property.isSameProperty(duplicateIdentity));
    }

    @Test
    void isSameProperty_differentIdentity_returnsFalse() {
        Property property = buildAlphaProperty();
        Property differentProperty = buildBetaProperty();
        assertFalse(property.isSameProperty(differentProperty));
    }

    @Test
    void equals_sameValues_returnsTrue() {
        Property property = buildAlphaProperty();
        Property copy = buildAlphaProperty();
        assertTrue(property.equals(copy));
    }

    @Test
    void equals_differentValues_returnsFalse() {
        Property property = buildAlphaProperty();
        Property different = buildBetaProperty();
        assertFalse(property.equals(different));
    }

    @Test
    void hashCode_sameValues_match() {
        Property property = buildAlphaProperty();
        Property copy = buildAlphaProperty();
        assertEquals(property.hashCode(), copy.hashCode());
    }

    @Test
    void toString_containsKeyDetails() {
        Property property = buildAlphaProperty();
        String representation = property.toString();
        assertTrue(representation.contains("Id="));
        assertTrue(representation.contains("Address=123 Main St 5"));
        assertTrue(representation.contains("Postal=123456"));
        assertTrue(representation.contains("Price=500000"));
    }

    private static Property buildAlphaProperty() {
        return new Property(new PropertyAddress("123 Main St 5"), new Bathroom("2"), new Bedroom("3"),
                new FloorArea("120"), new Listing("sale"), new Postal("123456"), new Price("500000"),
                new Status("listed"), new Type("HDB"), new Owner("owner123"));
    }

    private static Property buildBetaProperty() {
        return new Property(new PropertyAddress("456 Market Ave 9"), new Bathroom("1"), new Bedroom("2"),
                new FloorArea("80"), new Listing("rent"), new Postal("654321"), new Price("3500"),
                new Status("listed"), new Type("apartment"), new Owner("owner456"));
    }
}
