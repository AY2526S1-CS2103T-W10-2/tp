package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.property.Property;

/**
 * Unmodifiable view of a property
 */
public interface ReadOnlyPropertyBook {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Property> getPropertyList();

}
