package seedu.address.model.contact;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.uuid.Uuid;

/**
 * Represents a Contact in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Contact {

    // Identity fields
    private final Uuid uuid;
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final ContactAddress address;
    private final Set<Tag> tags = new HashSet<>();
    private final BudgetMin budgetMin;
    private final BudgetMax budgetMax;
    private final Notes notes;
    private final ContactStatus status;
    private final Set<Uuid> buyingPropertyIds = new HashSet<>();
    private final Set<Uuid> sellingPropertyIds = new HashSet<>();

    /**
     * At least name and phone must not be null.
     */
    public Contact(Uuid uuid, Name name, Phone phone, Email email,
                   ContactAddress address, Set<Tag> tags, BudgetMin budgetMin,
                   BudgetMax budgetMax, Notes notes, ContactStatus status,
                   Set<Uuid> buyingPropertyIds, Set<Uuid> sellingPropertyIds) {
        requireAllNonNull(name, phone);
        this.uuid = uuid;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.budgetMin = budgetMin;
        this.budgetMax = budgetMax;
        this.notes = notes;
        this.status = status;
        this.buyingPropertyIds.addAll(buyingPropertyIds);
        this.sellingPropertyIds.addAll(sellingPropertyIds);
    }

    /**
     * Overloaded constructor for missing UUID as UUID will be made in AddCommand
     */
    public Contact(Name name, Phone phone, Email email,
                   ContactAddress address, Set<Tag> tags, BudgetMin budgetMin,
                   BudgetMax budgetMax, Notes notes, ContactStatus status,
                   Set<Uuid> buyingPropertyIds, Set<Uuid> sellingPropertyIds) {
        requireAllNonNull(name, phone);
        this.uuid = null;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.budgetMin = budgetMin;
        this.budgetMax = budgetMax;
        this.notes = notes;
        this.status = status;
        this.buyingPropertyIds.addAll(buyingPropertyIds);
        this.sellingPropertyIds.addAll(sellingPropertyIds);
    }

    public Uuid getUuid() {
        return uuid;
    }

    /**
     * Duplicates Contact with the new Uuid.
     * Used for updating Contact when adding to addressbook.
     */
    public Contact duplicateWithNewUuid(Uuid uuid) {
        return new Contact(uuid, name, phone, email, address, tags, budgetMin,
                budgetMax, notes, status, buyingPropertyIds, sellingPropertyIds);
    }

    public BudgetMin getBudgetMin() {
        return budgetMin;
    }

    public BudgetMax getBudgetMax() {
        return budgetMax;
    }

    public Notes getNotes() {
        return notes;
    }

    public ContactStatus getStatus() {
        return status;
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public ContactAddress getAddress() {
        return address;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns an immutable property index set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Uuid> getBuyingPropertyIds() {
        return Collections.unmodifiableSet(buyingPropertyIds);
    }

    /**
     * Returns an immutable property index set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Uuid> getSellingPropertyIds() {
        return Collections.unmodifiableSet(sellingPropertyIds);
    }

    /**
     * Duplicates Contact with the new BuyingPropertyIds.
     * Used for updating Contact when linking or unlinking.
     */
    public Contact duplicateWithNewBuyingPropertyIds(Set<Uuid> buyingPropertyIds) {
        return new Contact(uuid, name, phone, email, address, tags, budgetMin,
                budgetMax, notes, status, buyingPropertyIds, sellingPropertyIds);
    }

    /**
     * Duplicates Contact with the new SellingPropertyIds.
     * Used for updating Contact when linking or unlinking.
     */
    public Contact duplicateWithNewSellingPropertyIds(Set<Uuid> sellingPropertyIds) {
        return new Contact(uuid, name, phone, email, address, tags, budgetMin,
                budgetMax, notes, status, buyingPropertyIds, sellingPropertyIds);
    }

    /**
     * Returns true if both contacts have the same name & same phone number.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Contact)) {
            return false;
        }

        Contact otherContact = (Contact) other;
        return Objects.equals(name.toString().toLowerCase(), otherContact.name.toString().toLowerCase())
                && Objects.equals(phone, otherContact.phone);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(uuid, name, phone, email, address, tags,
                budgetMin, budgetMax, notes, status, buyingPropertyIds, sellingPropertyIds);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("uuid", uuid.getValue())
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("tags", tags)
                .add("budgetMin", budgetMin)
                .add("budgetMax", budgetMax)
                .add("notes", notes)
                .add("status", status)
                .add("buyingPropertyIds", buyingPropertyIds)
                .add("sellingPropertyIds", sellingPropertyIds)
                .toString();
    }

}
