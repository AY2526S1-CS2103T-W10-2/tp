package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/"); // name
    public static final Prefix PREFIX_PHONE = new Prefix("p/"); // phone number
    public static final Prefix PREFIX_EMAIL = new Prefix("e/"); // email
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/"); // address
    public static final Prefix PREFIX_TAG = new Prefix("t/"); // tags
    public static final Prefix PREFIX_BUDGET_MIN = new Prefix("min/"); // budget min
    public static final Prefix PREFIX_BUDGET_MAX = new Prefix("max/"); // budget max
    public static final Prefix PREFIX_NOTES = new Prefix("notes/"); // notes
    public static final Prefix PREFIX_STATUS = new Prefix("s/"); // status


}
