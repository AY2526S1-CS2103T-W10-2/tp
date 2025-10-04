package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's status in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidStatus(String)}
 */
public class Status {

    public static final String MESSAGE_CONSTRAINTS =
            "Status should be one of the following: 'Active' or 'Inactive' or ''.";

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
    public Status(String status) {
        requireNonNull(status);
        checkArgument(isValidStatus(status), MESSAGE_CONSTRAINTS);
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
                || (other instanceof Status
                && value.equals(((Status) other).value));
    }
}
