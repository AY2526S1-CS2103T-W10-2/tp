package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Uuid;
import seedu.address.model.property.Property;

/**
 * Shows all clients associated with a specific property.
 * Displays the owner, buyers, and sellers linked to the property.
 */
public class ShowClientsCommand extends Command {

    public static final String COMMAND_WORD = "showclients";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Shows all clients associated with the specified property.\n"
            + "Parameters: p/PROPERTY_ID\n"
            + "Example: " + COMMAND_WORD + " p/5a2b3c";

    public static final String MESSAGE_SUCCESS = "Clients associated with Property %1$s:\n\n%2$s";

    public static final String MESSAGE_PROPERTY_NOT_FOUND =
            "Property with ID '%1$s' not found.\n"
                    + "Tip: Use 'showproperties' to see all available properties.";

    public static final String MESSAGE_NO_CLIENTS =
            "No clients are currently associated with Property %1$s.\n"
                    + "Tip: Use 'link p/%1$s r/buyer c/CLIENT_ID' to associate clients.";

    private final String propertyId;

    /**
     * Creates a ShowClientsCommand to display all clients associated with the specified property.
     *
     * @param propertyId The ID of the property to query.
     */
    public ShowClientsCommand(String propertyId) {
        requireNonNull(propertyId);
        this.propertyId = propertyId;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Find the property
        List<Property> propertyList = model.getFilteredPropertyList();
        Property targetProperty = propertyList.stream()
                .filter(property -> property.getId().equals(propertyId))
                .findFirst()
                .orElseThrow(() -> new CommandException(
                        String.format(MESSAGE_PROPERTY_NOT_FOUND, propertyId)));

        // Get all persons for lookup
        List<Person> personList = model.getFilteredPersonList();

        // Build the output message
        StringBuilder output = new StringBuilder();
        boolean hasClients = false;

        // Show Owner (Owner stores UUID as a String in owner.value)
        String ownerIdString = targetProperty.getOwner().value;
        if (ownerIdString != null && !ownerIdString.isEmpty()) {
            // Find person by UUID string
            Person owner = findPersonByUuidString(personList, ownerIdString);
            if (owner != null) {
                output.append(String.format("Owner: %s (UUID: %s)\n",
                        owner.getName(), owner.getUuid()));
                hasClients = true;
            }
        }

        // Show Buyers
        Set<Uuid> buyerUuids = targetProperty.getBuyingPersonIds();
        if (!buyerUuids.isEmpty()) {
            output.append("\nBuyers:\n");
            for (Uuid buyerUuid : buyerUuids) {
                Person buyer = findPersonByUuid(personList, buyerUuid);
                if (buyer != null) {
                    output.append(String.format("  - %s (UUID: %s)\n",
                            buyer.getName(), buyer.getUuid()));
                    hasClients = true;
                }
            }
        }

        // Show Sellers
        Set<Uuid> sellerUuids = targetProperty.getSellingPersonIds();
        if (!sellerUuids.isEmpty()) {
            output.append("\nSellers:\n");
            for (Uuid sellerUuid : sellerUuids) {
                Person seller = findPersonByUuid(personList, sellerUuid);
                if (seller != null) {
                    output.append(String.format("  - %s (UUID: %s)\n",
                            seller.getName(), seller.getUuid()));
                    hasClients = true;
                }
            }
        }

        if (!hasClients) {
            return new CommandResult(String.format(MESSAGE_NO_CLIENTS, propertyId));
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, propertyId, output.toString().trim()));
    }

    /**
     * Finds a person by their UUID from the person list.
     *
     * @param personList The list of all persons.
     * @param uuid The UUID to search for.
     * @return The Person object if found, null otherwise.
     */
    private Person findPersonByUuid(List<Person> personList, Uuid uuid) {
        return personList.stream()
                .filter(person -> person.getUuid().equals(uuid))
                .findFirst()
                .orElse(null);
    }

    /**
     * Finds a person by their UUID string from the person list.
     *
     * @param personList The list of all persons.
     * @param uuidString The UUID string to search for.
     * @return The Person object if found, null otherwise.
     */
    private Person findPersonByUuidString(List<Person> personList, String uuidString) {
        return personList.stream()
                .filter(person -> person.getUuid().toString().equals(uuidString))
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ShowClientsCommand)) {
            return false;
        }

        ShowClientsCommand otherCommand = (ShowClientsCommand) other;
        return propertyId.equals(otherCommand.propertyId);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("propertyId", propertyId)
                .toString();
    }
}
