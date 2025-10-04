package seedu.address.model.property;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Property's number of bathrooms in the property book.
 * Guarantees: immutable; is valid as declared in
 * {@link #isValidBathroom(String)}
 */
public class Bathroom {
    public static final String MESSAGE_CONSTRAINTS = "Invalid number of bathrooms. Use an integer between 0 and 20.";

    /*
     * The number of bathrooms must be a non-negative integer.
     */
    public static final String VALIDATION_REGEX = "^(?:[0-9]|1[0-9]|20)$";

    public final String value;

    /**
     * Constructs a {@code Bathroom}.
     *
     * @param bathroom A valid number of bathrooms.
     */
    public Bathroom(String bathroom) {
        // Trim whitespace before validating
        bathroom = bathroom.trim();

        requireNonNull(bathroom);
        checkArgument(isValidBathroom(bathroom), MESSAGE_CONSTRAINTS);
        value = bathroom;
    }

    /**
     * Returns true if a given string is a valid number of bathrooms.
     */
    public static boolean isValidBathroom(String test) {
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
        if (!(other instanceof Bathroom)) {
            return false;
        }

        Bathroom otherBathroom = (Bathroom) other;
        return value.equals(otherBathroom.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
