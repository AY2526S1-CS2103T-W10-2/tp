package seedu.address.model.contact;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Contact's status in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidStatus(String)}
 */
public class ContactStatus {

    public static final String MESSAGE_CONSTRAINTS =
            "Invalid status value \"%s\". Allowed (case-insensitive): Active, Inactive.";

    /*
     * Only allows specific values, Active, Inactive, and empty string
     */
    public static final String VALIDATION_REGEX = "(?i)(Active|Inactive)?";

    public final String value;

    /**
     * Constructs a {@code Status}.
     *
     * @param status A valid status string.
     */
    public ContactStatus(String status) {
        requireNonNull(status);
        checkArgument(isValidStatus(status), String.format(MESSAGE_CONSTRAINTS, status));
        value = status;
    }

    /**
     * Returns true if a given string is a valid status.
     */
    public static boolean isValidStatus(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof ContactStatus
                && value.equals(((ContactStatus) other).value));
    }
}
