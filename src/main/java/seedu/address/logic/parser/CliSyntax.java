package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Raw prefix definitions */
    public static final String PREFIX_NAME_RAW = "n/";
    public static final String PREFIX_PHONE_RAW = "p/";
    public static final String PREFIX_EMAIL_RAW = "e/";
    public static final String PREFIX_ADDRESS_RAW = "a/";
    public static final String PREFIX_BUDGET_MIN_RAW = "min/";
    public static final String PREFIX_BUDGET_MAX_RAW = "max/";
    public static final String PREFIX_NOTES_RAW = "notes/";
    public static final String PREFIX_STATUS_RAW = "s/";

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix(PREFIX_NAME_RAW); // name
    public static final Prefix PREFIX_PHONE = new Prefix(PREFIX_PHONE_RAW); // phone
    public static final Prefix PREFIX_EMAIL = new Prefix(PREFIX_EMAIL_RAW); // email
    public static final Prefix PREFIX_ADDRESS = new Prefix(PREFIX_ADDRESS_RAW); // address
    public static final Prefix PREFIX_BUDGET_MIN = new Prefix(PREFIX_BUDGET_MIN_RAW); // budget min
    public static final Prefix PREFIX_BUDGET_MAX = new Prefix(PREFIX_BUDGET_MAX_RAW); // budget max
    public static final Prefix PREFIX_NOTES = new Prefix(PREFIX_NOTES_RAW); // notes
    public static final Prefix PREFIX_STATUS = new Prefix(PREFIX_STATUS_RAW); // status

    /* Prefix definitions for property */
    public static final Prefix PREFIX_PROPERTY_ADDRESS = new Prefix(PREFIX_ADDRESS_RAW);
    public static final Prefix PREFIX_PROPERTY_BATHROOM = new Prefix("bath/");
    public static final Prefix PREFIX_PROPERTY_BEDROOM = new Prefix("bed/");
    public static final Prefix PREFIX_PROPERTY_FLOOR_AREA = new Prefix("f/");
    public static final Prefix PREFIX_PROPERTY_LISTING = new Prefix("l/");
    public static final Prefix PREFIX_PROPERTY_POSTAL = new Prefix("postal/");
    public static final Prefix PREFIX_PROPERTY_STATUS = new Prefix("status/");
    public static final Prefix PREFIX_PROPERTY_OWNER = new Prefix("o/");
    public static final Prefix PREFIX_PROPERTY_PRICE = new Prefix("price/");
    public static final Prefix PREFIX_PROPERTY_TYPE = new Prefix("type/");

    /* Prefix definitions for linking */
    public static final Prefix PREFIX_PROPERTY_ID = new Prefix("p/");
    public static final Prefix PREFIX_LINK_RELATIONSHIP = new Prefix("r/");
    public static final Prefix PREFIX_CONTACT_ID = new Prefix("c/");
}
