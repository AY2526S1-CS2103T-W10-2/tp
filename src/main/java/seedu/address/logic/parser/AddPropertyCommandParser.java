package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_BATHROOM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_BEDROOM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_FLOOR_AREA;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_LISTING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_OWNER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_POSTAL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_TYPE;

import java.util.stream.Stream;

import seedu.address.logic.commands.AddPropertyCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.property.Bathroom;
import seedu.address.model.property.Bedroom;
import seedu.address.model.property.FloorArea;
import seedu.address.model.property.Listing;
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

    /**
     * Parses the given {@code String} of arguments in the context of the AddPropertyCommand
     * and returns an AddPropertyCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public AddPropertyCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PROPERTY_ADDRESS, PREFIX_PROPERTY_POSTAL,
                        PREFIX_PROPERTY_PRICE, PREFIX_PROPERTY_TYPE, PREFIX_PROPERTY_STATUS,
                        PREFIX_PROPERTY_BEDROOM, PREFIX_PROPERTY_BATHROOM, PREFIX_PROPERTY_FLOOR_AREA,
                        PREFIX_PROPERTY_LISTING, PREFIX_PROPERTY_OWNER);

        if (!arePrefixesPresent(argMultimap, PREFIX_PROPERTY_ADDRESS, PREFIX_PROPERTY_POSTAL,
                PREFIX_PROPERTY_PRICE, PREFIX_PROPERTY_TYPE, PREFIX_PROPERTY_STATUS,
                PREFIX_PROPERTY_BEDROOM, PREFIX_PROPERTY_BATHROOM, PREFIX_PROPERTY_FLOOR_AREA,
                PREFIX_PROPERTY_OWNER)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPropertyCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_PROPERTY_ADDRESS, PREFIX_PROPERTY_POSTAL,
                PREFIX_PROPERTY_PRICE, PREFIX_PROPERTY_TYPE, PREFIX_PROPERTY_STATUS,
                PREFIX_PROPERTY_BEDROOM, PREFIX_PROPERTY_BATHROOM, PREFIX_PROPERTY_FLOOR_AREA,
                PREFIX_PROPERTY_LISTING, PREFIX_PROPERTY_OWNER);

        PropertyAddress address = ParserUtil.parsePropertyAddress(argMultimap.getValue(PREFIX_PROPERTY_ADDRESS).get());
        Postal postal = ParserUtil.parsePostal(argMultimap.getValue(PREFIX_PROPERTY_POSTAL).get());
        Price price = ParserUtil.parsePrice(argMultimap.getValue(PREFIX_PROPERTY_PRICE).get());
        Type type = ParserUtil.parseType(argMultimap.getValue(PREFIX_PROPERTY_TYPE).get());
        Status status = ParserUtil.parseStatus(argMultimap.getValue(PREFIX_PROPERTY_STATUS).get());
        Bedroom bedroom = ParserUtil.parseBedroom(argMultimap.getValue(PREFIX_PROPERTY_BEDROOM).get());
        Bathroom bathroom = ParserUtil.parseBathroom(argMultimap.getValue(PREFIX_PROPERTY_BATHROOM).get());
        FloorArea floorArea = ParserUtil.parseFloorArea(argMultimap.getValue(PREFIX_PROPERTY_FLOOR_AREA).get());
        Owner owner = ParserUtil.parseOwner(argMultimap.getValue(PREFIX_PROPERTY_OWNER).get());

        // Listing is optional
        Listing listing = null;
        if (argMultimap.getValue(PREFIX_PROPERTY_LISTING).isPresent()) {
            listing = ParserUtil.parseListing(argMultimap.getValue(PREFIX_PROPERTY_LISTING).get());
        }

        Property property = new Property(address, bathroom, bedroom, floorArea, listing,
                postal, price, status, type, owner);

        return new AddPropertyCommand(property);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
