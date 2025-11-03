package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_BATHROOM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_BEDROOM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_FLOOR_AREA;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_OWNER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_POSTAL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_TYPE;

import java.util.HashSet;

import seedu.address.logic.commands.AddPropertyCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.property.Bathroom;
import seedu.address.model.property.Bedroom;
import seedu.address.model.property.FloorArea;
import seedu.address.model.property.Owner;
import seedu.address.model.property.Postal;
import seedu.address.model.property.Price;
import seedu.address.model.property.Property;
import seedu.address.model.property.PropertyAddress;
import seedu.address.model.property.Status;
import seedu.address.model.property.Type;

/**
 * Parses input arguments and creates a new AddPropertyCommand object
 */
public class AddPropertyCommandParser implements Parser<AddPropertyCommand> {
    public static final String POSTAL_MISSING = "Postal parameter (" + PREFIX_PROPERTY_POSTAL + "POSTAL) is missing.\n";
    public static final String ADDRESS_MISSING = "Address parameter (" + PREFIX_PROPERTY_ADDRESS
            + "ADDRESS) is missing.\n";
    public static final String PRICE_MISSING = "Price parameter (" + PREFIX_PROPERTY_PRICE + "PRICE) is missing.\n";
    public static final String TYPE_MISSING = "Type parameter (" + PREFIX_PROPERTY_TYPE + "TYPE) is missing.\n";
    public static final String STATUS_MISSING = "Status parameter (" + PREFIX_PROPERTY_STATUS + "STATUS) is missing.\n";
    public static final String BEDROOM_MISSING = "Bedroom parameter (" + PREFIX_PROPERTY_BEDROOM
            + "BEDROOM) is missing.\n";
    public static final String BATHROOM_MISSING = "Bathroom parameter (" + PREFIX_PROPERTY_BATHROOM
            + "BATHROOM) is missing.\n";
    public static final String FLOOR_AREA_MISSING = "Floor Area parameter (" + PREFIX_PROPERTY_FLOOR_AREA
            + "FLOOR_AREA) is missing.\n";
    public static final String OWNER_MISSING = "Owner parameter (" + PREFIX_PROPERTY_OWNER + "OWNER) is missing.\n";

    /**
     * Parses the given {@code String} of arguments in the context of the
     * AddPropertyCommand
     * and returns an AddPropertyCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public AddPropertyCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_PROPERTY_ADDRESS,
                PREFIX_PROPERTY_POSTAL,
                PREFIX_PROPERTY_PRICE, PREFIX_PROPERTY_TYPE, PREFIX_PROPERTY_STATUS,
                PREFIX_PROPERTY_BEDROOM, PREFIX_PROPERTY_BATHROOM, PREFIX_PROPERTY_FLOOR_AREA,
                PREFIX_PROPERTY_OWNER);

        String missingPrefix = validateRequiredPrefixesPresent(argMultimap);
        if (missingPrefix != null) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    missingPrefix + AddPropertyCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_PROPERTY_ADDRESS, PREFIX_PROPERTY_POSTAL,
                PREFIX_PROPERTY_PRICE, PREFIX_PROPERTY_TYPE, PREFIX_PROPERTY_STATUS,
                PREFIX_PROPERTY_BEDROOM, PREFIX_PROPERTY_BATHROOM, PREFIX_PROPERTY_FLOOR_AREA,
                PREFIX_PROPERTY_OWNER);

        PropertyAddress address = ParserUtil
                .parsePropertyAddress(argMultimap.getValue(PREFIX_PROPERTY_ADDRESS).get());
        Postal postal = ParserUtil.parsePostal(argMultimap.getValue(PREFIX_PROPERTY_POSTAL).get());
        Price price = ParserUtil.parsePrice(argMultimap.getValue(PREFIX_PROPERTY_PRICE).get());
        Type type = ParserUtil.parseType(argMultimap.getValue(PREFIX_PROPERTY_TYPE).get());
        Status status = ParserUtil.parseStatus(argMultimap.getValue(PREFIX_PROPERTY_STATUS).get());
        Bedroom bedroom = ParserUtil.parseBedroom(argMultimap.getValue(PREFIX_PROPERTY_BEDROOM).get());
        Bathroom bathroom = ParserUtil.parseBathroom(argMultimap.getValue(PREFIX_PROPERTY_BATHROOM).get());
        FloorArea floorArea = ParserUtil.parseFloorArea(argMultimap.getValue(PREFIX_PROPERTY_FLOOR_AREA).get());
        Owner owner = ParserUtil.parseOwner(argMultimap.getValue(PREFIX_PROPERTY_OWNER).get());

        Property property = new Property(null, address, bathroom, bedroom, floorArea,
                postal, price, status, type, owner, new HashSet<>(), new HashSet<>());

        return new AddPropertyCommand(property);
    }

    // Helper method to check for required prefixes and throw relevant error
    // messages
    private String validateRequiredPrefixesPresent(ArgumentMultimap argMultimap) throws ParseException {
        boolean hasAddress = argMultimap.arePrefixesPresent(PREFIX_PROPERTY_ADDRESS);
        boolean hasPostal = argMultimap.arePrefixesPresent(PREFIX_PROPERTY_POSTAL);
        boolean hasPrice = argMultimap.arePrefixesPresent(PREFIX_PROPERTY_PRICE);
        boolean hasType = argMultimap.arePrefixesPresent(PREFIX_PROPERTY_TYPE);
        boolean hasStatus = argMultimap.arePrefixesPresent(PREFIX_PROPERTY_STATUS);
        boolean hasBedroom = argMultimap.arePrefixesPresent(PREFIX_PROPERTY_BEDROOM);
        boolean hasBathroom = argMultimap.arePrefixesPresent(PREFIX_PROPERTY_BATHROOM);
        boolean hasFloorArea = argMultimap.arePrefixesPresent(PREFIX_PROPERTY_FLOOR_AREA);
        boolean hasOwner = argMultimap.arePrefixesPresent(PREFIX_PROPERTY_OWNER);

        if (!hasAddress) {
            return ADDRESS_MISSING;
        }
        if (!hasPostal) {
            return POSTAL_MISSING;
        }
        if (!hasPrice) {
            return PRICE_MISSING;
        }
        if (!hasType) {
            return TYPE_MISSING;
        }
        if (!hasStatus) {
            return STATUS_MISSING;
        }
        if (!hasBedroom) {
            return BEDROOM_MISSING;
        }
        if (!hasBathroom) {
            return BATHROOM_MISSING;
        }
        if (!hasFloorArea) {
            return FLOOR_AREA_MISSING;
        }
        if (!hasOwner) {
            return OWNER_MISSING;
        }
        return null;
    }
}
