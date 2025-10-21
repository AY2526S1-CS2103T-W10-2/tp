package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.property.Property;
import seedu.address.model.uuid.Uuid;

/**
 * A UI component that displays information of a {@code Property}.
 */
public class PropertyCard extends UiPart<Region> {

    private static final String FXML = "PropertyListCard.fxml";

    public final Property property;

    @FXML
    private HBox cardPane;
    @FXML
    private Label propertyUuid;
    @FXML
    private Label propertyIndex;
    @FXML
    private Label propertyAddress;
    @FXML
    private Label address;
    @FXML
    private Label details;
    @FXML
    private Label price;
    @FXML
    private Label role;
    /**
     * Creates a {@code PropertyCard} with the given {@code Property} and index to display.
     */
    public PropertyCard(Property property, int displayedIndex) {
        super(FXML);
        this.property = property;
        propertyIndex.setText(displayedIndex + ".");
        propertyAddress.setText(property.getPropertyAddress().value);
        propertyUuid.setText("id: " + property.getUuid().getValue());

        // Format: "type • beds beds • baths baths • sqft sqft"
        details.setText(String.format("%s • %s beds • %s baths • %s sqft",
                property.getType().value,
                property.getBedroom().value,
                property.getBathroom().value,
                property.getFloorArea().value));

        // Format price with commas and status
        String formattedPrice = String.format("$%,d • %s • %s",
                Integer.parseInt(property.getPrice().value),
                property.getStatus().value,
                property.getListing().value);
        price.setText(formattedPrice);

        //placeholder for now (bug to resolve)
        role.setText("Role: ");
    }
}
