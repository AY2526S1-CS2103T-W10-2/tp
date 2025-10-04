package seedu.address.model.property.exceptions;

/**
 * Signals that the operation is unable to find the specified property.
 */
public class PropertyNotFoundException extends RuntimeException {
    public PropertyNotFoundException() {
        super("Property not found");
    }
}

