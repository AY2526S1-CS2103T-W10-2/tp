package seedu.address.model.property;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's address in the address book.
 * Guarantees: immutable; is valid as declared in
 * {@link #isValidAddress(String)}
 */
public class PropertyAddress {

    public static final String MESSAGE_CONSTRAINTS =
            "Invalid address. Provide 5-200 chars with at least one letter and one digit.";

    /*
     * Address must be 5-200 characters long, contain at least one letter and one
     * digit.
     * Uses positive lookahead to ensure both letter and digit are present.
     */
    public static final String VALIDATION_REGEX = "^(?=.*[A-Za-z])(?=.*\\d).{5,200}$";

    public final String value;

    /**
     * Constructs an {@code Address}.
     *
     * @param address A valid address.
     */
    public PropertyAddress(String address) {
        address = address.trim();
        requireNonNull(address);
        checkArgument(isValidPropertyAddress(address), MESSAGE_CONSTRAINTS);
        value = address;
    }

    /**
     * Returns true if a given string is a valid property address.
     */
    public static boolean isValidPropertyAddress(String test) {
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
        if (!(other instanceof PropertyAddress)) {
            return false;
        }

        PropertyAddress otherAddress = (PropertyAddress) other;
        return value.equals(otherAddress.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
