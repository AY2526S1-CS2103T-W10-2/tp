package seedu.address.model.property;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Property's status in the property book.
 * Guarantees: immutable; is valid as declared in {@link #isValidStatus(String)}
 */
public class Status {
    public static final String MESSAGE_CONSTRAINTS =
            "Invalid status \"VALUE\". Allowed: unsold, sold.";

    /*
     * The status must be one of the predefined values: unsold, sold
     * Case-insensitive matching is supported.
     */
    public static final String VALIDATION_REGEX = "^(?i)(unsold|sold)$";

    public final String value;

    /**
     * Constructs a {@code Status}.
     *
     * @param status A valid status value.
     */
    public Status(String status) {
        // Trim whitespace before validating
        status = status.trim();

        requireNonNull(status);
        checkArgument(isValidStatus(status), MESSAGE_CONSTRAINTS.replace("VALUE", status));
        value = status.toLowerCase(); // Store in lowercase for consistency
    }

    /**
     * Returns true if a given string is a valid status.
     */
    public static boolean isValidStatus(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public boolean isSold() {
        return value.equals("sold");
    }

    public boolean isRented() {
        return value.equals("rented");
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
        if (!(other instanceof Status)) {
            return false;
        }

        Status otherStatus = (Status) other;
        return value.equals(otherStatus.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
