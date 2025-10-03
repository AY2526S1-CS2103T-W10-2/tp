package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's minimum budget in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidBudgetMin(String)}.
 */
public class BudgetMin {

    public static final String MESSAGE_CONSTRAINTS = "BudgetMin should be a non-negative integer.";
    public static final String VALIDATION_REGEX = "^\\d+$"; // zero or more

    public final String value;

    /**
     * Constructs a {@code BudgetMin}.
     *
     * @param budgetMin A valid minimum budget.
     */
    public BudgetMin(String budgetMin) {
        requireNonNull(budgetMin);
        checkArgument(isValidBudgetMin(budgetMin), MESSAGE_CONSTRAINTS);
        value = budgetMin;
    }

    /**
     * Returns true if a given integer is a valid minimum budget.
     */
    public static boolean isValidBudgetMin(String test) {
        return test != null && test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof BudgetMin
                && value == ((BudgetMin) other).value);
    }
}
