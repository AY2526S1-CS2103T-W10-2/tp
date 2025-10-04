package seedu.address.model.property;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Property's postal code in the property book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPostal(String)}
 */
public class Postal {
    public static final String MESSAGE_CONSTRAINTS = "Invalid postal code. Use a 6-digit Singapore postal code.";

    /*
     * The postal code must be exactly 6 numeric characters.
     */
    public static final String VALIDATION_REGEX = "[0-9]{6}";

    public final String value;

    /**
     * Constructs a {@code Postal}.
     *
     * @param postal A valid postal code.
     */
    public Postal(String postal) {
        // Trim whitespace before validating
        postal = postal.trim();

        requireNonNull(postal);
        checkArgument(isValidPostal(postal), MESSAGE_CONSTRAINTS);
        value = postal;
    }

    /**
     * Returns true if a given string is a valid postal code.
     */
    public static boolean isValidPostal(String test) {
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
        if (!(other instanceof Postal)) {
            return false;
        }

        Postal otherPostal = (Postal) other;
        return value.equals(otherPostal.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
