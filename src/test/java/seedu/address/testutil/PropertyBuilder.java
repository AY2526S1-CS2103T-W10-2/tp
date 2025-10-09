package seedu.address.testutil;

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

/**
 * A utility class to help with building {@code Property} objects.
 */
public class PropertyBuilder {

    public static final String DEFAULT_ADDRESS = "123 Clementi Road";
    public static final String DEFAULT_BATHROOM = "2";
    public static final String DEFAULT_BEDROOM = "3";
    public static final String DEFAULT_FLOOR_AREA = "120";
    public static final String DEFAULT_LISTING = "sale";
    public static final String DEFAULT_POSTAL = "120467";
    public static final String DEFAULT_PRICE = "800000";
    public static final String DEFAULT_STATUS = "sold";
    public static final String DEFAULT_TYPE = "HDB";
    public static final String DEFAULT_OWNER = "234";
    public static final String DEFAULT_ID = "123";

    private PropertyAddress address;
    private Bathroom bathroom;
    private Bedroom bedroom;
    private FloorArea floorArea;
    private Listing listing;
    private Postal postal;
    private Price price;
    private Status status;
    private Type type;
    private Owner owner;
    private String id;

    /**
     * Creates a {@code PropertyBuilder} with the default details.
     */
    public PropertyBuilder() {
        id = DEFAULT_ID;
        address = new PropertyAddress(DEFAULT_ADDRESS);
        bathroom = new Bathroom(DEFAULT_BATHROOM);
        bedroom = new Bedroom(DEFAULT_BEDROOM);
        floorArea = new FloorArea(DEFAULT_FLOOR_AREA);
        listing = new Listing(DEFAULT_LISTING);
        postal = new Postal(DEFAULT_POSTAL);
        price = new Price(DEFAULT_PRICE);
        status = new Status(DEFAULT_STATUS);
        type = new Type(DEFAULT_TYPE);
        owner = new Owner(DEFAULT_OWNER);
    }

    /**
     * Initializes the {@code PropertyBuilder} with the data of {@code propertyToCopy}.
     *
     * @param propertyToCopy The property whose data will be copied.
     */
    public PropertyBuilder(Property propertyToCopy) {
        id = propertyToCopy.getId();
        address = propertyToCopy.getPropertyAddress();
        bathroom = propertyToCopy.getBathroom();
        bedroom = propertyToCopy.getBedroom();
        floorArea = propertyToCopy.getFloorArea();
        listing = propertyToCopy.getListing();
        postal = propertyToCopy.getPostal();
        price = propertyToCopy.getPrice();
        status = propertyToCopy.getStatus();
        type = propertyToCopy.getType();
        owner = propertyToCopy.getOwner();
    }

    /**
     * Sets the {@code PropertyAddress} of the {@code Property} being built.
     *
     * @param address The address string.
     * @return The updated {@code PropertyBuilder}.
     */
    public PropertyBuilder withAddress(String address) {
        this.address = new PropertyAddress(address);
        return this;
    }

    /**
     * Sets the {@code Bathroom} count of the {@code Property} being built.
     *
     * @param bathroom The bathroom count string.
     * @return The updated {@code PropertyBuilder}.
     */
    public PropertyBuilder withBathroom(String bathroom) {
        this.bathroom = new Bathroom(bathroom);
        return this;
    }

    /**
     * Sets the {@code Bedroom} count of the {@code Property} being built.
     *
     * @param bedroom The bedroom count string.
     * @return The updated {@code PropertyBuilder}.
     */
    public PropertyBuilder withBedroom(String bedroom) {
        this.bedroom = new Bedroom(bedroom);
        return this;
    }

    /**
     * Sets the {@code FloorArea} of the {@code Property} being built.
     *
     * @param floorArea The floor area string.
     * @return The updated {@code PropertyBuilder}.
     */
    public PropertyBuilder withFloorArea(String floorArea) {
        this.floorArea = new FloorArea(floorArea);
        return this;
    }

    /**
     * Sets the {@code Listing} type of the {@code Property} being built.
     *
     * @param listing The listing type string (e.g., sale, rent).
     * @return The updated {@code PropertyBuilder}.
     */
    public PropertyBuilder withListing(String listing) {
        this.listing = (listing == null) ? null : new Listing(listing);
        return this;
    }

    /**
     * Sets the {@code Postal} code of the {@code Property} being built.
     *
     * @param postal The postal code string.
     * @return The updated {@code PropertyBuilder}.
     */
    public PropertyBuilder withPostal(String postal) {
        this.postal = new Postal(postal);
        return this;
    }

    /**
     * Sets the {@code Price} of the {@code Property} being built.
     *
     * @param price The price string.
     * @return The updated {@code PropertyBuilder}.
     */
    public PropertyBuilder withPrice(String price) {
        this.price = new Price(price);
        return this;
    }

    /**
     * Sets the {@code Status} of the {@code Property} being built.
     *
     * @param status The status string ("sold" or "unsold").
     * @return The updated {@code PropertyBuilder}.
     */
    public PropertyBuilder withStatus(String status) {
        this.status = new Status(status);
        return this;
    }

    /**
     * Sets the {@code Type} of the {@code Property} being built.
     *
     * @param type The type string (e.g., HDB, Condo).
     * @return The updated {@code PropertyBuilder}.
     */
    public PropertyBuilder withType(String type) {
        this.type = new Type(type);
        return this;
    }

    /**
     * Sets the {@code Owner} of the {@code Property} being built.
     *
     * @param owner The owner ID string.
     * @return The updated {@code PropertyBuilder}.
     */
    public PropertyBuilder withOwner(String owner) {
        this.owner = new Owner(owner);
        return this;
    }

    /**
     * Builds and returns the {@code Property} object.
     *
     * @return A new {@code Property} object.
     */
    public Property build() {
        return new Property(address, bathroom, bedroom, floorArea, listing, postal, price, status, type, owner);
    }
}
