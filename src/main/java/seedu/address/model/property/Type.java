package seedu.address.model.property;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Property's type in the property book.
 * Guarantees: immutable; is valid as declared in {@link #isValidType(String)}
 */
public class Type {
    public static final String MESSAGE_CONSTRAINTS =
            "Invalid type value \"%s\". Allowed (case-insensitive): hdb, condo, landed, apartment, office, others.";

    public static final String VALIDATION_REGEX = "(?i)HDB|Condo|Landed|Apartment|Office|Others";

    public final String value;

    /**
     * Constructs a {@code Type}.
     *
     * @param type A valid property type.
     */
    public Type(String type) {
        type = type.trim().toLowerCase();

        requireNonNull(type);
        checkArgument(isValidType(type), String.format(MESSAGE_CONSTRAINTS, type));
        value = type;
    }

    /**
     * Returns true if a given string is a valid property type.
     */
    public static boolean isValidType(String test) {
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
        if (!(other instanceof Type)) {
            return false;
        }

        Type otherType = (Type) other;
        return value.equals(otherType.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
