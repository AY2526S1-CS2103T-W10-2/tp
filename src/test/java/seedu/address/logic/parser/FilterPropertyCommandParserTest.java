package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LIMIT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OFFSET;
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

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FilterPropertyCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.property.predicates.PropertyMatchesFilterPredicate;


public class FilterPropertyCommandParserTest {
    private final FilterPropertyCommandParser parser = new FilterPropertyCommandParser();

    @Test
    public void parseValidArgsSuccessFirst() throws Exception {
        String input = " "
                + PREFIX_PROPERTY_TYPE + "condo "
                + PREFIX_PROPERTY_BEDROOM + "3 "
                + PREFIX_PROPERTY_FLOOR_AREA + "100 "
                + PREFIX_PROPERTY_STATUS + "available "
                + PREFIX_LIMIT + "5 "
                + PREFIX_OFFSET + "10";
        FilterPropertyCommand expected =
                new FilterPropertyCommand(
                        new PropertyMatchesFilterPredicate.Builder()
                                .withType("condo").withBedroom("3")
                                .withFloorArea("100").withStatus("available").build(),
                        5, 10);
        assertEquals(expected, parser.parse(input));

    }

    @Test
    public void parseValidArgsSuccessSecond() throws Exception {
        String input = " " + PREFIX_PROPERTY_OWNER + "alice";
        FilterPropertyCommand expected =
                new FilterPropertyCommand(
                        new PropertyMatchesFilterPredicate.Builder()
                                .withOwner("alice").build(),
                        Integer.MAX_VALUE, 0);
        assertEquals(expected, parser.parse(input));
    }

    @Test
    public void parseValidArgsSuccessThird() throws Exception {
        String input = " "
                + PREFIX_PROPERTY_ADDRESS + "Geylang 18 "
                + PREFIX_PROPERTY_POSTAL + "123000 "
                + PREFIX_PROPERTY_BATHROOM + "3 "
                + PREFIX_PROPERTY_PRICE + "5000 "
                + PREFIX_PROPERTY_LISTING + "rent";
        FilterPropertyCommand expected =
                new FilterPropertyCommand(
                        new PropertyMatchesFilterPredicate.Builder()
                                .withAddress("Geylang 18").withPostal("123000").withBathroom("3")
                                .withPrice("5000").withListing("rent").build(),
                        Integer.MAX_VALUE, 0); // defaults
        assertEquals(expected, parser.parse(input));
    }

    @Test
    public void parseEmptyArgSuccessForth() throws Exception {
        String input = "";
        FilterPropertyCommand expected = new FilterPropertyCommand(
                new PropertyMatchesFilterPredicate.Builder().build(),
                Integer.MAX_VALUE,
                0);
        assertEquals(expected, parser.parse(input));
    }

    @Test
    public void parseInvalidArgThrowsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(" " + "aaa"));
    }

    @Test
    public void parseInvalidTagThrowsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(" " + PREFIX_PROPERTY_OWNER));
    }

    @Test
    public void parseInvalidPrefixThrowsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(" " + "abc/123"));
    }

    @Test
    public void parseInvalidPostalThrowsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(" " + PREFIX_PROPERTY_POSTAL + "1234567"));
    }

    @Test
    public void parseInvalidTypeThrowsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(" " + PREFIX_PROPERTY_TYPE + "villa"));
    }

    @Test
    public void parseDuplicateTagThrowsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(
                " " + PREFIX_PROPERTY_TYPE + "condo " + PREFIX_PROPERTY_TYPE + "hdb"));
    }

    @Test
    public void parseInvalidBedroomThrowsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(" " + PREFIX_PROPERTY_BEDROOM + "22"));
    }

    @Test
    public void parseInvalidBathroomThrowsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(" " + PREFIX_PROPERTY_BATHROOM + "23"));
    }

    @Test
    public void parseInvalidFloorAreaThrowsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(" " + PREFIX_PROPERTY_FLOOR_AREA + "23sqrft"));
    }

    @Test
    public void parseInvalidPriceThrowsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(" " + PREFIX_PROPERTY_PRICE + "5000dollar"));
    }

    @Test
    public void parseInvalidStatusThrowsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(" " + PREFIX_PROPERTY_STATUS + "listed"));
    }

    @Test
    public void parseInvalidListingThrowsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(" " + PREFIX_PROPERTY_LISTING + "free"));
    }

    @Test
    public void parseInvalidLimitThrowsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(" " + PREFIX_LIMIT + "0"));
    }

    @Test
    public void parseInvalidOffsetThrowsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(" " + PREFIX_OFFSET + "-1"));
    }
}

