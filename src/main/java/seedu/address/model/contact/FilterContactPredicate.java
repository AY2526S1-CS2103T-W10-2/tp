package seedu.address.model.contact;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests whether a {@code Contact} matches the filtering criteria given.
 * <p>
 * Each field (name, phone, email, etc.) is optional. If a field is present, at least one
 * of its keywords must match the corresponding {@code Contact} field (case-insensitive, substring match).
 * Budget range (min/max) is inclusive.
 * <p>
 * Also has optional parameters ({@code limit} and {@code offset}),
 * which determine how many results should be shown and from which position.
 */
public class FilterContactPredicate implements Predicate<Contact> {
    private final Optional<List<String>> names;
    private final Optional<List<String>> phones;
    private final Optional<List<String>> emails;
    private final Optional<List<String>> addresses;
    private final Optional<Long> budgetMin;
    private final Optional<Long> budgetMax;
    private final Optional<List<String>> notes;
    private final Optional<List<String>> status;

    /**
     * Creates a {@code FilterContactPredicate} with optional filtering fields.
     * Any empty field means "no restriction" for that field.
     * @param names Optional list of name keywords.
     * @param phones Optional list of phone keywords.
     * @param emails Optional list of email keywords.
     * @param addresses Optional list of address keywords.
     * @param budgetMin Optional minimum budget.
     * @param budgetMax Optional maximum budget.
     * @param notes Optional list of note keywords.
     * @param status Optional list of status keywords.
     */
    public FilterContactPredicate(Optional<List<String>> names,
                                  Optional<List<String>> phones,
                                  Optional<List<String>> emails,
                                  Optional<List<String>> addresses,
                                  Optional<Long> budgetMin,
                                  Optional<Long> budgetMax,
                                  Optional<List<String>> notes,
                                  Optional<List<String>> status) {
        this.names = names;
        this.phones = phones;
        this.emails = emails;
        this.addresses = addresses;
        this.budgetMin = budgetMin;
        this.budgetMax = budgetMax;
        this.notes = notes;
        this.status = status;
    }

    @Override
    public boolean test(Contact contact) {
        // If no filters are specified, don't match anything
        if (names.isEmpty() && phones.isEmpty() && emails.isEmpty() && addresses.isEmpty()
                && budgetMin.isEmpty() && budgetMax.isEmpty()
                && notes.isEmpty() && status.isEmpty()) {
            return true; // return all
        }

        // OR logic: return true if ANY field matches
        boolean nameMatches = names.map(list ->
                        list.stream().anyMatch(k ->
                                StringUtil.containsSubstringIgnoreCase(contact.getName().fullName, k)))
                .orElse(false);

        boolean phoneMatches = phones.map(list ->
                        list.stream().anyMatch(k ->
                                StringUtil.containsSubstringIgnoreCase(contact.getPhone().value, k)))
                .orElse(false);

        boolean emailMatches = emails.map(list ->
                        list.stream().anyMatch(k ->
                                StringUtil.containsSubstringIgnoreCase(contact.getEmail().value, k)))
                .orElse(false);

        boolean addressMatches = addresses.map(list ->
                        list.stream().anyMatch(k ->
                                StringUtil.containsSubstringIgnoreCase(contact.getAddress().value, k)))
                .orElse(false);

        // person minimum is more than or equals to input filter minimum
        boolean budgetMinMatches = budgetMin.map(min ->
                Float.parseFloat(contact.getBudgetMin().value) >= min).orElse(false);

        // person maximum is less than or equals to input filter maximum
        boolean budgetMaxMatches = budgetMax.map(max ->
                Float.parseFloat(contact.getBudgetMax().value) <= max).orElse(false);

        boolean notesMatches = notes.map(list ->
                        list.stream().anyMatch(k ->
                                StringUtil.containsSubstringIgnoreCase(contact.getNotes().value, k)))
                .orElse(false);

        boolean statusMatches = status.map(list ->
                list.stream().anyMatch(k -> {
                    String targetStatus = contact.getStatus().value.trim().toLowerCase();
                    String keyword = k.trim().toLowerCase();
                    // exact match only (not substring) so that searching active, inactive is not returned
                    return targetStatus.equals(keyword);
                })).orElse(false);

        // Return true if ANY field matches
        return nameMatches || phoneMatches || emailMatches || addressMatches
                || budgetMinMatches || budgetMaxMatches
                || notesMatches || statusMatches;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof FilterContactPredicate
                && names.equals(((FilterContactPredicate) other).names)
                && phones.equals(((FilterContactPredicate) other).phones)
                && emails.equals(((FilterContactPredicate) other).emails)
                && addresses.equals(((FilterContactPredicate) other).addresses)
                && budgetMin.equals(((FilterContactPredicate) other).budgetMin)
                && budgetMax.equals(((FilterContactPredicate) other).budgetMax)
                && notes.equals(((FilterContactPredicate) other).notes)
                && status.equals(((FilterContactPredicate) other).status));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(FilterContactPredicate.class.getSimpleName()).append(" {");

        names.ifPresent(n -> sb.append("names=").append(n).append(", "));
        phones.ifPresent(p -> sb.append("phones=").append(p).append(", "));
        emails.ifPresent(e -> sb.append("emails=").append(e).append(", "));
        addresses.ifPresent(a -> sb.append("addresses=").append(a).append(", "));
        budgetMin.ifPresent(min -> sb.append("budgetMin=").append(min).append(", "));
        budgetMax.ifPresent(max -> sb.append("budgetMax=").append(max).append(", "));
        notes.ifPresent(n -> sb.append("notes=").append(n).append(", "));
        status.ifPresent(s -> sb.append("status=").append(s).append(", "));

        // Remove trailing comma and space if present
        if (sb.lastIndexOf(", ") == sb.length() - 2) {
            sb.delete(sb.length() - 2, sb.length());
        }

        sb.append("}");
        return sb.toString();
    }
}
