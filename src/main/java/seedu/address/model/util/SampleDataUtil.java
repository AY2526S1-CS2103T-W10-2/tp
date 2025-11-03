package seedu.address.model.util;

import static seedu.address.model.uuid.Uuid.StoredItem.CONTACT;
import static seedu.address.model.uuid.Uuid.StoredItem.PROPERTY;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.PropertyBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyPropertyBook;
import seedu.address.model.contact.BudgetMax;
import seedu.address.model.contact.BudgetMin;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.ContactAddress;
import seedu.address.model.contact.ContactStatus;
import seedu.address.model.contact.Email;
import seedu.address.model.contact.Name;
import seedu.address.model.contact.Notes;
import seedu.address.model.contact.Phone;
import seedu.address.model.property.Bathroom;
import seedu.address.model.property.Bedroom;
import seedu.address.model.property.FloorArea;
import seedu.address.model.property.Owner;
import seedu.address.model.property.Postal;
import seedu.address.model.property.Price;
import seedu.address.model.property.Property;
import seedu.address.model.property.PropertyAddress;
import seedu.address.model.property.Status;
import seedu.address.model.property.Type;
import seedu.address.model.uuid.Uuid;
import seedu.address.model.uuid.Uuid.StoredItem;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {

    private static final Set<Uuid> EMPTY_BUYING_CONTACT_ID_SET = Set.of();
    private static final Set<Uuid> EMPTY_SELLING_CONTACT_ID_SET = Set.of();
    private static final Set<Uuid> EMPTY_BUYING_PROPERTY_ID_SET = Set.of();
    private static final Set<Uuid> EMPTY_SELLING_PROPERTY_ID_SET = Set.of();

    @SuppressWarnings("checkstyle:Regexp")
    public static Contact[] getSampleContacts() {
        return new Contact[] {
            new Contact(new Uuid(1, CONTACT), new Name("Alex Yeoh"), new Phone("87438807"),
                        new Email("alexyeoh@example.com"),
                        new ContactAddress("Blk 30 Geylang Street 29, #06-40"),
                        new BudgetMin("50000"), new BudgetMax("1000000"), new Notes("Prefers email contact"),
                        new ContactStatus("Active"), EMPTY_BUYING_PROPERTY_ID_SET, Set.of(new Uuid(1, PROPERTY))),

            new Contact(new Uuid(2, CONTACT), new Name("Bernice Yu"), new Phone("99272758"),
                        new Email("berniceyu@example.com"),
                        new ContactAddress("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                        new BudgetMin("50000"), new BudgetMax("1000000"), new Notes("VIP client"),
                        new ContactStatus("Active"), EMPTY_BUYING_PROPERTY_ID_SET, EMPTY_SELLING_PROPERTY_ID_SET),

            new Contact(new Uuid(3, CONTACT), new Name("Charlotte Oliveiro"), new Phone("93210283"),
                        new Email("charlotte@example.com"),
                        new ContactAddress("Blk 11 Ang Mo Kio Street 74, #11-04"),
                        new BudgetMin("70000"), new BudgetMax("1000000"), new Notes("Follow up in July"),
                        new ContactStatus("Inactive"), EMPTY_BUYING_PROPERTY_ID_SET, EMPTY_SELLING_PROPERTY_ID_SET),

            new Contact(new Uuid(4, CONTACT), new Name("David Li"), new Phone("91031282"),
                        new Email("lidavid@example.com"),
                        new ContactAddress("Blk 436 Serangoon Gardens Street 26, #16-43"),
                        new BudgetMin("80000"), new BudgetMax("90000"), new Notes("Prefers phone calls"),
                        new ContactStatus("Active"), Set.of(new Uuid(3, PROPERTY), new Uuid(5, PROPERTY)),
                        EMPTY_SELLING_PROPERTY_ID_SET),

            new Contact(new Uuid(5, CONTACT), new Name("Irfan Ibrahim"), new Phone("92492021"),
                        new Email("irfan@example.com"),
                        new ContactAddress("Blk 47 Tampines Street 20, #17-35"),
                        new BudgetMin("80000"), new BudgetMax("90000"), new Notes("Interested in workshops"),
                        new ContactStatus("Inactive"), EMPTY_BUYING_PROPERTY_ID_SET, EMPTY_SELLING_PROPERTY_ID_SET),

            new Contact(new Uuid(6, CONTACT), new Name("Roy Balakrishnan"), new Phone("92624417"),
                        new Email("royb@example.com"),
                        new ContactAddress("Blk 45 Aljunied Street 85, #11-31"),
                        new BudgetMin("50000"), new BudgetMax("500000"), new Notes("VIP"),
                        new ContactStatus("Active"), EMPTY_BUYING_PROPERTY_ID_SET, EMPTY_SELLING_PROPERTY_ID_SET),

            new Contact(new Uuid(7, CONTACT), new Name("Gyuri Kim"), new Phone("91119911"),
                        new Email("gyuri.kim@test.com.sg"), new ContactAddress("Blk 10 Punggol Field, #02-11"),
                        new BudgetMin("800000"), new BudgetMax("900000"),
                        new Notes("New arrival"), new ContactStatus("Active"),
                        Set.of(new Uuid(2, PROPERTY)), EMPTY_SELLING_PROPERTY_ID_SET),

            new Contact(new Uuid(8, CONTACT), new Name("Tan Wei Ming"), new Phone("98765544"),
                        new Email("weiming.tan@example.sg"), new ContactAddress("Blk 53 Bishan St 13, #09-22"),
                        new BudgetMin("200000"), new BudgetMax("300000"),
                        new Notes("Prefers city area"), new ContactStatus("Inactive"),
                        Set.of(new Uuid(6, PROPERTY)), EMPTY_SELLING_PROPERTY_ID_SET),

            new Contact(new Uuid(9, CONTACT), new Name("Priya Raj"), new Phone("93334444"),
                        new Email("priya.raj@example.com"), new ContactAddress("Blk 88 Toa Payoh Central, #20-02"),
                        new BudgetMin("200000"), new BudgetMax("900000"),
                        new Notes("Wants fast deal"), new ContactStatus("Active"),
                        EMPTY_BUYING_PROPERTY_ID_SET, EMPTY_SELLING_PROPERTY_ID_SET),

            new Contact(new Uuid(10, CONTACT), new Name("Jonathan Lim"), new Phone("91237654"),
                        new Email("jon.lim@example.com"), new ContactAddress("21 Orchard Boulevard, #23-01"),
                        new BudgetMin("3500"), new BudgetMax("4500"),
                        new Notes("Looking for luxury"), new ContactStatus("Active"),
                        EMPTY_BUYING_PROPERTY_ID_SET, EMPTY_SELLING_PROPERTY_ID_SET)
        };
    }

    public static Property[] getSampleProperties() {
        return new Property[] {
            new Property(new Uuid(1, PROPERTY), new PropertyAddress("123 Yishun Road"), new Bathroom("2"),
                    new Bedroom("3"), new FloorArea("100"), new Postal("545603"),
                    new Price("310000"), new Status("available"), new Type("HDB"),
                    new Owner("1"), EMPTY_BUYING_CONTACT_ID_SET, Set.of(new Uuid(1, CONTACT))),

            new Property(new Uuid(2, PROPERTY), new PropertyAddress("15 Bukit Timah Avenue"), new Bathroom("1"),
                    new Bedroom("2"), new FloorArea("76"), new Postal("529126"),
                    new Price("2000"), new Status("available"), new Type("Condo"),
                    new Owner("7"), Set.of(new Uuid(7, CONTACT)), EMPTY_SELLING_CONTACT_ID_SET),

            new Property(new Uuid(3, PROPERTY), new PropertyAddress("456 Sengkang East Way"), new Bathroom("2"),
                    new Bedroom("4"), new FloorArea("123"), new Postal("272822"),
                    new Price("450000"), new Status("available"), new Type("HDB"),
                    new Owner("2"), Set.of(new Uuid(4, CONTACT)), EMPTY_SELLING_CONTACT_ID_SET),

            new Property(new Uuid(4, PROPERTY), new PropertyAddress("89 Pasir Ris Drive 1"), new Bathroom("2"),
                    new Bedroom("3"), new FloorArea("95"), new Postal("510089"),
                    new Price("320000"), new Status("unavailable"), new Type("office"),
                    new Owner("4"), EMPTY_BUYING_CONTACT_ID_SET, EMPTY_SELLING_CONTACT_ID_SET),

            new Property(new Uuid(5, PROPERTY), new PropertyAddress("77 Holland Road"), new Bathroom("2"),
                    new Bedroom("2"), new FloorArea("85"), new Postal("229999"),
                    new Price("3300"), new Status("available"), new Type("apartment"),
                    new Owner("8"), Set.of(new Uuid(4, CONTACT)), EMPTY_SELLING_CONTACT_ID_SET),

            new Property(new Uuid(6, PROPERTY), new PropertyAddress("56 Jurong West St 61"), new Bathroom("1"),
                    new Bedroom("3"), new FloorArea("92"), new Postal("640056"),
                    new Price("285000"), new Status("available"), new Type("HDB"),
                    new Owner("6"), Set.of(new Uuid(8, CONTACT)), EMPTY_SELLING_CONTACT_ID_SET),

            new Property(new Uuid(7, PROPERTY), new PropertyAddress("35 Ghim Moh Road"), new Bathroom("3"),
                    new Bedroom("5"), new FloorArea("180"), new Postal("270035"),
                    new Price("900000"), new Status("available"), new Type("Landed"),
                    new Owner("9"), EMPTY_BUYING_CONTACT_ID_SET, EMPTY_SELLING_CONTACT_ID_SET),

            new Property(new Uuid(8, PROPERTY), new PropertyAddress("44 Marine Parade"), new Bathroom("2"),
                    new Bedroom("4"), new FloorArea("120"), new Postal("449000"),
                    new Price("4000"), new Status("unavailable"), new Type("Condo"),
                    new Owner("1"), EMPTY_BUYING_CONTACT_ID_SET, EMPTY_SELLING_CONTACT_ID_SET),

            new Property(new Uuid(9, PROPERTY), new PropertyAddress("18 Ardmore Park"), new Bathroom("4"),
                    new Bedroom("5"), new FloorArea("320"), new Postal("259955"),
                    new Price("5000000"), new Status("available"), new Type("condo"),
                    new Owner("10"), EMPTY_BUYING_CONTACT_ID_SET, EMPTY_SELLING_CONTACT_ID_SET),

            new Property(new Uuid(10, PROPERTY), new PropertyAddress("99 Keppel Bay View"), new Bathroom("3"),
                    new Bedroom("4"), new FloorArea("150"), new Postal("098405"),
                    new Price("9000"), new Status("available"), new Type("hdb"),
                    new Owner("3"), EMPTY_BUYING_CONTACT_ID_SET, EMPTY_SELLING_CONTACT_ID_SET)
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Contact sampleContact : getSampleContacts()) {
            sampleAb.addContact(sampleContact);
        }
        sampleAb.setNextUuid(getSampleContacts().length + 1);
        return sampleAb;
    }

    public static ReadOnlyPropertyBook getSamplePropertyBook() {
        PropertyBook samplePb = new PropertyBook();
        for (Property sampleProperty : getSampleProperties()) {
            samplePb.addProperty(sampleProperty);
        }
        samplePb.setNextUuid(getSampleProperties().length + 1);
        return samplePb;
    }

    /**
     * Returns a Uuid set containing the list of integers representing indices given.
     */
    public static Set<Uuid> getUuidSet(StoredItem itemType, int ... ids) {
        return Arrays.stream(ids)
                .boxed()
                .map(id -> new Uuid(id, itemType))
                .collect(Collectors.toSet());
    }

}
