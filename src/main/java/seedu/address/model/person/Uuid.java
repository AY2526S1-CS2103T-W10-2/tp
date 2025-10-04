package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's unique identifier in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidUuid(int)}.
 */
public class Uuid {

    public static final String MESSAGE_CONSTRAINTS = "UUID should be a positive integer.";

    public final int value;

    /**
     * Constructs a {@code Uuid}.
     *
     * @param uuid A valid UUID integer.
     */
    public Uuid(int uuid) {
        requireNonNull(uuid);
        checkArgument(isValidUuid(uuid), MESSAGE_CONSTRAINTS);
        value = uuid;
    }

    /**
     * Returns true if a given integer is a valid UUID.
     */
    public static boolean isValidUuid(int test) {
        return test > 0;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Uuid
                && value == ((Uuid) other).value);
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(value);
    }
}
