package seedu.address.model.contact;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Contact's minimum budget in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidBudgetMin(String)}.
 */
public class BudgetMin {

    public static final String MESSAGE_CONSTRAINTS =
            "Minimum Budget should be a non-negative integer below than or equals to 200 million";
    public static final String VALIDATION_REGEX = "^\\d+$"; // allows empty string and leading zeros

    public final String value;

    /**
     * Constructs a {@code BudgetMin}.
     *
     * @param budgetMin A valid minimum budget.
     */
    public BudgetMin(String budgetMin) {
        requireNonNull(budgetMin);
        checkArgument(isValidBudgetMin(budgetMin), MESSAGE_CONSTRAINTS);
        value = String.valueOf(Long.parseLong(budgetMin)); // remove leading zeros
    }

    /**
     * Returns true if a given integer is a valid minimum budget.
     */
    public static boolean isValidBudgetMin(String test) {
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
                || (other instanceof BudgetMin
                && value.equals(((BudgetMin) other).value));
    }
}
