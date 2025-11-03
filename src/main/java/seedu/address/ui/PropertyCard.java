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
    private Label price;
    @FXML
    private Label postal;
    @FXML
    private Label type;
    @FXML
    private Label bedroom;
    @FXML
    private Label bathroom;
    @FXML
    private Label floorArea;
    @FXML
    private Label status;
    @FXML
    private Label owner;
    @FXML
    private Label buyerIds;
    @FXML
    private Label sellerIds;

    /**
     * Creates a {@code PropertyCard} with the given {@code Property} and index to display.
     */
    public PropertyCard(Property property, int displayedIndex) {
        super(FXML);
        this.property = property;
        propertyIndex.setText(displayedIndex + ".");
        propertyAddress.setText(property.getPropertyAddress().value);
        propertyUuid.setText("id: " + property.getUuid().getValue());
        postal.setText("Postal Code: " + property.getPostal().value);
        type.setText("Type: " + property.getType().value);
        bedroom.setText("Bedrooms: " + property.getBedroom().value);
        bathroom.setText("Bathrooms: " + property.getBathroom().value);
        floorArea.setText("Floor Area: " + property.getFloorArea().value + " sqft");
        price.setText("Price: $" + String.format("%,d", Integer.parseInt(property.getPrice().value)));
        status.setText("Status: " + property.getStatus().value);
        owner.setText("Owner ID: " + property.getOwner().value);

        setIdsIfNotEmpty(buyerIds, "Buyer IDs: ", property.getBuyingContactIds());
        setIdsIfNotEmpty(sellerIds, "Seller IDs: ", property.getSellingContactIds());
    }


    /**
     * Hides a label by making it invisible and unmanaged.
     */
    private void hideLabel(Label label) {
        label.setVisible(false);
        label.setManaged(false);
    }

    /**
     * Sets property IDs if not empty, otherwise hides the label.
     */
    private void setIdsIfNotEmpty(Label label, String prefix, java.util.Set<Uuid> ids) {
        if (ids.isEmpty()) {
            hideLabel(label);
        } else {
            label.setText(prefix + Uuid.getGuiSetDisplayAsString(ids));
        }
    }
}
