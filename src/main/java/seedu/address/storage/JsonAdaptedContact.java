package seedu.address.storage;

import static seedu.address.model.uuid.Uuid.StoredItem.CONTACT;
import static seedu.address.model.uuid.Uuid.StoredItem.PROPERTY;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.contact.BudgetMax;
import seedu.address.model.contact.BudgetMin;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.ContactAddress;
import seedu.address.model.contact.ContactStatus;
import seedu.address.model.contact.Email;
import seedu.address.model.contact.Name;
import seedu.address.model.contact.Notes;
import seedu.address.model.contact.Phone;
import seedu.address.model.uuid.Uuid;


/**
 * Jackson-friendly version of {@link Contact}.
 */
class JsonAdaptedContact {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Contact's %s field is missing!";

    private final Integer uuid;
    private final String name;
    private final String phone;
    private final String email;
    private final String address;
    private final String budgetMin;
    private final String budgetMax;
    private final String notes;
    private final String status;
    private final List<Integer> buyingPropertyIds = new ArrayList<>();
    private final List<Integer> sellingPropertyIds = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedContact} with the given contact details.
     */
    @JsonCreator
    public JsonAdaptedContact(@JsonProperty("uuid") Integer uuid,
                              @JsonProperty("name") String name,
                              @JsonProperty("phone") String phone,
                              @JsonProperty("email") String email,
                              @JsonProperty("address") String address,
                              @JsonProperty("budgetMin") String budgetMin,
                              @JsonProperty("budgetMax") String budgetMax,
                              @JsonProperty("notes") String notes,
                              @JsonProperty("status") String status,
                              @JsonProperty("buyingPropertyIds") List<Integer> buyingPropertyIds,
                              @JsonProperty("sellingPropertyIds") List<Integer> sellingPropertyIds) {
        this.uuid = uuid;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.budgetMin = budgetMin;
        this.budgetMax = budgetMax;
        this.notes = notes;
        this.status = status;
        if (buyingPropertyIds != null) {
            this.buyingPropertyIds.addAll(buyingPropertyIds);
        }
        if (sellingPropertyIds != null) {
            this.sellingPropertyIds.addAll(sellingPropertyIds);
        }
    }

    /**
     * Converts a given {@code Contact} into this class for Jackson use.
     */
    public JsonAdaptedContact(Contact source) {
        uuid = source.getUuid().getValue();
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        budgetMin = source.getBudgetMin().value;
        budgetMax = source.getBudgetMax().value;
        notes = source.getNotes().value;
        status = source.getStatus().value;
        buyingPropertyIds.addAll(source
                .getBuyingPropertyIds()
                .stream()
                .map(id -> id.getValue())
                .toList());
        sellingPropertyIds.addAll(source
                .getSellingPropertyIds()
                .stream()
                .map(id -> id.getValue())
                .toList());
    }

    /**
     * Converts this Jackson-friendly adapted contact object into the model's {@code Contact} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted contact.
     */
    public Contact toModelType() throws IllegalValueException {

        if (uuid == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Uuid"));
        }
        final Uuid modelUuid = new Uuid(uuid, CONTACT);

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                                            ContactAddress.class.getSimpleName()));
        }
        if (!ContactAddress.isValidAddress(address)) {
            throw new IllegalValueException(ContactAddress.MESSAGE_CONSTRAINTS);
        }
        final ContactAddress modelAddress = new ContactAddress(address);

        if (budgetMin == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "BudgetMin"));
        }
        BudgetMin modelBudgetMin;
        try {
            modelBudgetMin = new BudgetMin(budgetMin);
        } catch (IllegalArgumentException e) {
            throw new IllegalValueException(BudgetMin.MESSAGE_CONSTRAINTS);
        }

        if (budgetMax == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "BudgetMax"));
        }
        BudgetMax modelBudgetMax;
        try {
            modelBudgetMax = new BudgetMax(budgetMax);
        } catch (IllegalArgumentException e) {
            throw new IllegalValueException(BudgetMax.MESSAGE_CONSTRAINTS);
        }

        if (notes == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Notes"));
        }
        final Notes modelNotes = new Notes(notes);

        if (status == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "ContactStatus"));
        }
        if (!ContactStatus.isValidStatus(status)) {
            throw new IllegalValueException(ContactStatus.MESSAGE_CONSTRAINTS);
        }
        final ContactStatus modelStatus = new ContactStatus(status);

        final List<Uuid> tempBuyingPropertyIds = new ArrayList<>();
        for (Integer id : this.buyingPropertyIds) {
            tempBuyingPropertyIds.add(new Uuid(id, PROPERTY));
        }
        final Set<Uuid> modelBuyingPropertyIds = new HashSet<>(tempBuyingPropertyIds);

        final List<Uuid> tempSellingPropertyIds = new ArrayList<>();
        for (Integer id : this.sellingPropertyIds) {
            tempSellingPropertyIds.add(new Uuid(id, PROPERTY));
        }
        final Set<Uuid> modelSellingPropertyIds = new HashSet<>(tempSellingPropertyIds);

        return new Contact(modelUuid, modelName, modelPhone, modelEmail, modelAddress,
                          modelBudgetMin, modelBudgetMax, modelNotes, modelStatus,
                modelBuyingPropertyIds, modelSellingPropertyIds);
    }

}
