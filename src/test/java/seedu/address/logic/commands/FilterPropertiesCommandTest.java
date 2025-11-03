package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
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
import seedu.address.model.property.predicates.PropertyMatchesFilterPredicate;


/**
 * Tests for FilterPropertyCommand
 */
public class FilterPropertiesCommandTest {

    private Model model;

    private Property p1;
    private Property p2;
    private Property p3;

    @BeforeEach
    public void setUp() {
        model = new ModelManager();

        p1 = createProperty("123 Orchard Rd", "123000", "Condo", 3, 2,
                "100", "1000000", "available", "alice", "sale");
        p2 = createProperty("456 Bedok Ave", "456000", "HDB", 4, 3,
                "150", "750000", "unavailable", "bob", "sale");
        p3 = createProperty("789 Clementi St", "123456", "Landed", 5, 4,
                "180", "2000000", "available", "carol", "rent");

        model.addProperty(p1);
        model.addProperty(p2);
        model.addProperty(p3);
    }

    /**
     * Helper to create a Property using simplified fields.
     */
    private Property createProperty(
            String address, String postal, String type, int bedroom,
            int bathroom, String floorArea, String price, String status, String ownerName, String listing) {
        return new Property(
                null,
                new PropertyAddress(address),
                new Bathroom(String.valueOf(bathroom)),
                new Bedroom(String.valueOf(bedroom)),
                new FloorArea(floorArea),
                new Listing(listing),
                new Postal(postal),
                new Price(price),
                new Status(status),
                new Type(type),
                new Owner(ownerName),
                new HashSet<>(),
                new HashSet<>()
        );
    }

    @Test
    public void execute_filterByPostal_success() throws CommandException {
        PropertyMatchesFilterPredicate predicate =
                new PropertyMatchesFilterPredicate.Builder().withPostal("123000").build();

        FilterPropertiesCommand command = new FilterPropertiesCommand(predicate, Integer.MAX_VALUE, 0);
        CommandResult result = command.execute(model);

        List<Property> shown = model.getFilteredPropertyList();
        assertEquals(1, shown.size());
        assertTrue(shown.contains(p1));
        assertEquals("1 properties matched", result.getFeedbackToUser());
    }

    @Test
    public void execute_filterByType_success() throws CommandException {
        PropertyMatchesFilterPredicate predicate =
                new PropertyMatchesFilterPredicate.Builder().withType("Condo").build();

        FilterPropertiesCommand command = new FilterPropertiesCommand(predicate, Integer.MAX_VALUE, 0);
        CommandResult result = command.execute(model);

        List<Property> shown = model.getFilteredPropertyList();
        assertEquals(1, shown.size());
        assertTrue(shown.contains(p1));
        assertEquals("1 properties matched", result.getFeedbackToUser());
    }

    @Test
    public void execute_filterByStatus_success() throws CommandException {
        PropertyMatchesFilterPredicate predicate =
                new PropertyMatchesFilterPredicate.Builder().withStatus("available").build();

        FilterPropertiesCommand command = new FilterPropertiesCommand(predicate, Integer.MAX_VALUE, 0);
        CommandResult result = command.execute(model);

        List<Property> shown = model.getFilteredPropertyList();
        assertEquals(2, shown.size());
        assertTrue(shown.contains(p1));
        assertTrue(shown.contains(p3));
        assertEquals("2 properties matched", result.getFeedbackToUser());
    }

    @Test
    public void execute_filterByBedroom_success() throws CommandException {
        PropertyMatchesFilterPredicate predicate =
                new PropertyMatchesFilterPredicate.Builder().withBedroom("4").build();

        FilterPropertiesCommand command = new FilterPropertiesCommand(predicate, Integer.MAX_VALUE, 0);
        CommandResult result = command.execute(model);

        List<Property> shown = model.getFilteredPropertyList();
        assertEquals(1, shown.size());
        assertTrue(shown.contains(p2));
        assertEquals("1 properties matched", result.getFeedbackToUser());
    }

    @Test
    public void execute_filterByFloorArea_success() throws CommandException {
        PropertyMatchesFilterPredicate predicate =
                new PropertyMatchesFilterPredicate.Builder().withFloorArea("100").build();

        FilterPropertiesCommand command = new FilterPropertiesCommand(predicate, Integer.MAX_VALUE, 0);
        CommandResult result = command.execute(model);

        List<Property> shown = model.getFilteredPropertyList();
        assertEquals(1, shown.size());
        assertTrue(shown.contains(p1));
        assertEquals("1 properties matched", result.getFeedbackToUser());
    }

    @Test
    public void execute_filterByPrice_success() throws CommandException {
        PropertyMatchesFilterPredicate predicate =
                new PropertyMatchesFilterPredicate.Builder().withPrice("1000000").build();

        FilterPropertiesCommand command = new FilterPropertiesCommand(predicate, Integer.MAX_VALUE, 0);
        CommandResult result = command.execute(model);

        List<Property> shown = model.getFilteredPropertyList();
        assertEquals(2, shown.size());
        assertTrue(shown.contains(p1));
        assertEquals("2 properties matched", result.getFeedbackToUser());
    }

    @Test
    public void execute_filterByOwner_success() throws CommandException {
        PropertyMatchesFilterPredicate predicate =
                new PropertyMatchesFilterPredicate.Builder().withOwner("carol").build();

        FilterPropertiesCommand command = new FilterPropertiesCommand(predicate, Integer.MAX_VALUE, 0);
        CommandResult result = command.execute(model);

        List<Property> shown = model.getFilteredPropertyList();
        assertEquals(1, shown.size());
        assertTrue(shown.contains(p3));
        assertEquals("1 properties matched", result.getFeedbackToUser());
    }

    @Test
    public void execute_filterByListing_success() throws CommandException {
        PropertyMatchesFilterPredicate predicate =
                new PropertyMatchesFilterPredicate.Builder().withListing("sale").build();

        FilterPropertiesCommand command = new FilterPropertiesCommand(predicate, Integer.MAX_VALUE, 0);
        CommandResult result = command.execute(model);

        List<Property> shown = model.getFilteredPropertyList();
        assertEquals(2, shown.size());
        assertTrue(shown.contains(p1));
        assertEquals("2 properties matched", result.getFeedbackToUser());
    }

    @Test
    public void execute_filterWithLimitAndOffset_success() throws CommandException {
        PropertyMatchesFilterPredicate predicate =
                new PropertyMatchesFilterPredicate.Builder().withStatus("available").build();

        FilterPropertiesCommand command = new FilterPropertiesCommand(predicate, 1, 1);
        CommandResult result = command.execute(model);

        List<Property> shown = model.getFilteredPropertyList();
        assertEquals(1, shown.size());
        assertEquals("1 properties matched", result.getFeedbackToUser());
    }

    @Test
    public void equals() {
        PropertyMatchesFilterPredicate firstPredicate =
                new PropertyMatchesFilterPredicate.Builder().withType("condo").build();
        PropertyMatchesFilterPredicate secondPredicate =
                new PropertyMatchesFilterPredicate.Builder().withType("hdb").build();

        FilterPropertiesCommand firstCommand = new FilterPropertiesCommand(firstPredicate, 20, 0);
        FilterPropertiesCommand secondCommand = new FilterPropertiesCommand(secondPredicate, 20, 0);

        assertEquals(firstCommand, new FilterPropertiesCommand(firstPredicate, 20, 0));
        assertNotEquals(firstCommand, secondCommand);
        assertNotEquals(firstCommand, null);
        assertNotEquals(firstCommand, "some string");
    }
}

