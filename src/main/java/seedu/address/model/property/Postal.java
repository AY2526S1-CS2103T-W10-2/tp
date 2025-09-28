package seedu.address.model.property;

import static java.util.Objects.requireNonNull;

import static seedu.address.commons.util.AppUtil.checkArgument;

public class Postal {
    public static final String MESSAGE_CONSTRAINTS = "Postal codes should only contain numeric characters and must be 6 characters long";

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
