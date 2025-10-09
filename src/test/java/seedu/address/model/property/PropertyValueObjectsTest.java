package seedu.address.model.property;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class PropertyValueObjectsTest {

    @Test
    void address_validation() {
        assertThrows(NullPointerException.class, () -> new PropertyAddress(null));
        assertThrows(IllegalArgumentException.class, () -> new PropertyAddress("12345"));
        assertThrows(IllegalArgumentException.class, () -> new PropertyAddress("Main Street"));
        assertThrows(NullPointerException.class, () -> PropertyAddress.isValidPropertyAddress(null));
        assertTrue(PropertyAddress.isValidPropertyAddress("123 Main St 5"));
        assertEquals("123 Main St 5", new PropertyAddress(" 123 Main St 5 ").toString());
    }

    @Test
    void address_equals() {
        PropertyAddress address = new PropertyAddress("123 Main St 5");
        assertEquals(new PropertyAddress("123 Main St 5"), address);
        assertEquals(address, address);
        assertNotEquals(null, address);
        assertNotEquals(new PropertyAddress("456 Market Ave 9"), address);
    }

    @Test
    void bathroom_validation() {
        assertThrows(NullPointerException.class, () -> new Bathroom(null));
        assertThrows(IllegalArgumentException.class, () -> new Bathroom("-1"));
        assertThrows(IllegalArgumentException.class, () -> new Bathroom("21"));
        assertThrows(NullPointerException.class, () -> Bathroom.isValidBathroom(null));
        assertTrue(Bathroom.isValidBathroom("0"));
        assertTrue(Bathroom.isValidBathroom("20"));
    }

    @Test
    void bathroom_equals() {
        Bathroom bathroom = new Bathroom("2");
        assertEquals(new Bathroom("2"), bathroom);
        assertEquals(bathroom, bathroom);
        assertNotEquals(null, bathroom);
        assertNotEquals(new Bathroom("1"), bathroom);
    }

    @Test
    void bedroom_validation() {
        assertThrows(NullPointerException.class, () -> new Bedroom(null));
        assertThrows(IllegalArgumentException.class, () -> new Bedroom("-1"));
        assertThrows(IllegalArgumentException.class, () -> new Bedroom("21"));
        assertThrows(NullPointerException.class, () -> Bedroom.isValidBedroom(null));
        assertTrue(Bedroom.isValidBedroom("0"));
        assertTrue(Bedroom.isValidBedroom("20"));
    }

    @Test
    void bedroom_equals() {
        Bedroom bedroom = new Bedroom("3");
        assertEquals(new Bedroom("3"), bedroom);
        assertEquals(bedroom, bedroom);
        assertNotEquals(null, bedroom);
        assertNotEquals(new Bedroom("2"), bedroom);
    }

    @Test
    void floorArea_validation() {
        assertThrows(NullPointerException.class, () -> new FloorArea(null));
        assertThrows(IllegalArgumentException.class, () -> new FloorArea("49"));
        assertThrows(IllegalArgumentException.class, () -> new FloorArea("100001"));
        assertThrows(NullPointerException.class, () -> FloorArea.isValidFloorArea(null));
        assertTrue(FloorArea.isValidFloorArea("50"));
        assertTrue(FloorArea.isValidFloorArea("100000"));
    }

    @Test
    void floorArea_equals() {
        FloorArea floorArea = new FloorArea("120");
        assertTrue(floorArea.equals(new FloorArea("120")));
        assertTrue(floorArea.equals(floorArea));
        assertFalse(floorArea.equals(null));
        assertFalse(floorArea.equals(new FloorArea("80")));
    }

    @Test
    void listing_validation() {
        assertThrows(NullPointerException.class, () -> new Listing(null));
        assertThrows(IllegalArgumentException.class, () -> new Listing("lease"));
        assertThrows(NullPointerException.class, () -> Listing.isValidListing(null));
        assertTrue(Listing.isValidListing("sale"));
        assertTrue(Listing.isValidListing("rent"));
        assertEquals("sale", new Listing("SALE").toString());
    }

    @Test
    void listing_equals() {
        Listing listing = new Listing("sale");
        assertTrue(listing.equals(new Listing("sale")));
        assertTrue(listing.equals(new Listing("SALE")));
        assertTrue(listing.equals(listing));
        assertFalse(listing.equals(null));
        assertFalse(listing.equals(new Listing("rent")));
    }

    @Test
    void postal_validation() {
        assertThrows(NullPointerException.class, () -> new Postal(null));
        assertThrows(IllegalArgumentException.class, () -> new Postal("12345"));
        assertThrows(IllegalArgumentException.class, () -> new Postal("1234567"));
        assertThrows(IllegalArgumentException.class, () -> new Postal("ABC123"));
        assertThrows(NullPointerException.class, () -> Postal.isValidPostal(null));
        assertTrue(Postal.isValidPostal("123456"));
    }

    @Test
    void postal_equals() {
        Postal postal = new Postal("123456");
        assertTrue(postal.equals(new Postal("123456")));
        assertTrue(postal.equals(postal));
        assertFalse(postal.equals(null));
        assertFalse(postal.equals(new Postal("654321")));
    }

    @Test
    void price_validation() {
        assertThrows(NullPointerException.class, () -> new Price(null));
        assertThrows(IllegalArgumentException.class, () -> new Price("0"));
        assertThrows(IllegalArgumentException.class, () -> new Price("-1"));
        assertThrows(IllegalArgumentException.class, () -> new Price("1000000000001"));
        assertThrows(NullPointerException.class, () -> Price.isValidPrice(null));
        assertTrue(Price.isValidPrice("1"));
        assertTrue(Price.isValidPrice("1000000000000"));
    }

    @Test
    void price_equals() {
        Price price = new Price("500000");
        assertTrue(price.equals(new Price("500000")));
        assertTrue(price.equals(price));
        assertFalse(price.equals(null));
        assertFalse(price.equals(new Price("600000")));
    }

    @Test
    void status_validation() {
        assertThrows(NullPointerException.class, () -> new Status(null));
        assertThrows(IllegalArgumentException.class, () -> new Status("pending"));
        assertThrows(NullPointerException.class, () -> Status.isValidStatus(null));
        assertTrue(Status.isValidStatus("unsold"));
        assertTrue(Status.isValidStatus("sold"));
        assertEquals("sold", new Status("sold").toString());
    }

    @Test
    void status_equals() {
        Status status = new Status("sold");
        assertTrue(status.equals(new Status("sold")));
        assertTrue(status.equals(new Status("SOLD")));
        assertTrue(status.equals(status));
        assertFalse(status.equals(null));
        assertFalse(status.equals(new Status("UNsold")));
    }

    @Test
    void type_validation() {
        assertThrows(NullPointerException.class, () -> new Type(null));
        assertThrows(IllegalArgumentException.class, () -> new Type("villa"));
        assertThrows(NullPointerException.class, () -> Type.isValidType(null));
        assertTrue(Type.isValidType("hdb"));
        assertTrue(Type.isValidType("Condo"));
        assertEquals("apartment", new Type(" Apartment ").toString());
    }

    @Test
    void type_equals() {
        Type type = new Type("HDB");
        assertTrue(type.equals(new Type("hdb")));
        assertTrue(type.equals(type));
        assertFalse(type.equals(null));
        assertFalse(type.equals(new Type("condo")));
    }

    @Test
    void owner_validation() {
        assertThrows(NullPointerException.class, () -> new Owner(null));
        assertThrows(IllegalArgumentException.class, () -> new Owner(""));
        assertThrows(IllegalArgumentException.class, () -> new Owner("owner 1"));
        assertThrows(NullPointerException.class, () -> Owner.isValidOwner(null));
        assertTrue(Owner.isValidOwner("owner_123"));
        assertEquals("owner123", new Owner(" owner123 ").toString());
    }

    @Test
    void owner_equals() {
        Owner owner = new Owner("owner123");
        assertTrue(owner.equals(new Owner("owner123")));
        assertTrue(owner.equals(owner));
        assertFalse(owner.equals(null));
        assertFalse(owner.equals(new Owner("owner456")));
    }
}
