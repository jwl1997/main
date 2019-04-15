package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;
import seedu.address.model.reminder.Interval;
import seedu.address.model.reminder.Reminder;


/**
 * A utility class containing a list of {@code Event} objects to be used in tests.
 */
public class TypicalReminders {


    public static final Event EVENT1 = new EventBuilder().withName("event1")
            .withDescription("CS2103 lecture").withVenue("LT16")
            .withStartDateTime("2020-01-01 14:00:00")
            .withEndDateTime("2020-01-01 16:00:00")
            .withLabel("important").build();
    public static final Event EVENT2 = new EventBuilder().withName("event2")
            .withDescription("CS2103 tutorial").withVenue("com1 b1")
            .withStartDateTime("2020-01-21 14:00:00")
            .withEndDateTime("2020-01-21 15:00:00")
            .withLabel("urgent").build();


    public static final Reminder REMINDER1 = new Reminder(EVENT1, new Interval("2", "min"),
            "Reminder: You have an Event!");

    public static final Reminder REMINDER2 = new Reminder(EVENT1, new Interval("3", "min"),
            "Reminder: You have an Event!");
    public static final Reminder REMINDER3 = new Reminder(EVENT2, new Interval("2", "min"),
            "Reminder: You have an Event!");

    private TypicalReminders() {
    } // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Event event : TypicalEvents.getTypicalEvents()) {
            ab.addEvent(event);
        }
        for (Person person : TypicalPersons.getTypicalPersons()) {
            ab.addPerson(person);
        }
        for (Reminder reminder : getTypicalReminders()) {
            ab.addReminder(reminder);
        }



        return ab;
    }

    public static List<Reminder> getTypicalReminders() {
        return new ArrayList<>(Arrays.asList(REMINDER1, REMINDER2, REMINDER3));
    }
}

