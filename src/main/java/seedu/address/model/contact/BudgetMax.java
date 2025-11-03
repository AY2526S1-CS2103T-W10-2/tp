package seedu.address.model.contact;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Contact's maximum budget in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidBudgetMax(String)}.
 */
public class BudgetMax {

    public static final String MESSAGE_CONSTRAINTS =
            "Maximum Budget should be a non-negative integer below than or equals to 200 billion";
    public static final String VALIDATION_REGEX = "^\\d+$"; // allows empty string and leading zeros

    public final String value;

    /**
     * Constructs a {@code BudgetMax}.
     *
     * @param budgetMax A valid maximum budget.
     */
    public BudgetMax(String budgetMax) {
        requireNonNull(budgetMax);
        checkArgument(isValidBudgetMax(budgetMax), MESSAGE_CONSTRAINTS);
        value = String.valueOf(Long.parseLong(budgetMax)); // remove leading zeros
    }

    /**
     * Returns true if a given integer is a valid maximum budget.
     */
    public static boolean isValidBudgetMax(String test) {
        if (test == null || !test.matches(VALIDATION_REGEX)) {
            return false;
        }
        try {
            long value = Long.parseLong(test);
            return value >= 0 && value <= 200_000_000_000L; // range check
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof BudgetMax
                && value.equals(((BudgetMax) other).value));
    }
}
