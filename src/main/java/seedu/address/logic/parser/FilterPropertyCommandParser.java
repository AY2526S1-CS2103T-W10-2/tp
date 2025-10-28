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

import java.util.List;
import java.util.Optional;

import seedu.address.logic.commands.FilterPropertyCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.property.Bathroom;
import seedu.address.model.property.Bedroom;
import seedu.address.model.property.FloorArea;
import seedu.address.model.property.Listing;
import seedu.address.model.property.Owner;
import seedu.address.model.property.Postal;
import seedu.address.model.property.Price;
import seedu.address.model.property.Status;
import seedu.address.model.property.Type;
import seedu.address.model.property.predicates.PropertyMatchesFilterPredicate;

/**
 * Parses input arguments and creates a new FilterPropertyCommand object.
 */
public class FilterPropertyCommandParser implements Parser<FilterPropertyCommand> {

    private static final Prefix PREFIX_LIMIT = new Prefix("limit/");
    private static final Prefix PREFIX_OFFSET = new Prefix("offset/");

    /**
     * Parses the given {@code String} of arguments in the context of the FilterPropertyCommand
     * and returns an FilterPropertyCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public FilterPropertyCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_PROPERTY_ADDRESS, PREFIX_PROPERTY_POSTAL, PREFIX_PROPERTY_TYPE, PREFIX_PROPERTY_BEDROOM,
                PREFIX_PROPERTY_BATHROOM, PREFIX_PROPERTY_FLOOR_AREA, PREFIX_PROPERTY_PRICE, PREFIX_PROPERTY_STATUS,
                PREFIX_PROPERTY_OWNER, PREFIX_PROPERTY_LISTING, PREFIX_LIMIT, PREFIX_OFFSET);


        List<Prefix> validPrefixes = List.of(
                PREFIX_PROPERTY_ADDRESS, PREFIX_PROPERTY_POSTAL, PREFIX_PROPERTY_TYPE, PREFIX_PROPERTY_BEDROOM,
                PREFIX_PROPERTY_BATHROOM, PREFIX_PROPERTY_FLOOR_AREA, PREFIX_PROPERTY_PRICE, PREFIX_PROPERTY_STATUS,
                PREFIX_PROPERTY_OWNER, PREFIX_PROPERTY_LISTING, PREFIX_LIMIT, PREFIX_OFFSET
        );

        // Check for invalid prefixes
        if (!argMultimap.getAllPrefixes().isEmpty()) {
            List<String> invalidPrefixes = argMultimap.getAllPrefixes().stream()
                    .filter(p -> !p.getPrefix().isEmpty())
                    .filter(p -> validPrefixes.stream().noneMatch(v -> v.getPrefix().equals(p.getPrefix())))
                    .map(Prefix::getPrefix)
                    .toList();

            if (!invalidPrefixes.isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        FilterPropertyCommand.MESSAGE_USAGE));
            }
        }

        argMultimap.verifyNoDuplicatePrefixesFor(
                PREFIX_PROPERTY_ADDRESS, PREFIX_PROPERTY_POSTAL, PREFIX_PROPERTY_TYPE, PREFIX_PROPERTY_BEDROOM,
                PREFIX_PROPERTY_BATHROOM, PREFIX_PROPERTY_FLOOR_AREA, PREFIX_PROPERTY_PRICE, PREFIX_PROPERTY_STATUS,
                PREFIX_PROPERTY_OWNER, PREFIX_PROPERTY_LISTING, PREFIX_LIMIT, PREFIX_OFFSET
        );

        PropertyMatchesFilterPredicate.Builder builder = new PropertyMatchesFilterPredicate.Builder();

        Optional<String> maybeAddress = argMultimap.getValue(PREFIX_PROPERTY_ADDRESS);
        if (maybeAddress.isPresent()) {
            String t = maybeAddress.get().trim();
            if (!t.matches("^(?=.*[A-Za-z]).{0,200}$")) {
                throw new ParseException("Invalid address. Provide 1-200 chars with at least one letter");
            }
            builder.withAddress(t);
        }

        Optional<String> maybePostal = argMultimap.getValue(PREFIX_PROPERTY_POSTAL);
        if (maybePostal.isPresent()) {
            String t = maybePostal.get().trim();
            if (!Postal.isValidPostal(t)) {
                throw new ParseException("Error: Invalid postal code (Use a 6-digit postal code)");
            }
            builder.withPostal(t);
        }

        Optional<String> maybeType = argMultimap.getValue(PREFIX_PROPERTY_TYPE);
        if (maybeType.isPresent()) {
            String t = maybeType.get().trim();
            if (!Type.isValidType(t)) {
                throw new ParseException("Error: Invalid type (Allowed: hdb, condo, "
                        + "landed, apartment, office or others)");
            }
            builder.withType(t);
        }

        Optional<String> maybeBedroom = argMultimap.getValue(PREFIX_PROPERTY_BEDROOM);
        if (maybeBedroom.isPresent()) {
            String t = maybeBedroom.get().trim();
            if (!Bedroom.isValidBedroom(t)) {
                throw new ParseException("Error: Invalid bedroom (0–20)");
            }
            builder.withBedroom(t);
        }

        Optional<String> maybeBathroom = argMultimap.getValue(PREFIX_PROPERTY_BATHROOM);
        if (maybeBathroom.isPresent()) {
            String t = maybeBathroom.get().trim();
            if (!Bathroom.isValidBathroom(t)) {
                throw new ParseException("Error: Invalid bathroom (0–20)");
            }
            builder.withBathroom(t);
        }

        Optional<String> maybeFloorArea = argMultimap.getValue(PREFIX_PROPERTY_FLOOR_AREA);
        if (maybeFloorArea.isPresent()) {
            String t = maybeFloorArea.get().replace(",", "").trim();
            if (!FloorArea.isValidFloorArea(t)) {
                throw new ParseException("Error: Invalid floor area (digits only)");
            }
            builder.withFloorArea(t);
        }

        Optional<String> maybePrice = argMultimap.getValue(PREFIX_PROPERTY_PRICE);
        if (maybePrice.isPresent()) {
            String t = maybePrice.get().replace(",", "").trim();
            if (!Price.isValidPrice(t)) {
                throw new ParseException("Error: Invalid price (Use a positive integer ≤ 1,000,000,000,000.)");
            }
            builder.withPrice(t);
        }

        Optional<String> maybeStatus = argMultimap.getValue(PREFIX_PROPERTY_STATUS);
        if (maybeStatus.isPresent()) {
            String t = maybeStatus.get().trim().toLowerCase();
            if (!Status.isValidStatus(t)) {
                throw new ParseException("Error: Invalid status");
            }
            builder.withStatus(t);
        }

        Optional<String> maybeOwner = argMultimap.getValue(PREFIX_PROPERTY_OWNER);
        if (maybeOwner.isPresent()) {
            String t = maybeOwner.get().trim();
            if (!Owner.isValidOwner(t)) {
                throw new ParseException("Error: Invalid owner");
            }
            builder.withOwner(t);
        }

        Optional<String> maybeListing = argMultimap.getValue(PREFIX_PROPERTY_LISTING);
        if (maybeListing.isPresent()) {
            String t = maybeListing.get().trim().toLowerCase();
            if (!Listing.isValidListing(t)) {
                throw new ParseException("Error: Invalid listing");
            }
            builder.withListing(t);
        }

        int limit = 20;
        Optional<String> maybeLimit = argMultimap.getValue(PREFIX_LIMIT);
        if (maybeLimit.isPresent()) {
            String t = maybeLimit.get().trim();
            if (!t.matches("^[1-9]\\d*$")) {
                throw new ParseException(FilterPropertyCommand.MESSAGE_INVALID_LIMIT);
            }
            limit = Integer.parseInt(t);
        }

        int offset = 0;
        Optional<String> maybeOffset = argMultimap.getValue(PREFIX_OFFSET);
        if (maybeOffset.isPresent()) {
            String t = maybeOffset.get().trim();
            if (!t.matches("^0|[1-9]\\d*$")) {
                throw new ParseException(FilterPropertyCommand.MESSAGE_INVALID_OFFSET);
            }
            offset = Integer.parseInt(t);
        }

        return new FilterPropertyCommand(builder.build(), limit, offset);
    }
}
