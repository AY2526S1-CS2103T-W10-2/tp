package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyPropertyBook;

/**
 * Represents a storage for {@link seedu.address.model.PropertyBook}.
 */
public interface PropertyBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getPropertyBookFilePath();

    /**
     * Returns Property data as a {@link ReadOnlyPropertyBook}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    Optional<ReadOnlyPropertyBook> readPropertyBook() throws DataLoadingException;

    /**
     * @see #getPropertyBookFilePath()
     */
    Optional<ReadOnlyPropertyBook> readPropertyBook(Path filePath) throws DataLoadingException;

    /**
     * Saves the given {@link ReadOnlyPropertyBook} to the storage.
     * @param property cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void savePropertyBook(ReadOnlyPropertyBook property) throws IOException;

    /**
     * @see #savePropertyBook(ReadOnlyPropertyBook)
     */
    void savePropertyBook(ReadOnlyPropertyBook property, Path filePath) throws IOException;

}
