package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.Uuid;
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
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Property objects.
 */
public class PropertyBuilderUtil {

    public static final String DEFAULT_ID = null;
    public static final String DEFAULT_PROPERTY_ADDRESS = "123 Kent Ridge Road";
    public static final String DEFAULT_BATHROOM = "1";
    public static final String DEFAULT_BEDROOM = "2";
    public static final String DEFAULT_FLOORAREA = "2000";
    public static final String DEFAULT_LISTING = "sale";
    public static final String DEFAULT_POSTAL = "886231";
    public static final String DEFAULT_PRICE = "210000";
    public static final String DEFAULT_STATUS = "sold";
    public static final String DEFAULT_TYPE = "landed";
    public static final String DEFAULT_OWNER = "owner123";
    public static final Set<Uuid> DEFAULT_BUYING_PERSON_IDS = new HashSet<>();
    public static final Set<Uuid> DEFAULT_SELLING_PERSON_IDS = new HashSet<>();

    private String id;
    private PropertyAddress propertyAddress;
    private Bathroom bathroom;
    private Bedroom bedroom;
    private FloorArea floorArea;
    private Listing listing;
    private Postal postal;
    private Price price;
    private Status status;
    private Type type;
    private Owner owner;
    private Set<Uuid> buyingPersonIds;
    private Set<Uuid> sellingPersonIds;

    /**
     * Creates a {@code PropertyBuilderUtil} with the default details.
     */
    public PropertyBuilderUtil() {
        id = DEFAULT_ID;
        propertyAddress = new PropertyAddress(DEFAULT_PROPERTY_ADDRESS);
        bathroom = new Bathroom(DEFAULT_BATHROOM);
        bedroom = new Bedroom(DEFAULT_BEDROOM);
        floorArea = new FloorArea(DEFAULT_FLOORAREA);
        listing = new Listing(DEFAULT_LISTING);
        postal = new Postal(DEFAULT_POSTAL);
        price = new Price(DEFAULT_PRICE);
        status = new Status(DEFAULT_STATUS);
        type = new Type(DEFAULT_TYPE);
        owner = new Owner(DEFAULT_OWNER);
        buyingPersonIds = DEFAULT_BUYING_PERSON_IDS;
        sellingPersonIds = DEFAULT_SELLING_PERSON_IDS;
    }

    /**
     * Initializes the PropertyBuilderUtil with the data of {@code propertyToCopy}.
     */
    public PropertyBuilderUtil(Property propertyToCopy) {
        id = propertyToCopy.getId();
        propertyAddress = new PropertyAddress(propertyToCopy.getPropertyAddress().value);
        bathroom = new Bathroom(propertyToCopy.getBathroom().value);
        bedroom = new Bedroom(propertyToCopy.getBedroom().value);
        floorArea = new FloorArea(propertyToCopy.getFloorArea().value);
        listing = new Listing(propertyToCopy.getListing().value);
        postal = new Postal(propertyToCopy.getPostal().value);
        price = new Price(propertyToCopy.getPrice().value);
        status = new Status(propertyToCopy.getStatus().value);
        type = new Type(propertyToCopy.getType().value);
        owner = new Owner(propertyToCopy.getOwner().value);
        buyingPersonIds = new HashSet<>(propertyToCopy.getBuyingPersonIds());
        sellingPersonIds = new HashSet<>(propertyToCopy.getSellingPersonIds());
    }

    /**
     * Sets the {@code PropertyAddress} of the {@code Property} that we are building.
     */
    public PropertyBuilderUtil withPropertyAddress(String address) {
        this.propertyAddress = new PropertyAddress(address);
        return this;
    }


    /**
     * Sets the {@code id} of the {@code Property} that we are building.
     */
    public PropertyBuilderUtil withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * Sets the {@code Bathroom} of the {@code Property} that we are building.
     */
    public PropertyBuilderUtil withBathroom(String bathroom) {
        this.bathroom = new Bathroom(bathroom);
        return this;
    }

    /**
     * Sets the {@code Bedroom} of the {@code Property} that we are building.
     */
    public PropertyBuilderUtil withBedroom(String bedroom) {
        this.bedroom = new Bedroom(bedroom);
        return this;
    }

    /**
     * Sets the {@code FloorArea} of the {@code Property} that we are building.
     */
    public PropertyBuilderUtil withFloorArea(String floorArea) {
        this.floorArea = new FloorArea(floorArea);
        return this;
    }

    /**
     * Sets the {@code Listing} of the {@code Property} that we are building.
     */
    public PropertyBuilderUtil withListing(String listing) {
        this.listing = new Listing(listing);
        return this;
    }

    /**
     * Sets the {@code Postal} of the {@code Property} that we are building.
     */
    public PropertyBuilderUtil withPostal(String postal) {
        this.postal = new Postal(postal);
        return this;
    }

    /**
     * Sets the {@code Price} of the {@code Property} that we are building.
     */
    public PropertyBuilderUtil withPrice(String price) {
        this.price = new Price(price);
        return this;
    }

    /**
     * Sets the {@code Status} of the {@code Property} that we are building.
     */
    public PropertyBuilderUtil withStatus(String status) {
        this.status = new Status(status);
        return this;
    }

    /**
     * Sets the {@code Type} of the {@code Property} that we are building.
     */
    public PropertyBuilderUtil withType(String type) {
        this.type = new Type(type);
        return this;
    }

    /**
     * Sets the {@code Owner} of the {@code Property} that we are building.
     */
    public PropertyBuilderUtil withOwner(String owner) {
        this.owner = new Owner(owner);
        return this;
    }

    /**
     * Empties the {@code buyingPersonIds} of the {@code Property} that we are building.
     * Used to avoid ambiguity with varargs method.
     */
    public PropertyBuilderUtil withBuyingPersonIds() {
        this.buyingPersonIds = new HashSet<>();
        return this;
    }

    /**
     * Parses the {@code ids} into a {@code Set<Uuid>} and set it to the {@code Person} that we are building.
     */
    public PropertyBuilderUtil withBuyingPersonIds(int ... ids) {
        this.buyingPersonIds = SampleDataUtil.getUuidSet(ids);
        return this;
    }

    /**
     * Sets the {@code buyingPersonIds} of the {@code Property} that we are building.
     */
    public PropertyBuilderUtil withBuyingPersonIds(Uuid ... ids) {
        this.buyingPersonIds = Set.of(ids);
        return this;
    }

    /**
     * Empties the {@code sellingPersonIds} of the {@code Property} that we are building.
     * Used to avoid ambiguity with varargs method.
     */
    public PropertyBuilderUtil withSellingPersonIds() {
        this.sellingPersonIds = new HashSet<>();
        return this;
    }

    /**
     * Parses the {@code ids} into a {@code Set<Uuid>} and set it to the {@code Person} that we are building.
     */
    public PropertyBuilderUtil withSellingPersonIds(int ... ids) {
        this.sellingPersonIds = SampleDataUtil.getUuidSet(ids);
        return this;
    }

    /**
     * Sets the {@code buyingPersonIds} of the {@code Property} that we are building.
     */
    public PropertyBuilderUtil withSellingPersonIds(Uuid ... ids) {
        this.sellingPersonIds = Set.of(ids);
        return this;
    }

    /**
     * Builds the {@code Property} that we are building.
     */
    public Property build() {
        return new Property(id, propertyAddress, bathroom, bedroom, floorArea, listing, postal, price, status,
                type, owner, buyingPersonIds, sellingPersonIds);
    }

}
