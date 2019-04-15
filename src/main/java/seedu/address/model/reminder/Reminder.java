package seedu.address.model.reminder;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import seedu.address.model.event.Event;

/**
 *  Represents a Reminder in the address book.
 *  Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Reminder {
    private Event event;
    private String message;
    private Interval interval;
    private boolean show;
    private boolean notShow;
    public Reminder(Event event, Interval interval, String message) {
        requireAllNonNull(event, interval, message);
        this.event = event;
        this.message = message;
        this.show = false;
        this.notShow = false;
        this.interval = interval;
    }

    public Reminder (Reminder source) {
        this(source.getEvent(), source.getInterval(), source.getMessage());
    }

    /**
     * Set the message of reminder, reminder message must be present and not NULL
     */
    public void setMessage(String message) {
        requireAllNonNull(message);
        this.message = message;
    }

    public void setShow(boolean show) {
        requireAllNonNull(show);
        this.show = show;
    }

    public void setNotShow(boolean notShow) {
        requireAllNonNull(notShow);
        this.notShow = notShow;
    }

    public boolean getShow() {
        return show;
    }

    public boolean getNotShow() {
        return notShow;
    }

    public String getMessage() {
        return message;
    }

    public Interval getInterval() {
        return interval;
    }

    public String getName() {
        return event.getName().toString();
    }
    //TODO: implement getEvent, which needs a get() in event class, event file
    public Event getEvent() {
        return event;
    }

    /**
     * Returns true if both reminders have same data filed.
     * This defines a stronger notion of equality between two reminders.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof seedu.address.model.reminder.Reminder)) {
            return false;
        }

        Reminder otherReminder = (Reminder) other;

        return otherReminder.getMessage().equals(getMessage())
                && otherReminder.getInterval().equals(getInterval())
                && otherReminder.getEvent().getName().equals(getEvent().getName())
                && otherReminder.getEvent().getDescription().equals(getEvent().getDescription())
                && otherReminder.getEvent().getVenue().equals(getEvent().getVenue())
                && otherReminder.getEvent().getLabel().equals(getEvent().getLabel())
                && otherReminder.getEvent().getStartDateTime().equals(getEvent().getStartDateTime())
                && otherReminder.getEvent().getEndDateTime().equals(getEvent().getEndDateTime());
    }

    /**
     * Returns true if both reminders of the same name have same message.
     * This defines a weaker notion of equality between two events.
     */
    public boolean isSameReminder(seedu.address.model.reminder.Reminder otherReminder) {
        if (otherReminder == this) {
            return true;
        }

        if (!(otherReminder instanceof seedu.address.model.reminder.Reminder)) {
            return false;
        }

        return getMessage().equals(getMessage())
                && otherReminder.getInterval().equals(getInterval())
                && otherReminder.getEvent().getName().equals(getEvent().getName())
                && otherReminder.getEvent().getDescription().equals(getEvent().getDescription())
                && otherReminder.getEvent().getVenue().equals(getEvent().getVenue())
                && otherReminder.getEvent().getLabel().equals(getEvent().getLabel())
                && otherReminder.getEvent().getStartDateTime().equals(getEvent().getStartDateTime())
                && otherReminder.getEvent().getEndDateTime().equals(getEvent().getEndDateTime());
    }


    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Message: ")
                .append(getMessage())
                .append(getName())
                .append(" Description: ")
                .append(getEvent().getDescription())
                .append(" Venue: ")
                .append(getEvent().getVenue())
                .append(" Label: ")
                .append(getEvent().getLabel())
                .append(" Start Date Time: ")
                .append(getEvent().getStartDateTime())
                .append(" End Date Time: ")
                .append(getEvent().getEndDateTime())
                .append(" Interval: ")
                .append(getInterval());
        return builder.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(event, interval, message);
    }

    /**
     *     compare current time with reminder time, to decide whether show this reminder or not.
     *         if reminder Time is equal to current time, return 0
     *         if reminder Time is earlier than current time, return -1
     *         if reminder Time is later than current time, return 1
     */
    public boolean compareWithCurrentTime() {
        Calendar fakeReminderTimeUpper = getFakeReminderTimeUpper(this.getInterval());
        Calendar startTime = changeStringIntoDateFormat(this.getEvent().getStartDateTime().toString());


        if (startTime.compareTo(fakeReminderTimeUpper) <= 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * delete te reminder
     * @return
     */
    public boolean deleteReminder() {
        Calendar deleteTimeUpper = getReminderDeleteTimeUpper(this.getInterval());
        Calendar startTime = changeStringIntoDateFormat(this.getEvent().getStartDateTime().toString());
        if (startTime.compareTo(deleteTimeUpper) <= 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * change the time in String format into Date format
     * @param date
     * @return
     */
    public Calendar changeStringIntoDateFormat(String date) {
        String stringDate = date;
        Date dateParse = new Date();
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            dateParse = sdf.parse(stringDate);
            cal.setTime(dateParse);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return cal;
    }


    /**
     * @param interval change interval time into milllis seconds.
     * @return
     */
    public int changeIntervalIntoMillis(Interval interval) {
        int time = Integer.parseInt(interval.getIntervalInt());
        if (interval.getUnit().equalsIgnoreCase("MIN")) {
            return time * 60 * 1000;
        } else if (interval.getUnit().equalsIgnoreCase("HOUR")) {
            return time * 60 * 60 * 1000;
        } else if (interval.getUnit().equalsIgnoreCase("YEAR")) {
            return time * 365 * 24 * 60 * 60 * 1000;
        } else {
            throw new RuntimeException("This is a unit exception. It should not happen");
        }
    }

    /**
     * Return the earliest reminder time
     */
    /*public Date getFakeReminderTimeLower(Interval interval) {
        int intervalMillis = changeIntervalIntoMillis(interval);
        Date temp = new Date(System.currentTimeMillis() + intervalMillis - 30 * 1000);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        temp = changeStringIntoDateFormat(sdf.format(temp));
        return temp;
    }*/

    /**
     * @return the latest reminder time
     */
    public Calendar getFakeReminderTimeUpper(Interval interval) {
        int number = Integer.parseInt(interval.getIntervalInt());
        Calendar cal = Calendar.getInstance();
        if (interval.getUnit().equalsIgnoreCase("MIN")) {
            cal.add(Calendar.MINUTE, number);
            return cal;
        } else if (interval.getUnit().equalsIgnoreCase("HOUR")) {
            cal.add(Calendar.HOUR, number);
            return cal;
        } else if (interval.getUnit().equalsIgnoreCase("YEAR")) {
            cal.add(Calendar.YEAR, number);
            return cal;
        } else {
            throw new RuntimeException("This is a unit exception. It should not happen");
        }
    }



    /*
    Return the latest delete time.
     Delete time is : 3 minutes after reminder, in which reminder is 2 minutes before start time.
     */
    public Calendar getReminderDeleteTimeUpper(Interval interval) {
        int number = Integer.parseInt(interval.getIntervalInt());
        Calendar cal = Calendar.getInstance();
        if (interval.getUnit().equalsIgnoreCase("MIN")) {
            cal.add(Calendar.MINUTE, number);
        } else if (interval.getUnit().equalsIgnoreCase("HOUR")) {
            cal.add(Calendar.HOUR, number);
        } else if (interval.getUnit().equalsIgnoreCase("YEAR")) {
            cal.add(Calendar.YEAR, number);
        } else {
            throw new RuntimeException("This is a unit exception. It should not happen");
        }
        cal.add(Calendar.MINUTE, -1);
        return cal;
    }
}
