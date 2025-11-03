package seedu.address.testutil;

import static seedu.address.model.uuid.Uuid.StoredItem.CONTACT;
import static seedu.address.model.uuid.Uuid.StoredItem.PROPERTY;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.property.Bathroom;
import seedu.address.model.property.Bedroom;
import seedu.address.model.property.FloorArea;
import seedu.address.model.property.Owner;
import seedu.address.model.property.Postal;
import seedu.address.model.property.Price;
import seedu.address.model.property.Property;
import seedu.address.model.property.PropertyAddress;
import seedu.address.model.property.Status;
import seedu.address.model.property.Type;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.model.uuid.Uuid;

/**
 * A utility class to help with building Property objects.
 */
public class PropertyBuilderUtil {

    public static final Uuid DEFAULT_ID = new Uuid(1, PROPERTY);
    public static final String DEFAULT_PROPERTY_ADDRESS = "123 Kent Ridge Road";
    public static final String DEFAULT_BATHROOM = "1";
    public static final String DEFAULT_BEDROOM = "2";
    public static final String DEFAULT_FLOORAREA = "2000";
    public static final String DEFAULT_POSTAL = "886231";
    public static final String DEFAULT_PRICE = "210000";
    public static final String DEFAULT_STATUS = "unavailable";
    public static final String DEFAULT_TYPE = "landed";
    public static final String DEFAULT_OWNER = "owner123";
    public static final Set<Uuid> DEFAULT_BUYING_CONTACT_IDS = new HashSet<>();
    public static final Set<Uuid> DEFAULT_SELLING_CONTACT_IDS = new HashSet<>();

    private PropertyAddress propertyAddress;
    private Bathroom bathroom;
    private Bedroom bedroom;
    private FloorArea floorArea;
    private Postal postal;
    private Price price;
    private Status status;
    private Type type;
    private Owner owner;
    private Uuid uuid;
    private Set<Uuid> buyingContactIds;
    private Set<Uuid> sellingContactIds;

    /**
     * Creates a {@code PropertyBuilderUtil} with the default details.
     */
    public PropertyBuilderUtil() {
        uuid = DEFAULT_ID;
        propertyAddress = new PropertyAddress(DEFAULT_PROPERTY_ADDRESS);
        bathroom = new Bathroom(DEFAULT_BATHROOM);
        bedroom = new Bedroom(DEFAULT_BEDROOM);
        floorArea = new FloorArea(DEFAULT_FLOORAREA);
        postal = new Postal(DEFAULT_POSTAL);
        price = new Price(DEFAULT_PRICE);
        status = new Status(DEFAULT_STATUS);
        type = new Type(DEFAULT_TYPE);
        owner = new Owner(DEFAULT_OWNER);
        buyingContactIds = DEFAULT_BUYING_CONTACT_IDS;
        sellingContactIds = DEFAULT_SELLING_CONTACT_IDS;
    }

    /**
     * Initializes the PropertyBuilderUtil with the data of {@code propertyToCopy}.
     */
    public PropertyBuilderUtil(Property propertyToCopy) {
        uuid = propertyToCopy.getUuid();
        propertyAddress = new PropertyAddress(propertyToCopy.getPropertyAddress().value);
        bathroom = new Bathroom(propertyToCopy.getBathroom().value);
        bedroom = new Bedroom(propertyToCopy.getBedroom().value);
        floorArea = new FloorArea(propertyToCopy.getFloorArea().value);
        postal = new Postal(propertyToCopy.getPostal().value);
        price = new Price(propertyToCopy.getPrice().value);
        status = new Status(propertyToCopy.getStatus().value);
        type = new Type(propertyToCopy.getType().value);
        owner = new Owner(propertyToCopy.getOwner().value);
        buyingContactIds = new HashSet<>(propertyToCopy.getBuyingContactIds());
        sellingContactIds = new HashSet<>(propertyToCopy.getSellingContactIds());
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
    public PropertyBuilderUtil withUuid(int uuid) {
        this.uuid = new Uuid(uuid, PROPERTY);
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
     * Empties the {@code buyingContactIds} of the {@code Property} that we are building.
     * Used to avoid ambiguity with varargs method.
     */
    public PropertyBuilderUtil withBuyingContactIds() {
        this.buyingContactIds = new HashSet<>();
        return this;
    }

    /**
     * Parses the {@code ids} into a {@code Set<Uuid>} and set it to the {@code Contact} that we are building.
     */
    public PropertyBuilderUtil withBuyingContactIds(int ... ids) {
        this.buyingContactIds = SampleDataUtil.getUuidSet(CONTACT, ids);
        return this;
    }

    /**
     * Sets the {@code buyingContactIds} of the {@code Property} that we are building.
     */
    public PropertyBuilderUtil withBuyingContactIds(Uuid ... ids) {
        this.buyingContactIds = Set.of(ids);
        return this;
    }

    /**
     * Empties the {@code sellingContactIds} of the {@code Property} that we are building.
     * Used to avoid ambiguity with varargs method.
     */
    public PropertyBuilderUtil withSellingContactIds() {
        this.sellingContactIds = new HashSet<>();
        return this;
    }

    /**
     * Parses the {@code ids} into a {@code Set<Uuid>} and set it to the {@code Contact} that we are building.
     */
    public PropertyBuilderUtil withSellingContactIds(int ... ids) {
        this.sellingContactIds = SampleDataUtil.getUuidSet(CONTACT, ids);
        return this;
    }

    /**
     * Sets the {@code buyingContactIds} of the {@code Property} that we are building.
     */
    public PropertyBuilderUtil withSellingContactIds(Uuid ... ids) {
        this.sellingContactIds = Set.of(ids);
        return this;
    }

    /**
     * Builds the {@code Property} that we are building.
     */
    public Property build() {
        return new Property(uuid, propertyAddress, bathroom, bedroom, floorArea, postal, price, status,
                type, owner, buyingContactIds, sellingContactIds);
    }

}
