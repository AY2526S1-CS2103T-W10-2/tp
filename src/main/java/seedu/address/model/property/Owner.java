package seedu.address.model.property;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Property's owner in the property book.
 * Guarantees: immutable; is valid as declared in {@link #isValidOwner(String)}
 */
public class Owner {
    public static final String MESSAGE_CONSTRAINTS = "Invalid owner. Owner's id should not be blank.";

    /*
     * The owner's id must be a String.
     */
    public static final String VALIDATION_REGEX = "^[A-Za-z0-9._-]+$";

    public final String value;

    /**
     * Constructs a {@code Owner}.
     *
     * @param owner A valid owner id.
     */
    public Owner(String owner) {
        // Trim whitespace before validating
        owner = owner.trim();

        requireNonNull(owner);
        checkArgument(isValidOwner(owner), MESSAGE_CONSTRAINTS.replace("VALUE", owner));
        value = owner;
    }

    /**
     * Returns true if a given string is a valid owner id.
     */
    public static boolean isValidOwner(String test) {
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
        if (!(other instanceof Owner)) {
            return false;
        }

        Owner otherOwner = (Owner) other;
        return value.equals(otherOwner.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
