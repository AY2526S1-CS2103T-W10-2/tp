package seedu.address.model.property;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Property's price in the property book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPrice(String)}
 */
public class Price {
    public static final String MESSAGE_CONSTRAINTS = "Invalid price. Use a positive integer ≤ 1,000,000,000,000.";

    /*
     * The price must be a positive integer up to 1,000,000,000,000 (1 trillion).
     */
    public static final String VALIDATION_REGEX = "^(?:[1-9]\\d{0,11}|1000000000000)$";

    public final String value;

    /**
     * Constructs a {@code Price}.
     *
     * @param price A valid price value.
     */
    public Price(String price) {
        // Trim whitespace before validating
        price = price.trim();

        requireNonNull(price);
        checkArgument(isValidPrice(price), MESSAGE_CONSTRAINTS);
        value = price;
    }

    /**
     * Returns true if a given string is a valid price.
     */
    public static boolean isValidPrice(String test) {
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
        if (!(other instanceof Price)) {
            return false;
        }

        Price otherPrice = (Price) other;
        return value.equals(otherPrice.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
