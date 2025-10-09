package seedu.address.model.property.predicates;

import java.util.Locale;
import java.util.function.Predicate;

import seedu.address.model.property.Property;

/**
 * Checks if a Property matches all filter conditions (logical AND).
 * Uses case-insensitive substring for text fields; equals for enums; exact match for numbers.
 *
 * - Price is an integer-like String (no decimals)
 * - Type allowed: HDB, Condo, Landed, Apartment, Office, Others
 * - Status allowed: listed, sold, rented, off-market
 */
public class PropertyMatchesFilterPredicate implements Predicate<Property> {

    private final String address; // substring (case-insensitive)
    private final String type; // equalsIgnoreCase to Type.toString()
    private final String bedroom; // numeric string (0..20)
    private final String bathroom; // numeric string (0..20)
    private final String price; // integer string (no commas)
    private final String status; // equalsIgnoreCase to Status.toString()
    private final String owner; // substring of Owner.toString()

    /**
     * Create a filter predicate which checks if a Property matches all filter conditions.
     */
    public PropertyMatchesFilterPredicate(
            String address, String type, String bedroom, String bathroom,
            String price, String status, String owner) {
        this.address = norm(address);
        this.type = norm(type);
        this.bedroom = norm(bedroom);
        this.bathroom = norm(bathroom);
        this.price = norm(price == null ? null : price.replace(",", "")); // accept "500,000"
        this.status = norm(status);
        this.owner = norm(owner);
    }

    /**
     * Turn string into lower case
     *
     */
    private static String norm(String s) {
        return s == null ? null : s.trim().toLowerCase();
    }

    /**
     * Returns true if the property matches all given filters.
     */
    @Override
    public boolean test(Property p) {
        // address substring
        if (address != null && !p.getPropertyAddress().toString().toLowerCase().contains(address)) {
            return false;
        }

        // type equality (case-insensitive)
        if (type != null && !p.getType().toString().equalsIgnoreCase(type)) {
            return false;
        }

        // bedrooms equals
        if (bedroom != null) {
            String bedVal = p.getBedroom().value; // your wrapper stores string
            if (!bedVal.equals(bedroom)) {
                return false;
            }
        }

        // bathrooms equals
        if (bathroom != null) {
            String bathVal = p.getBathroom().value;
            if (!bathVal.equals(bathroom)) {
                return false;
            }
        }

        // price equals (integer string)
        if (price != null) {
            String priceVal = p.getPrice().value; // digits only
            if (!priceVal.equals(price)) {
                return false;
            }
        }

        // status equality (case-insensitive)
        if (status != null && !p.getStatus().toString().equalsIgnoreCase(status)) {
            return false;
        }

        // owner substring (case-insensitive; owner is an id-like string)
        if (owner != null && !p.getOwner().toString().toLowerCase(Locale.ROOT).contains(owner)) {
            return false;
        }

        return true;
    }

    /**
     * Builder for {@link PropertyMatchesFilterPredicate}.
     * Use to set any subset of filters, then call {@link #build()}.
     */
    public static class Builder {
        private String address;
        private String type;
        private String bedroom;
        private String bathroom;
        private String price;
        private String status;
        private String owner;

        /**
         * Sets the address substring filter (case-insensitive).
         */
        public Builder withAddress(String s) {
            this.address = s;
            return this;
        }

        /**
         * Sets the type filter (e.g. HDB, Condo, Landed).
         * Compared case-insensitively.
         */
        public Builder withType(String s) {
            this.type = s;
            return this;
        }

        /**
         * Sets the bedroom filter.
         */
        public Builder withBedroom(String s) {
            this.bedroom = s;
            return this;
        }

        /**
         * Sets the bathroom filter
         */
        public Builder withBathroom(String s) {
            this.bathroom = s;
            return this;
        }

        /**
         * Sets the price filter.
         */
        public Builder withPrice(String s) {
            this.price = s;
            return this;
        }

        /**
         * Sets the status filter.
         */
        public Builder withStatus(String s) {
            this.status = s;
            return this;
        }

        /**
         * Sets the owner substring filter (case-insensitive).
         */
        public Builder withOwner(String s) {
            this.owner = s;
            return this;
        }

        /**
         * Builds a predicate with the current filters.
         */
        public PropertyMatchesFilterPredicate build() {
            return new PropertyMatchesFilterPredicate(address, type, bedroom, bathroom, price, status, owner);
        }
    }
}
