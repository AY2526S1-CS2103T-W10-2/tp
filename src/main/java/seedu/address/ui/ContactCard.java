package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.contact.Contact;
import seedu.address.model.uuid.Uuid;

/**
 * An UI component that displays information of a {@code Contact}.
 */
public class ContactCard extends UiPart<Region> {

    private static final String FXML = "ContactListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Contact contact;

    @FXML
    private HBox cardPane;
    @FXML
    private Label uuid;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label budgetMin;
    @FXML
    private Label budgetMax;
    @FXML
    private Label notes;
    @FXML
    private Label status;
    @FXML
    private Label buyingIds;
    @FXML
    private Label sellingIds;

    /**
     * Creates a {@code ContactCard} with the given {@code Contact} and index to display.
     */
    public ContactCard(Contact contact, int displayedIndex) {
        super(FXML);
        this.contact = contact;

        // Always displayed fields - ID combined with name
        id.setText(displayedIndex + ". ");
        name.setText(contact.getName().fullName + " (ID: " + contact.getUuid().getValue() + ")");
        phone.setText("Phone: " + contact.getPhone().value);
        budgetMin.setText("Budget Minimum: $" + contact.getBudgetMin().toString());
        budgetMax.setText("Budget Maximum: $" + contact.getBudgetMax().toString());

        // Hide the separate UUID label since we combined it with name
        hideLabel(uuid);

        // Conditionally displayed fields
        setLabelIfNotEmpty(email, "Email: ", contact.getEmail().value);
        setLabelIfNotEmpty(address, "Address: ", contact.getAddress().value);
        setLabelIfNotEmpty(notes, "Notes: ", contact.getNotes().toString());
        setLabelIfNotEmpty(status, "Status: ", contact.getStatus().toString());

        // Property IDs
        setIdsIfNotEmpty(buyingIds, "Buying Property IDs: ", contact.getBuyingPropertyIds());
        setIdsIfNotEmpty(sellingIds, "Selling Property IDs: ", contact.getSellingPropertyIds());
    }

    /**
     * Hides a label by making it invisible and unmanaged.
     *
     * @param label The label to hide.
     */
    private void hideLabel(Label label) {
        label.setVisible(false);
        label.setManaged(false);
    }

    /**
     * Sets the label text if the value is not empty, otherwise hides the label.
     *
     * @param label The label to set.
     * @param prefix The prefix text (e.g., "Email: ").
     * @param value The value to display.
     */
    private void setLabelIfNotEmpty(Label label, String prefix, String value) {
        if (value.isEmpty()) {
            hideLabel(label);
        } else {
            label.setText(prefix + value);
        }
    }

    /**
     * Sets property IDs if not empty, otherwise hides the label.
     *
     * @param label The label to set.
     * @param prefix The prefix text
     * @param ids The set of UUIDs to display.
     */
    private void setIdsIfNotEmpty(Label label, String prefix, java.util.Set<Uuid> ids) {
        if (ids.isEmpty()) {
            hideLabel(label);
        } else {
            label.setText(prefix + Uuid.getGuiSetDisplayAsString(ids));
        }
    }
}
