package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class FilterContactPredicateTest {

    @Test
    public void equals() {
        FilterContactPredicate firstPredicate = new FilterContactPredicate(
                Optional.of(Collections.singletonList("Alice")),
                Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty());

        FilterContactPredicate secondPredicate = new FilterContactPredicate(
                Optional.of(Arrays.asList("Alice", "Bob")),
                Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty());

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        FilterContactPredicate firstPredicateCopy = new FilterContactPredicate(
                Optional.of(Collections.singletonList("Alice")),
                Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty());
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different predicate -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameMatches_returnsTrue() {
        FilterContactPredicate predicate = new FilterContactPredicate(
                Optional.of(Collections.singletonList("Alice")),
                Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty());

        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_phoneMatches_returnsTrue() {
        FilterContactPredicate predicate = new FilterContactPredicate(
                Optional.empty(),
                Optional.of(Collections.singletonList("12345")),
                Optional.empty(), Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty());

        assertTrue(predicate.test(new PersonBuilder().withPhone("12345").build()));
    }

    @Test
    public void test_emailMatches_returnsTrue() {
        FilterContactPredicate predicate = new FilterContactPredicate(
                Optional.empty(), Optional.empty(),
                Optional.of(Collections.singletonList("email@example.com")),
                Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty());

        assertTrue(predicate.test(new PersonBuilder().withEmail("email@example.com").build()));
    }

    @Test
    public void test_addressMatches_returnsTrue() {
        FilterContactPredicate predicate = new FilterContactPredicate(
                Optional.empty(), Optional.empty(), Optional.empty(),
                Optional.of(Collections.singletonList("Main Street")),
                Optional.empty(),
                Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty());

        assertTrue(predicate.test(new PersonBuilder().withAddress("Main Street Apt 1").build()));
    }

    @Test
    public void test_tagMatches_returnsTrue() {
        FilterContactPredicate predicate = new FilterContactPredicate(
                Optional.empty(), Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.of(Collections.singletonList("friend")),
                Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty());

        assertTrue(predicate.test(new PersonBuilder().withTags("friend", "colleague").build()));
    }

    @Test
    public void test_budgetMinAndMaxMatches_returnsTrue() {
        FilterContactPredicate predicate = new FilterContactPredicate(
                Optional.empty(), Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty(),
                Optional.of(100), Optional.of(500),
                Optional.empty(), Optional.empty());

        assertTrue(predicate.test(new PersonBuilder().withBudgetMin("100").withBudgetMax("500").build()));
    }

    @Test
    public void test_notesMatches_returnsTrue() {
        FilterContactPredicate predicate = new FilterContactPredicate(
                Optional.empty(), Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty(),
                Optional.of(Collections.singletonList("important")),
                Optional.empty());

        assertTrue(predicate.test(new PersonBuilder().withNotes("This is important").build()));
    }

    @Test
    public void test_statusMatches_returnsTrue() {
        FilterContactPredicate predicate = new FilterContactPredicate(
                Optional.empty(), Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty(),
                Optional.empty(),
                Optional.of(Collections.singletonList("active")));

        assertTrue(predicate.test(new PersonBuilder().withStatus("Active").build()));
    }

    @Test
    public void test_noMatches_returnsFalse() {
        FilterContactPredicate predicate = new FilterContactPredicate(
                Optional.of(Collections.singletonList("Carol")),
                Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty());

        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));
    }

    @Test
    public void toString_containsAllFields() {
        FilterContactPredicate predicate = new FilterContactPredicate(
                Optional.of(Arrays.asList("Alice", "Bob")),
                Optional.of(Arrays.asList("12345")),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.of(100),
                Optional.of(500),
                Optional.of(Arrays.asList("note1")),
                Optional.of(Arrays.asList("active"))
        );

        String result = predicate.toString();

        assertTrue(result.contains("FilterContactPredicate"));
        assertTrue(result.contains("names=[Alice, Bob]"));
        assertTrue(result.contains("phones=[12345]"));
        assertTrue(result.contains("budgetMin=100"));
        assertTrue(result.contains("budgetMax=500"));
        assertTrue(result.contains("notes=[note1]"));
        assertTrue(result.contains("status=[active]"));
    }

    @Test
    public void toString_emptyFields() {
        FilterContactPredicate predicate = new FilterContactPredicate(
                Optional.empty(), Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty(), Optional.empty()
        );

        String result = predicate.toString();

        assertTrue(result.contains("FilterContactPredicate"));
        assertTrue(result.endsWith("{}"));
    }
}
