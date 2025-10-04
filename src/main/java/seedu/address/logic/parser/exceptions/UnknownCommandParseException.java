package seedu.address.logic.parser.exceptions;

/**
 * Signals that a parser did not recognise the command word and another parser may handle it.
 */
public class UnknownCommandParseException extends ParseException {

    public UnknownCommandParseException(String message) {
        super(message);
    }

    public UnknownCommandParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
