package seedu.address.model.uuid;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Represents a Person or Properties's unique identifier in the address or property book.
 * Guarantees: immutable; is valid as declared in {@link #isValidUuid(int)}.
 */
public class Uuid {

    public static final String MESSAGE_CONSTRAINTS = "UUID should be a positive integer.";

    private final int value;
    private final StoredItem itemType;

    /**
     * Enum class for tracking which type of item the uuid is attached to.
     */
    public static enum StoredItem {
        PROPERTY,
        PERSON;
    }

    /**
     * Constructs a {@code Uuid}.
     *
     * @param uuid A valid UUID integer.
     * @param itemType The stored item the ID is attached to.
     */
    public Uuid(int uuid, StoredItem itemType) {
        requireNonNull(uuid);
        checkArgument(isValidUuid(uuid), MESSAGE_CONSTRAINTS);
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
    public static boolean isValidUuid(int test) {
        return test > 0;
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
        return String.format("%d (%s)", value, itemType.name());
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
