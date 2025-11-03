package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.PropertyBook;
import seedu.address.model.property.Property;

/**
 * A utility class containing a list of {@code Property} objects to be used in tests.
 */
public class TypicalProperties {

    public static final Property PROPERTY_ALPHA = new PropertyBuilderUtil().withUuid(1)
            .withPropertyAddress("123 Main St 5").withBathroom("2").withBedroom("3")
            .withFloorArea("120").withListing("sale")
            .withPostal("123456").withPrice("500000").withStatus("unavailable")
            .withType("HDB").withOwner("owner123").withBuyingContactIds().withSellingContactIds().build();

    public static final Property PROPERTY_ALPHA_VARIANT = new PropertyBuilderUtil().withUuid(1)
            .withPropertyAddress("123 Main St 5").withBathroom("2").withBedroom("3")
            .withFloorArea("120").withListing("rent").withPostal("123456")
            .withPrice("510000").withStatus("unavailable").withType("HDB")
            .withOwner("owner789").withBuyingContactIds().withSellingContactIds().build();

    public static final Property PROPERTY_BETA = new PropertyBuilderUtil().withUuid(2)
            .withPropertyAddress("456 Market Ave 9").withBathroom("1").withBedroom("2")
            .withFloorArea("80").withListing("rent").withPostal("654321")
            .withPrice("3500").withStatus("unavailable").withType("apartment")
            .withOwner("owner456").withBuyingContactIds().withSellingContactIds().build();

    public static final Property PROPERTY_GAMMA = new PropertyBuilderUtil().withUuid(3)
            .withPropertyAddress("321 Market St 9").withBathroom("3").withBedroom("4")
            .withFloorArea("150").withListing("rent").withPostal("654321")
            .withPrice("750000").withStatus("unavailable").withType("Condo")
            .withOwner("owner321").withBuyingContactIds().withSellingContactIds().build();

    private TypicalProperties() {} // prevents instantiation

    /**
     * Returns a {@code PropertyBook} with all the typical properties.
     */
    public static PropertyBook getTypicalPropertyBook() {
        PropertyBook pb = new PropertyBook();
        for (Property property : getTypicalProperties()) {
            pb.addProperty(property);
        }
        return pb;
    }

    public static List<Property> getTypicalProperties() {
        return new ArrayList<>(Arrays.asList(new PropertyBuilderUtil(PROPERTY_ALPHA).build(),
                new PropertyBuilderUtil(PROPERTY_BETA).build(),
                new PropertyBuilderUtil(PROPERTY_GAMMA).build()));
    }
}
