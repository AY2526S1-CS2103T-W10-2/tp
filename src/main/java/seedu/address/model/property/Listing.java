package seedu.address.model.property;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Property's listing type in the property book.
 * Guarantees: immutable; is valid as declared in
 * {@link #isValidListing(String)}
 */
public class Listing {
    public static final String MESSAGE_CONSTRAINTS = "Invalid listing \"VALUE\". Allowed: sale, rent.";

    /*
     * The listing must be either "sale" or "rent".
     * Case-insensitive matching is supported.
     */
    public static final String VALIDATION_REGEX = "^(?i)(sale|rent)$";

    public final String value;

    /**
     * Constructs a {@code Listing}.
     *
     * @param listing A valid listing type.
     */
    public Listing(String listing) {
        // Trim whitespace before validating
        listing = listing.trim();

        requireNonNull(listing);
        checkArgument(isValidListing(listing), MESSAGE_CONSTRAINTS.replace("VALUE", listing));
        value = listing.toLowerCase(); // Store in lowercase for consistency
    }

    /**
     * Returns true if a given string is a valid listing type.
     */
    public static boolean isValidListing(String test) {
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
        if (!(other instanceof Listing)) {
            return false;
        }

        Listing otherListing = (Listing) other;
        return value.equals(otherListing.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
