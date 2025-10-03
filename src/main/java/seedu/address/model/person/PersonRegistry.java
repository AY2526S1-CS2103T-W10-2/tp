package seedu.address.model.person;

import java.util.HashMap;
import java.util.Map;

/**
 * Maintains a registry of all {@link Person} objects in the address book.
 * <p>
 * Each person is stored in a {@link Map}, keyed by their unique {@link Uuid}.
 * This allows for efficient lookup, addition, and removal of persons by ID.
 * </p>
 */
public class PersonRegistry {
    private final Map<Integer, Person> personMap = new HashMap<>();

    /**
     * Adds a person to the registry.
     * If a person with the same UUID already exists, it will be replaced.
     *
     * @param person The person to add. Must not be {@code null}.
     */
    public void addPerson(Person person) {
        personMap.put(person.getUuid().value, person);
    }

    /**
     * Retrieves a person from the registry by their unique ID.
     *
     * @param id The unique UUID of the person.
     * @return The {@link Person} associated with the given ID, or {@code null} if not found.
     */
    public Person getPerson(Integer id) {
        return personMap.get(id);
    }

    /**
     * Checks whether a person with the given ID exists in the registry.
     *
     * @param id The unique UUID of the person.
     * @return {@code true} if the registry contains a person with the given ID,
     *         {@code false} otherwise.
     */
    public boolean containsId(Integer id) {
        return personMap.containsKey(id);
    }

    /**
     * Removes a person from the registry by their ID.
     * If no person with the given ID exists, no action is taken.
     *
     * @param id The unique UUID of the person to remove.
     */
    public void removePerson(Integer id) {
        personMap.remove(id);
    }
}
