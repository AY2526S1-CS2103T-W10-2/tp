package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
public class FilterPropertyCommandTest {

    private Model model;

    private Property p1;
    private Property p2;
    private Property p3;

    @BeforeEach
    public void setUp() {
        model = new ModelManager();

        p1 = createProperty("123 Orchard Rd", "Condo", 3, 2, "1000000", "listed", "alice");
        p2 = createProperty("456 Bedok Ave", "HDB", 4, 3, "750000", "sold", "bob");
        p3 = createProperty("789 Clementi St", "Landed", 5, 4, "2000000", "listed", "carol");

        model.addProperty(p1);
        model.addProperty(p2);
        model.addProperty(p3);
    }

    /**
     * Helper to create a Property using simplified fields.
     */
    private Property createProperty(String address, String type, int bedroom,
                                    int bathroom, String price, String status, String ownerName) {
        return new Property(
                new PropertyAddress(address),
                new Bathroom(String.valueOf(bathroom)),
                new Bedroom(String.valueOf(bedroom)),
                new FloorArea("100"),
                new Listing("sale"),
                new Postal("123456"),
                new Price(price),
                new Status(status),
                new Type(type),
                new Owner(ownerName)
        );
    }

    @Test
    public void execute_filterByType_success() throws CommandException {
        PropertyMatchesFilterPredicate predicate =
                new PropertyMatchesFilterPredicate.Builder().withType("Condo").build();

        FilterPropertyCommand command = new FilterPropertyCommand(predicate, 20, 0);
        CommandResult result = command.execute(model);

        List<Property> shown = model.getFilteredPropertyList();
        assertEquals(1, shown.size());
        assertTrue(shown.contains(p1));
        assertEquals("1 properties matched (showing 1–1)", result.getFeedbackToUser());
    }

    @Test
    public void execute_filterByStatus_success() throws CommandException {
        PropertyMatchesFilterPredicate predicate =
                new PropertyMatchesFilterPredicate.Builder().withStatus("listed").build();

        FilterPropertyCommand command = new FilterPropertyCommand(predicate, 20, 0);
        CommandResult result = command.execute(model);

        List<Property> shown = model.getFilteredPropertyList();
        assertEquals(2, shown.size());
        assertTrue(shown.contains(p1));
        assertTrue(shown.contains(p3));
        assertEquals("2 properties matched (showing 1–2)", result.getFeedbackToUser());
    }

    @Test
    public void execute_filterByBedroom_success() throws CommandException {
        PropertyMatchesFilterPredicate predicate =
                new PropertyMatchesFilterPredicate.Builder().withBedroom("4").build();

        FilterPropertyCommand command = new FilterPropertyCommand(predicate, 20, 0);
        CommandResult result = command.execute(model);

        List<Property> shown = model.getFilteredPropertyList();
        assertEquals(1, shown.size());
        assertTrue(shown.contains(p2));
        assertEquals("1 properties matched (showing 1–1)", result.getFeedbackToUser());
    }

    @Test
    public void execute_filterByPrice_success() throws CommandException {
        PropertyMatchesFilterPredicate predicate =
                new PropertyMatchesFilterPredicate.Builder().withPrice("1000000").build();

        FilterPropertyCommand command = new FilterPropertyCommand(predicate, 20, 0);
        CommandResult result = command.execute(model);

        List<Property> shown = model.getFilteredPropertyList();
        assertEquals(1, shown.size());
        assertTrue(shown.contains(p1));
        assertEquals("1 properties matched (showing 1–1)", result.getFeedbackToUser());
    }

    @Test
    public void execute_filterByOwner_success() throws CommandException {
        PropertyMatchesFilterPredicate predicate =
                new PropertyMatchesFilterPredicate.Builder().withOwner("carol").build();

        FilterPropertyCommand command = new FilterPropertyCommand(predicate, 20, 0);
        CommandResult result = command.execute(model);

        List<Property> shown = model.getFilteredPropertyList();
        assertEquals(1, shown.size());
        assertTrue(shown.contains(p3));
        assertEquals("1 properties matched (showing 1–1)", result.getFeedbackToUser());
    }

    @Test
    public void execute_filterWithLimitAndOffset_success() throws CommandException {
        PropertyMatchesFilterPredicate predicate =
                new PropertyMatchesFilterPredicate.Builder().withStatus("listed").build();

        FilterPropertyCommand command = new FilterPropertyCommand(predicate, 1, 1);
        CommandResult result = command.execute(model);

        List<Property> shown = model.getFilteredPropertyList();
        assertEquals(1, shown.size());
        assertEquals("2 properties matched (showing 2–2)", result.getFeedbackToUser());
    }
}

