package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Uuid;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";

    private Uuid uuid;
    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Set<Tag> tags;
    private Set<String> buyingPropertyIds;
    private Set<String> sellingPropertyIds;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        uuid = new Uuid(0);
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        tags = new HashSet<>();
        buyingPropertyIds = new HashSet<>();
        sellingPropertyIds = new HashSet<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        uuid = personToCopy.getUuid();
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        address = personToCopy.getAddress();
        tags = new HashSet<>(personToCopy.getTags());
        buyingPropertyIds = new HashSet<>(personToCopy.getBuyingPropertyIds());
        sellingPropertyIds = new HashSet<>(personToCopy.getSellingPropertyIds());
    }

    /**
     * Sets the {@code Uuid} of the {@code Person} that we are building.
     */
    public PersonBuilder withUuid(int uuid) {
        this.uuid = new Uuid(uuid);
        return this;
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public PersonBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Parses the {@code ids} into a {@code Set<Index>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withBuyingPropertyIds(String ... ids) {
        this.buyingPropertyIds = Set.of(ids);
        return this;
    }

    /**
     * Parses the {@code ids} into a {@code Set<Index>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withSellingPropertyIds(String ... ids) {
        this.sellingPropertyIds = Set.of(ids);
        return this;
    }

    public Person build() {
        return new Person(uuid, name, phone, email, address, tags, buyingPropertyIds, sellingPropertyIds);
    }

}
