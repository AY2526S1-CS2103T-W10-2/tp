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

    public static final Property PROPERTY_ALPHA = new PropertyBuilderUtil().withId("a")
            .withPropertyAddress("123 Main St 5").withBathroom("2").withBedroom("3")
            .withFloorArea("120").withListing("sale")
            .withPostal("123456").withPrice("500000").withStatus("unsold")
            .withType("HDB").withOwner("owner123").withBuyingPersonIds().withSellingPersonIds().build();

    public static final Property PROPERTY_ALPHA_VARIANT = new PropertyBuilderUtil().withId("b")
            .withPropertyAddress("123 Main St 5").withBathroom("2").withBedroom("3")
            .withFloorArea("120").withListing("rent").withPostal("123456")
            .withPrice("510000").withStatus("unsold").withType("HDB")
            .withOwner("owner789").withBuyingPersonIds().withSellingPersonIds().build();

    public static final Property PROPERTY_BETA = new PropertyBuilderUtil().withId("c")
            .withPropertyAddress("456 Market Ave 9").withBathroom("1").withBedroom("2")
            .withFloorArea("80").withListing("rent").withPostal("654321")
            .withPrice("3500").withStatus("unsold").withType("apartment")
            .withOwner("owner456").withBuyingPersonIds().withSellingPersonIds().build();

    public static final Property PROPERTY_GAMMA = new PropertyBuilderUtil().withId("d")
            .withPropertyAddress("321 Market St 9").withBathroom("3").withBedroom("4")
            .withFloorArea("150").withListing("rent").withPostal("654321")
            .withPrice("750000").withStatus("unsold").withType("Condo")
            .withOwner("owner321").withBuyingPersonIds().withSellingPersonIds().build();

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
        return new ArrayList<>(Arrays.asList(PROPERTY_ALPHA, PROPERTY_BETA, PROPERTY_GAMMA));
    }
}
