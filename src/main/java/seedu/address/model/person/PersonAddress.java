package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's address in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidAddress(String)}
 */
public class PersonAddress {

    public static final String MESSAGE_CONSTRAINTS =
            "Addresses can take any values, and it can be blank. Maximum 200 characters.";

    /*
     * The first character of the address must not be a whitespace,
     * also "" (a blank string) is a valid input.
     * Maximum 200 characters
     */
    public static final String VALIDATION_REGEX = "^$|[^\\s].{0,199}$";


    public final String value;

    /**
     * Constructs an {@code Address}.
     *
     * @param address A valid address.
     */
    public PersonAddress(String address) {
        requireNonNull(address);
        checkArgument(isValidAddress(address), MESSAGE_CONSTRAINTS);
        value = address;
    }

    /**
     * Returns true if a given string is a valid email.
     */
    public static boolean isValidAddress(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonAddress)) {
            return false;
        }

        PersonAddress otherAddress = (PersonAddress) other;
        return value.equals(otherAddress.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
