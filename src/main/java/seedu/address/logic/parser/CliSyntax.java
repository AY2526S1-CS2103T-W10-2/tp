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
    public static final Prefix PREFIX_STATUS = new Prefix("status/"); // status
    public static final Prefix PREFIX_LIMIT = new Prefix("limit/"); // limit for filtering
    public static final Prefix PREFIX_OFFSET = new Prefix("offset/"); // offset for filtering


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

    /* Prefix definitions for linking */
    public static final Prefix PREFIX_PROPERTY_ID = new Prefix("p/");
    public static final Prefix PREFIX_LINK_RELATIONSHIP = new Prefix("r/");
    public static final Prefix PREFIX_CONTACT_ID = new Prefix("c/");
}
