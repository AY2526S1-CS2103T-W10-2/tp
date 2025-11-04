package seedu.address.model.uuid;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Represents a Contact or Properties's unique identifier in the address or property book.
 * Guarantees: immutable; is valid as declared in {@link #isValidUuid(String)}.
 */
public class Uuid {

    public static final String MESSAGE_CONSTRAINTS = "UUID should be a positive integer. (maximum of 2000000)";

    private final int value;
    private final StoredItem itemType;

    /**
     * Enum class for tracking which type of item the uuid is attached to.
     */
    public static enum StoredItem {
        PROPERTY,
        CONTACT;
    }

    /**
     * Constructs a {@code Uuid}.
     *
     * @param uuid A valid UUID integer.
     * @param itemType The stored item the ID is attached to.
     */
    public Uuid(int uuid, StoredItem itemType) {
        requireNonNull(uuid);
        checkArgument(isValidUuid(String.valueOf(uuid)), MESSAGE_CONSTRAINTS);
        value = uuid;
        this.itemType = itemType;
    }

    /**
     * Constructor that duplicates {@code Uuid}.
     *
     * @param uuid A {@code Uuid}
     */
    public Uuid(Uuid uuid) {
        requireNonNull(uuid);
        this.value = uuid.value;
        this.itemType = uuid.itemType;
    }

    /**
     * Returns true if a given integer is a valid UUID.
     */
    public static boolean isValidUuid(String test) {
        try { // maximum UUID is 2 million to ensure integer won't overflow
            float value = Float.parseFloat(test);
            return value > 0 && value <= 2_000_000;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Getter for value of Uuid. This is to prevent overwriting of value.
     */
    public int getValue() {
        return value;
    }

    public static String getGuiSetDisplayAsString(Set<Uuid> uuids) {
        return uuids.stream().map(id -> id.getValue() + "").sorted().collect(Collectors.joining(", "));
    }

    @Override
    public String toString() {
        return String.format("%d", value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Uuid
                && value == ((Uuid) other).value
                && itemType == ((Uuid) other).itemType);
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(value);
    }
}
