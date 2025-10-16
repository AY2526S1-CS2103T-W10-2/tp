package seedu.address.model.util;

import static seedu.address.model.uuid.Uuid.StoredItem.PERSON;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.PropertyBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyPropertyBook;
import seedu.address.model.person.BudgetMax;
import seedu.address.model.person.BudgetMin;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Notes;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonAddress;
import seedu.address.model.person.PersonStatus;
import seedu.address.model.person.Phone;
import seedu.address.model.property.Property;
import seedu.address.model.tag.Tag;
import seedu.address.model.uuid.Uuid;
import seedu.address.model.uuid.Uuid.StoredItem;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Uuid(1, PERSON), new Name("Alex Yeoh"), new Phone("87438807"),
                        new Email("alexyeoh@example.com"),
                        new PersonAddress("Blk 30 Geylang Street 29, #06-40"), getTagSet("friends"),
                        new BudgetMin("500"), new BudgetMax("1000"), new Notes("Prefers email contact"),
                        new PersonStatus("Active"), new HashSet<>(), new HashSet<>()),

            new Person(new Uuid(2, PERSON), new Name("Bernice Yu"), new Phone("99272758"),
                        new Email("berniceyu@example.com"),
                        new PersonAddress("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                                            getTagSet("colleagues", "friends"),
                        new BudgetMin("700"), new BudgetMax("1500"), new Notes("VIP client"),
                        new PersonStatus("Active"), new HashSet<>(), new HashSet<>()),

            new Person(new Uuid(3, PERSON), new Name("Charlotte Oliveiro"), new Phone("93210283"),
                        new Email("charlotte@example.com"),
                        new PersonAddress("Blk 11 Ang Mo Kio Street 74, #11-04"), getTagSet("neighbours"),
                        new BudgetMin("400"), new BudgetMax("1200"), new Notes("Follow up in July"),
                        new PersonStatus("Inactive"), new HashSet<>(), new HashSet<>()),

            new Person(new Uuid(4, PERSON), new Name("David Li"), new Phone("91031282"),
                        new Email("lidavid@example.com"),
                        new PersonAddress("Blk 436 Serangoon Gardens Street 26, #16-43"), getTagSet("family"),
                        new BudgetMin("800"), new BudgetMax("2000"), new Notes("Prefers phone calls"),
                        new PersonStatus("Active"), new HashSet<>(), new HashSet<>()),

            new Person(new Uuid(5, PERSON), new Name("Irfan Ibrahim"), new Phone("92492021"),
                        new Email("irfan@example.com"),
                        new PersonAddress("Blk 47 Tampines Street 20, #17-35"), getTagSet("classmates"),
                        new BudgetMin("300"), new BudgetMax("900"), new Notes("Interested in workshops"),
                        new PersonStatus("Inactive"), new HashSet<>(), new HashSet<>()),

            new Person(new Uuid(6, PERSON), new Name("Roy Balakrishnan"), new Phone("92624417"),
                        new Email("royb@example.com"),
                        new PersonAddress("Blk 45 Aljunied Street 85, #11-31"), getTagSet("colleagues"),
                        new BudgetMin("600"), new BudgetMax("1400"), new Notes("Important partner"),
                        new PersonStatus("Active"), new HashSet<>(), new HashSet<>())
        };
    }

    public static Property[] getSampleProperties() {
        return new Property[] {
            // Sample properties can be added here in the future
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        sampleAb.setNextUuid(getSamplePersons().length + 1);
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
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
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
