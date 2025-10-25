package seedu.address.model.contact;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Contact's email in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidEmail(String)}
 */
public class Email {

    private static final String SPECIAL_CHARACTERS = "+_.-";
    public static final String MESSAGE_CONSTRAINTS =
            "Emails should follow the format: name@example.com\n"
            + "• The part before @ can contain letters, numbers, and the symbols " + SPECIAL_CHARACTERS + "\n"
            + "• It cannot start or end with a symbol\n"
            + "• The part after @ must be a valid domain (like example.com)\n"
            + "• The domain must end with at least 2 letters (like .com or .sg)\n"
            + "• You can also leave the email field empty\n"
            + "• Emails can also be left empty";
    // alphanumeric characters except underscore
    private static final String ALPHANUMERIC_NO_UNDERSCORE = "[^\\W_]+";

    // Local part: must start and end with alphanumeric, special chars only in middle
    private static final String LOCAL_PART_REGEX = ALPHANUMERIC_NO_UNDERSCORE + "([" + SPECIAL_CHARACTERS + "]"
            + ALPHANUMERIC_NO_UNDERSCORE + ")*";

    // Domain label: alphanumeric, hyphens only in middle
    private static final String DOMAIN_PART_REGEX = ALPHANUMERIC_NO_UNDERSCORE
            + "(-" + ALPHANUMERIC_NO_UNDERSCORE + ")*";

    // Top-level domain: must be at least 2 letters (not numbers)
    private static final String DOMAIN_LAST_PART_REGEX = "[A-Za-z]{2,}";

    // Full domain: one or more labels with dots, ending with valid TLD
    private static final String DOMAIN_REGEX = "(" + DOMAIN_PART_REGEX + "\\.)+(" + DOMAIN_LAST_PART_REGEX + ")";

    public static final String VALIDATION_REGEX = "^(" + LOCAL_PART_REGEX + "@" + DOMAIN_REGEX + ")?$";

    public final String value;

    /**
     * Constructs an {@code Email}.
     *
     * @param email A valid email address.
     */
    public Email(String email) {
        requireNonNull(email);
        checkArgument(isValidEmail(email), MESSAGE_CONSTRAINTS);
        value = email;
    }

    /**
     * Returns if a given string is a valid email.
     */
    public static boolean isValidEmail(String test) {
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
        if (!(other instanceof Email)) {
            return false;
        }

        Email otherEmail = (Email) other;
        return value.equals(otherEmail.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
