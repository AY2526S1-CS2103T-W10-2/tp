package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_BATHROOM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_BEDROOM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_OWNER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_TYPE;

import seedu.address.logic.commands.FilterPropertyCommand;
import seedu.address.logic.parser.exceptions.ParseException;
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
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public FilterPropertyCommand parse(String args) throws ParseException {
        try {
            ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                    PREFIX_PROPERTY_ADDRESS, PREFIX_PROPERTY_TYPE, PREFIX_PROPERTY_BEDROOM, PREFIX_PROPERTY_BATHROOM,
                    PREFIX_PROPERTY_PRICE, PREFIX_PROPERTY_STATUS, PREFIX_PROPERTY_OWNER,
                    PREFIX_LIMIT, PREFIX_OFFSET);

            argMultimap.verifyNoDuplicatePrefixesFor(
                    PREFIX_PROPERTY_ADDRESS, PREFIX_PROPERTY_TYPE, PREFIX_PROPERTY_BEDROOM, PREFIX_PROPERTY_BATHROOM,
                    PREFIX_PROPERTY_PRICE, PREFIX_PROPERTY_STATUS, PREFIX_PROPERTY_OWNER, PREFIX_LIMIT, PREFIX_OFFSET
            );

            PropertyMatchesFilterPredicate.Builder builder = new PropertyMatchesFilterPredicate.Builder();

            argMultimap.getValue(PREFIX_PROPERTY_ADDRESS).ifPresent(s -> {
                String t = s.trim();
                if (t.isEmpty() || t.length() > 50) {
                    throw new RuntimeException("Error: address too long (max 50)");
                }
                builder.withAddress(t);
            });

            argMultimap.getValue(PREFIX_PROPERTY_TYPE).ifPresent(s -> builder.withType(s.trim()));

            argMultimap.getValue(PREFIX_PROPERTY_BEDROOM).ifPresent(s -> {
                String t = s.trim();
                if (!t.matches("^(?:[0-9]|1[0-9]|20)$")) {
                    throw new RuntimeException("Error: Invalid bedroom (0–20)");
                }
                builder.withBedroom(t);
            });

            argMultimap.getValue(PREFIX_PROPERTY_BATHROOM).ifPresent(s -> {
                String t = s.trim();
                if (!t.matches("^(?:[0-9]|1[0-9]|20)$")) {
                    throw new RuntimeException("Error: Invalid bathroom (0–20)");
                }
                builder.withBathroom(t);
            });

            argMultimap.getValue(PREFIX_PROPERTY_PRICE).ifPresent(s -> {
                String t = s.replace(",", "").trim();
                if (!t.matches("^\\d+$")) {
                    throw new RuntimeException("Error: Invalid price (digits only)");
                }
                builder.withPrice(t);
            });

            argMultimap.getValue(PREFIX_PROPERTY_STATUS).ifPresent(s -> {
                String t = s.trim().toLowerCase();
                if (!(t.equals("listed") || t.equals("sold") || t.equals("rented") || t.equals("off-market"))) {
                    throw new RuntimeException("Error: Invalid status");
                }
                builder.withStatus(t);
            });

            argMultimap.getValue(PREFIX_PROPERTY_OWNER).ifPresent(s -> {
                String t = s.trim();
                if (t.isEmpty() || t.length() > 50) {
                    throw new RuntimeException("Error: owner value too long");
                }
                builder.withOwner(t);
            });

            int limit = argMultimap.getValue(PREFIX_LIMIT)
                    .map(String::trim)
                    .map(Integer::parseInt)
                    .orElse(20);
            int offset = argMultimap.getValue(PREFIX_OFFSET)
                    .map(String::trim)
                    .map(Integer::parseInt)
                    .orElse(0);

            if (limit < 1) {
                throw new ParseException(FilterPropertyCommand.MESSAGE_INVALID_LIMIT);
            }
            if (offset < 0) {
                throw new ParseException(FilterPropertyCommand.MESSAGE_INVALID_OFFSET);
            }

            return new FilterPropertyCommand(builder.build(), limit, offset);
        } catch (RuntimeException e) {
            throw new ParseException(e.getMessage());
        } catch (Exception e) {
            throw new ParseException("Invalid input: " + e.getMessage());
        }
    }
}


