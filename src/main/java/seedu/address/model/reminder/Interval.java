package seedu.address.model.reminder;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Objects;


/**
 *  Represents a Interval in the reminder class.
 *  Guarantees: details are present and not null, unit should be selected from min, hour and day.
 */
public class Interval {
    public static final String MESSAGE_CONSTRAINTS = "Interval should have both time and unit."
            + " Time should be a non-zero integer. Unit should be one of min|hour|year";
    public static final String INTERVAL_INT_VALIDATION_REGEX = "\\d+";
    public static final String UNIT_VALIDATION_REGEX = "(?i)min|year|hour";

    public final String intervalInt;
    public final Unit unit;

    public Interval(String intervalInt, String unit) {
        requireNonNull(unit);
        requireNonNull(intervalInt);
        checkArgument(isValidInterval(intervalInt, unit), MESSAGE_CONSTRAINTS);

        this.intervalInt = intervalInt;
        this.unit = new Unit(unit.toLowerCase());
    }

    public static boolean isValidInterval(String intervalIntTest, String unitTest) {
        return unitTest.matches(UNIT_VALIDATION_REGEX) && intervalIntTest.matches(INTERVAL_INT_VALIDATION_REGEX);
    }

    public String toString() {
        return intervalInt + unit.toString();
    }

    /**
     * Returns true if both intervals have same data filed.
     * This defines a stronger notion of equality between two reminders.
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Interval // instanceof handles nulls
                && getIntervalInt().equals(((Interval) other).getIntervalInt())
                && getUnit().equals(((Interval) other).getUnit())); // state check
    }

    public String getUnit() {
        return unit.toString();
    }

    public String getIntervalInt() {
        return intervalInt;
    }

    public int hashCode() {
        return Objects.hash(intervalInt, unit);
    }

}
