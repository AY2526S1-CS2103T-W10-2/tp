package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import seedu.address.model.contact.BudgetMax;
import seedu.address.model.contact.BudgetMin;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.ContactAddress;
import seedu.address.model.contact.ContactStatus;
import seedu.address.model.contact.Email;
import seedu.address.model.contact.Name;
import seedu.address.model.contact.Notes;
import seedu.address.model.contact.Phone;
import seedu.address.model.contact.Tag;
import seedu.address.model.uuid.Uuid;

public class ContactCardTest extends ApplicationTest {

    private ContactCard contactCard;
    private Contact sampleContact;

    @BeforeEach
    public void setUp() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(() -> new javafx.application.Application() {
            @Override
            public void start(Stage primaryStage) { }
        });
        FxToolkit.showStage();
        WaitForAsyncUtils.waitForFxEvents(20);
    }

    @AfterEach
    public void tearDown() throws Exception {
        FxToolkit.cleanupStages();
    }

    @Test
    public void contactCard_displayCompleteContact_showAllFields() {
        sampleContact = new Contact(
                new Uuid(1, Uuid.StoredItem.CONTACT),
                new Name("John Doe"),
                new Phone("98765432"),
                new Email("john.doe@example.com"),
                new ContactAddress("Blk 123 Test Rd, #01-01"),
                Set.of(new Tag("buyer"), new Tag("seller")),
                new BudgetMin("500"),
                new BudgetMax("1500"),
                new Notes("Test notes"),
                new ContactStatus("Active"),
                Set.of(new Uuid(1, Uuid.StoredItem.PROPERTY)),
                Set.of(new Uuid(2, Uuid.StoredItem.PROPERTY))
        );

        contactCard = new ContactCard(sampleContact, 1);

        // Check basic info
        assertEquals("ID: 1", contactCard.getUuid().getText());
        assertEquals("1. ", contactCard.getId().getText());
        assertEquals("John Doe", contactCard.getName().getText());
        assertEquals("Phone: 98765432", contactCard.getPhone().getText());

        // Check optional fields visible and correct
        assertEquals("Email: john.doe@example.com", contactCard.getEmail().getText());
        assertEquals("Address: Blk 123 Test Rd, #01-01", contactCard.getAddress().getText());
        assertEquals("Notes: Test notes", contactCard.getNotes().getText());
        assertEquals("Status: Active", contactCard.getStatus().getText());
        assertEquals("Budget Minimum: $500", contactCard.getBudgetMin().getText());
        assertEquals("Budget Maximum: $1500", contactCard.getBudgetMax().getText());

        // Check tags
        FlowPane tagsFlowPane = contactCard.getTags();
        assertEquals(2, tagsFlowPane.getChildren().size());
        Label firstTag = (Label) tagsFlowPane.getChildren().get(0);
        Label secondTag = (Label) tagsFlowPane.getChildren().get(1);
        assertEquals("buyer", firstTag.getText());
        assertEquals("seller", secondTag.getText());

        // Check buying and selling property ids
        assertTrue(contactCard.getBuyingIds().isVisible());
        assertTrue(contactCard.getSellingIds().isVisible());
        assertTrue(contactCard.getBuyingIds().getText().contains("1"));
        assertTrue(contactCard.getSellingIds().getText().contains("2"));
    }

    @Test
    public void contactCard_displayEmptyFields_hidesLabels() {
        sampleContact = new Contact(
                new Uuid(2, Uuid.StoredItem.CONTACT),
                new Name("Jane Smith"),
                new Phone("12345678"),
                new Email(""),
                new ContactAddress(""),
                new HashSet<>(),
                new BudgetMin(""),
                new BudgetMax(""),
                new Notes(""),
                new ContactStatus(""),
                new HashSet<>(),
                new HashSet<>()
        );

        contactCard = new ContactCard(sampleContact, 2);

        // Optional fields should be hidden
        assertFalse(contactCard.getEmail().isVisible());
        assertFalse(contactCard.getAddress().isVisible());
        assertFalse(contactCard.getNotes().isVisible());
        assertFalse(contactCard.getStatus().isVisible());

        // Tags should be hidden
        assertFalse(contactCard.getTags().isVisible());

        // Property IDs labels should be hidden
        assertFalse(contactCard.getBuyingIds().isVisible());
        assertFalse(contactCard.getSellingIds().isVisible());
    }
}
