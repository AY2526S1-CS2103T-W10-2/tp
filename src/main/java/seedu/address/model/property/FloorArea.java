package seedu.address.model.property;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Property's floor area in the property book.
 * Guarantees: immutable; is valid as declared in
 * {@link #isValidFloorArea(String)}
 */
public class FloorArea {
    public static final String MESSAGE_CONSTRAINTS =
            "Invalid floor-area value. Use an integer between 50 and 100000 (sqft).";

    /*
     * The floor area must be an integer between 50 and 100000 (inclusive).
     */
    public static final String VALIDATION_REGEX = "^(?:5[0-9]|[6-9][0-9]|[1-9][0-9]{2,4}|100000)$";

    public final String value;

    /**
     * Constructs a {@code FloorArea}.
     *
     * @param floorArea A valid floor area value.
     */
    public FloorArea(String floorArea) {
        // Trim whitespace before validating
        floorArea = floorArea.trim();

        requireNonNull(floorArea);
        checkArgument(isValidFloorArea(floorArea), MESSAGE_CONSTRAINTS);
        value = floorArea;
    }

    /**
     * Returns true if a given string is a valid floor area.
     */
    public static boolean isValidFloorArea(String test) {
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
        if (!(other instanceof FloorArea)) {
            return false;
        }

        FloorArea otherFloorArea = (FloorArea) other;
        return value.equals(otherFloorArea.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
