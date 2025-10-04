package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");

    /* Prefix definitions for property */
    public static final Prefix PREFIX_PROPERTY_ADDRESS = new Prefix("address/");
    public static final Prefix PREFIX_PROPERTY_BATHROOM = new Prefix("bathroom/");
    public static final Prefix PREFIX_PROPERTY_BEDROOM = new Prefix("bedroom/");
    public static final Prefix PREFIX_PROPERTY_FLOOR_AREA = new Prefix("floorarea/");
    public static final Prefix PREFIX_PROPERTY_LISTING = new Prefix("listing/");
    public static final Prefix PREFIX_PROPERTY_POSTAL = new Prefix("postal/");
    public static final Prefix PREFIX_PROPERTY_STATUS = new Prefix("status/");
    public static final Prefix PREFIX_PROPERTY_OWNER = new Prefix("owner/");
    public static final Prefix PREFIX_PROPERTY_PRICE = new Prefix("price/");
    public static final Prefix PREFIX_PROPERTY_TYPE = new Prefix("type/");
}
