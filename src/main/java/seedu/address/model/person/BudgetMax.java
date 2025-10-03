package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's maximum budget in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidBudgetMax(String)}.
 */
public class BudgetMax {

    public static final String MESSAGE_CONSTRAINTS = "BudgetMax should be a non-negative integer.";
    public static final String VALIDATION_REGEX = "^\\d+$"; // zero or more

    public final String value;

    /**
     * Constructs a {@code BudgetMax}.
     *
     * @param budgetMax A valid maximum budget.
     */
    public BudgetMax(String budgetMax) {
        requireNonNull(budgetMax);
        checkArgument(isValidBudgetMax(budgetMax), MESSAGE_CONSTRAINTS);
        value = budgetMax;
    }

    /**
     * Returns true if a given integer is a valid maximum budget.
     */
    public static boolean isValidBudgetMax(String test) {
        return test != null && test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof BudgetMax
                && value == ((BudgetMax) other).value);
    }
}
