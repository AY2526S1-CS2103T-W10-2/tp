package seedu.address.testutil;

import static seedu.address.model.uuid.Uuid.StoredItem.CONTACT;

import java.util.HashSet;
import java.util.Set;

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
import seedu.address.model.util.SampleDataUtil;
import seedu.address.model.uuid.Uuid;

/**
 * A utility class to help with building Contact objects.
 */
public class ContactBuilderUtil {

    public static final Uuid DEFAULT_UUID = new Uuid(1, CONTACT);
    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_BUDGET_MIN = "0";
    public static final String DEFAULT_BUDGET_MAX = String.valueOf(200_000_000_000L);
    public static final String DEFAULT_NOTES = "No notes";
    public static final String DEFAULT_STATUS = "Active";

    private Name name;
    private Phone phone;
    private Email email;
    private ContactAddress address;
    private Set<Tag> tags;
    private BudgetMin budgetMin;
    private BudgetMax budgetMax;
    private Notes notes;
    private ContactStatus status;
    private Uuid uuid;
    private Set<Uuid> buyingPropertyIds;
    private Set<Uuid> sellingPropertyIds;

    /**
     * Creates a {@code ContactBuilder} with the default details.
     */
    public ContactBuilderUtil() {
        uuid = DEFAULT_UUID;
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new ContactAddress(DEFAULT_ADDRESS);
        tags = new HashSet<>();
        budgetMin = new BudgetMin(DEFAULT_BUDGET_MIN);
        budgetMax = new BudgetMax(DEFAULT_BUDGET_MAX);
        notes = new Notes(DEFAULT_NOTES);
        status = new ContactStatus(DEFAULT_STATUS);
        buyingPropertyIds = new HashSet<>();
        sellingPropertyIds = new HashSet<>();
    }

    /**
     * Initializes the ContactBuilder with the data of {@code contactToCopy}.
     */
    public ContactBuilderUtil(Contact contactToCopy) {
        uuid = new Uuid(contactToCopy.getUuid());
        name = new Name(contactToCopy.getName().fullName);
        phone = new Phone(contactToCopy.getPhone().value);
        email = new Email(contactToCopy.getEmail().value);
        address = new ContactAddress(contactToCopy.getAddress().value);
        tags = new HashSet<>(contactToCopy.getTags());
        budgetMin = new BudgetMin(contactToCopy.getBudgetMin().value);
        budgetMax = new BudgetMax(contactToCopy.getBudgetMax().value);
        notes = new Notes(contactToCopy.getNotes().value);
        status = new ContactStatus(contactToCopy.getStatus().value);
        buyingPropertyIds = new HashSet<>(contactToCopy.getBuyingPropertyIds());
        sellingPropertyIds = new HashSet<>(contactToCopy.getSellingPropertyIds());
    }

    /**
     * Sets the {@code uuid} of the {@code Contact} that we are building.
     */
    public ContactBuilderUtil withUuid(int uuid) {
        this.uuid = new Uuid(uuid, CONTACT);
        return this;
    }

    /**
     * Sets the {@code Name} of the {@code Contact} that we are building.
     */
    public ContactBuilderUtil withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Contact} that we are building.
     */
    public ContactBuilderUtil withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Contact} that we are building.
     */
    public ContactBuilderUtil withAddress(String address) {
        this.address = new ContactAddress(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Contact} that we are building.
     */
    public ContactBuilderUtil withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Contact} that we are building.
     */
    public ContactBuilderUtil withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code BudgetMin} of the {@code Contact}.
     */
    public ContactBuilderUtil withBudgetMin(String min) {
        this.budgetMin = new BudgetMin(min);
        return this;
    }

    /**
     * Sets the {@code BudgetMax} of the {@code Contact}.
     */
    public ContactBuilderUtil withBudgetMax(String max) {
        this.budgetMax = new BudgetMax(max);
        return this;
    }

    /**
     * Sets the {@code Notes} of the {@code Contact}.
     */
    public ContactBuilderUtil withNotes(String notes) {
        this.notes = new Notes(notes);
        return this;
    }

    /**
     * Sets the {@code Status} of the {@code Contact}.
     */
    public ContactBuilderUtil withStatus(String status) {
        this.status = new ContactStatus(status);
        return this;
    }

    /**
     * Parses the {@code ids} into a {@code Set<Index>} and set it to the {@code Contact} that we are building.
     */
    public ContactBuilderUtil withBuyingPropertyIds(Uuid ... ids) {
        this.buyingPropertyIds = Set.of(ids);
        return this;
    }

    /**
     * Parses the {@code ids} into a {@code Set<Index>} and set it to the {@code Contact} that we are building.
     */
    public ContactBuilderUtil withSellingPropertyIds(Uuid ... ids) {
        this.sellingPropertyIds = Set.of(ids);
        return this;
    }


    /**
     * Builds and returns the {@code Contact} with the specified fields.
     * A new {@code Uuid} will be generated automatically.
     */
    public Contact build() {
        return new Contact(
                uuid,
                name,
                phone,
                email,
                address,
                tags,
                budgetMin,
                budgetMax,
                notes,
                status,
                buyingPropertyIds,
                sellingPropertyIds
        );
    }

}
