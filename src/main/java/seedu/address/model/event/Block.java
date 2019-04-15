package seedu.address.model.event;

import static java.util.Objects.requireNonNull;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.function.Predicate;

/**
 * Block class defines a block of time, in hours and minutes.
 */
public class Block {

    public static final String AFTERNOON = "afternoon";
    public static final String BREAKFAST = "breakfast";
    public static final String BRUNCH = "brunch";
    public static final String DINNER = "dinner";
    public static final String EVENING = "evening";
    public static final String LUNCH = "lunch";
    public static final String MESSAGE_CONSTRAINTS = "Block must be a set of two times in HH:MM format"
            + " separated by a single space. "
            + "It can be negated with a ! prefix.\n"
            + "For example, 12:00 14:00 denotes a block of time between 12pm and 2pm,\n"
            + " and !23:00 2:00 denotes a block of time not between 11pm and 2am.";
    public static final String MIDNIGHT = "midnight";
    public static final String MORNING = "morning";
    public static final String NIGHT = "night";
    public static final String SCHOOL = "school";
    public static final String SUPPER = "supper";

    private final LocalTime first;
    private final LocalTime second;
    private final Predicate<LocalDateTime> tester;
    private final Duration d;
    private final boolean nonBlock;

    /**
     * Constructs a block instance using two LocalTimes, and whether the block should be negated.
     * @param first     The start time of the block.
     * @param second    The end time of the block.
     * @param negated   Whether the block of time is negated.
     */
    public Block(LocalTime first, LocalTime second, boolean negated) {
        requireNonNull(first);
        requireNonNull(second);

        // If first equals second, then this block is a non-block and all events fall within this block.
        if (first.equals(second)) {
            nonBlock = true;
        } else {
            nonBlock = false;
        }

        // If the block is negated, simply reverse first and second.
        if (negated) {
            this.second = first;
            this.first = second;
        } else {
            this.first = first;
            this.second = second;
        }

        // Setting up testers.
        Predicate<LocalDateTime> firstPredicate = x -> this.first.isBefore(x.toLocalTime())
                || this.first.equals(x.toLocalTime());
        Predicate<LocalDateTime> secondPredicate = x -> this.second.isAfter(x.toLocalTime())
                || this.second.equals(x.toLocalTime());

        // Setting the duration of this block.
        if (this.first.isBefore(this.second)) {
            tester = firstPredicate.and(secondPredicate);
            d = Duration.between(this.first, this.second);
        } else {
            tester = firstPredicate.or(secondPredicate);
            d = Duration.between(this.second, this.first);
        }

    }

    /**
     * Returns a {@code block} that represents the morning, from 6am to noon.
     * @param negated Whether this morning block should be negated.
     * @return The morning block (negated or otherwise).
     */
    public static Block morning(boolean negated) {
        return new Block(LocalTime.parse("06:00"), LocalTime.NOON, negated);
    }

    /**
     * Returns a {@code block} that represents the afternoon, from noon to 6pm.
     * @param negated Whether this afternoon block should be negated.
     * @return The afternoon block (negated or otherwise).
     */
    public static Block afternoon(boolean negated) {
        return new Block(LocalTime.NOON, LocalTime.parse("18:00"), negated);
    }

    /**
     * Returns a {@code block} that represents the evening, from 6pm to 8pm.
     * @param negated Whether this evening block should be negated.
     * @return The evening block (negated or otherwise).
     */
    public static Block evening(boolean negated) {
        return new Block(LocalTime.parse("18:00"), LocalTime.parse("20:00"), negated);
    }

    /**
     * Returns a {@code block} that represents night, from 8pm to midnight.
     * @param negated Whether this night block should be negated.
     * @return The night block (negated or otherwise).
     */
    public static Block night(boolean negated) {
        return new Block(LocalTime.parse("20:00"), LocalTime.MIDNIGHT, negated);
    }

    /**
     * Returns a {@code block} that represents midnight, from 12am to 6am.
     * @param negated Whether this midnight block should be negated.
     * @return The midnight block (negated or otherwise).
     */
    public static Block midnight(boolean negated) {
        return new Block(LocalTime.MIDNIGHT, LocalTime.parse("06:00"), negated);
    }

    /**
     * Returns a {@code block} that represents school hours, from 8am to 6pm.
     * @param negated Whether this school block should be negated.
     * @return The school block (negated or otherwise).
     */
    public static Block school(boolean negated) {
        return new Block(LocalTime.parse("08:00"), LocalTime.parse("18:00"), negated);
    }

    /**
     * Returns a {@code block} that represents breakfast, from 7am to 10am.
     * @param negated Whether this breakfast block should be negated.
     * @return The breakfast block (negated or otherwise).
     */
    public static Block breakfast(boolean negated) {
        return new Block(LocalTime.parse("07:00"), LocalTime.parse("10:00"), negated);
    }

    /**
     * Returns a {@code block} that represents lunch, from 12pm to 2pm.
     * @param negated Whether this lunch block should be negated.
     * @return The lunch block (negated or otherwise).
     */
    public static Block lunch(boolean negated) {
        return new Block(LocalTime.parse("12:00"), LocalTime.parse("14:00"), negated);
    }

    /**
     * Returns a {@code block} that represents dinner, from 5pm to 8pm.
     * @param negated Whether this dinner block should be negated.
     * @return The dinner block (negated or otherwise).
     */
    public static Block dinner(boolean negated) {
        return new Block(LocalTime.parse("17:00"), LocalTime.parse("20:00"), negated);
    }

    /**
     * Returns a {@code block} that represents supper, from 9pm to 1am.
     * @param negated Whether this supper block should be negated.
     * @return The supper block (negated or otherwise).
     */
    public static Block supper(boolean negated) {
        return new Block(LocalTime.parse("21:00"), LocalTime.parse("01:00"), negated);
    }

    /**
     * Returns a {@code block} that represents brunch, from 10am to 1pm.
     * @param negated Whether this brunch block should be negated.
     * @return The brunch block (negated or otherwise).
     */
    public static Block brunch(boolean negated) {
        return new Block(LocalTime.parse("10:00"), LocalTime.parse("13:00"), negated);
    }

    /**
     * Checks if two LocalDateTimes form a period that is within this block.
     * @param start The start datetime.
     * @param end   The end datetime.
     * @return      True if the two LocalDateTimes fall within this block.
     */
    public boolean isWithinBlock(LocalDateTime start, LocalDateTime end) {
        if (nonBlock) {
            return true;
        }
        if (!tester.test(start)) {
            return false;
        }

        if (!tester.test(end)) {
            return false;
        }

        Duration durationToSecond;

        if (start.toLocalTime().isBefore(second)) {
            durationToSecond = Duration.between(start.toLocalTime(), second);
        } else {
            durationToSecond = Duration.between(second, start.toLocalTime());
        }
        if (durationToSecond.toSeconds() < Duration.between(start, end).toSeconds()) {
            return false;
        }

        return true;
    }

    /**
     * Gets the first {@code LocalTime} of this block.
     * @return The first LocalTime.
     */
    public LocalTime getFirst() {
        return first;
    }

    @Override
    public String toString() {
        return nonBlock ? "any time" : first + " to " + second;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Block)) {
            return false;
        }

        Block other = (Block) o;

        if (this.nonBlock && other.nonBlock) {
            return true;
        }
        return this.first.equals(other.first)
                && this.second.equals(other.second);
    }

}
