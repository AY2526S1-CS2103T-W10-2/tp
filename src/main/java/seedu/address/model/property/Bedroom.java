package seedu.address.model.property;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Property's number of bedrooms in the property book.
 * Guarantees: immutable; is valid as declared in
 * {@link #isValidBedroom(String)}
 */
public class Bedroom {
    public static final String MESSAGE_CONSTRAINTS = "Invalid number of bedrooms. Use an integer between 0 and 20.";

    /*
     * The number of bedrooms must be a non-negative integer.
     */
    public static final String VALIDATION_REGEX = "^(?:[0-9]|1[0-9]|20)$";

    public final String value;

    /**
     * Constructs a {@code Bedroom}.
     *
     * @param bedroom A valid number of bedrooms.
     */
    public Bedroom(String bedroom) {
        // Trim whitespace before validating
        bedroom = bedroom.trim();

        requireNonNull(bedroom);
        checkArgument(isValidBedroom(bedroom), MESSAGE_CONSTRAINTS);
        value = bedroom;
    }

    /**
     * Returns true if a given string is a valid number of bedrooms.
     */
    public static boolean isValidBedroom(String test) {
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
        if (!(other instanceof Bedroom)) {
            return false;
        }

        Bedroom otherBedroom = (Bedroom) other;
        return value.equals(otherBedroom.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
