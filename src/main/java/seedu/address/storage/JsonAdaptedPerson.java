package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.BudgetMax;
import seedu.address.model.person.BudgetMin;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Notes;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonAddress;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Status;
import seedu.address.model.person.Uuid;
import seedu.address.model.tag.Tag;


/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final Integer uuid;
    private final String name;
    private final String phone;
    private final String email;
    private final String address;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();
    private final String budgetMin;
    private final String budgetMax;
    private final String notes;
    private final String status;

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("uuid") Integer uuid,
                             @JsonProperty("name") String name,
                             @JsonProperty("phone") String phone,
                             @JsonProperty("email") String email,
                             @JsonProperty("address") String address,
                             @JsonProperty("tags") List<JsonAdaptedTag> tags,
                             @JsonProperty("budgetMin") String budgetMin,
                             @JsonProperty("budgetMax") String budgetMax,
                             @JsonProperty("notes") String notes,
                             @JsonProperty("status") String status) {
        this.uuid = uuid;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.budgetMin = budgetMin;
        this.budgetMax = budgetMax;
        this.notes = notes;
        this.status = status;
        if (tags != null) {
            this.tags.addAll(tags);
        }
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        uuid = source.getUuid().value;
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        budgetMin = source.getBudgetMin().value;
        budgetMax = source.getBudgetMax().value;
        notes = source.getNotes().value;
        status = source.getStatus().value;

        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Person toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            personTags.add(tag.toModelType());
        }

        if (uuid == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Uuid"));
        }
        final Uuid modelUuid = new Uuid(uuid);

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
                                            PersonAddress.class.getSimpleName()));
        }
        if (!PersonAddress.isValidAddress(address)) {
            throw new IllegalValueException(PersonAddress.MESSAGE_CONSTRAINTS);
        }
        final PersonAddress modelAddress = new PersonAddress(address);

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
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Status"));
        }
        if (!Status.isValidStatus(status)) {
            throw new IllegalValueException(Status.MESSAGE_CONSTRAINTS);
        }
        final Status modelStatus = new Status(status);

        final Set<Tag> modelTags = new HashSet<>(personTags);
        return new Person(modelUuid, modelName, modelPhone, modelEmail, modelAddress,
                          modelTags, modelBudgetMin, modelBudgetMax, modelNotes, modelStatus);
    }

}
