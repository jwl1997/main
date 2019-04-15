package seedu.address.model.reminder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import static seedu.address.testutil.TypicalEvents.EVENT1;

import java.util.Objects;

import org.junit.BeforeClass;
import org.junit.Test;

import seedu.address.model.event.Event;

public class ReminderTest {
    private static Event event1;
    private static String message1;
    private static Interval interval1;
    private static boolean show1;
    private static boolean notShow1;
    @BeforeClass
    public static void constructor() {
        event1 = new Event(EVENT1.getName(), EVENT1.getDescription(), EVENT1.getVenue(), EVENT1.getStartDateTime(),
                EVENT1.getEndDateTime(), EVENT1.getLabel());
        message1 = "Reminder: You have an Event!";
        interval1 = new Interval("2", "min");
    }


    @Test
    public void createReminder_checkCorrectness() {
        Reminder reminder = new Reminder(event1, interval1, message1);
        assertNotNull(reminder);

        assertEquals(event1, reminder.getEvent());
        assertEquals(message1, reminder.getMessage());
    }

    @Test
    public void createReminder2_checkCorrectness() {
        Reminder reminder = new Reminder(event1, interval1, message1);
        assertNotNull(reminder);

        assertEquals(event1, reminder.getEvent());
        assertEquals(message1, reminder.getMessage());
        assertEquals(interval1, reminder.getInterval());
    }
    @Test
    public void copyReminder_testing() {
        Reminder reminder1 = new Reminder(event1, interval1, message1);
        Reminder reminder2 = new Reminder(reminder1);

        assertEquals(reminder1, reminder2);
    }

    /*@Test
    public void equal() {
        Reminder reminder1 = new Reminder((Event) EVENT1, message1);
        Reminder reminder2 = new Reminder((Event) EVENT1, message1);

        assertEquals(reminder1, reminder2);
    }*/

    @Test
    public void setMessage() {
        Reminder reminder = new Reminder(event1, interval1, message1);
        assertNotNull(reminder);

        reminder.setMessage("testing set message method");
        assertEquals("testing set message method", reminder.getMessage());
    }

    @Test
    public void toString_testing() {
        Reminder reminder = new Reminder(event1, interval1, message1);
        assertNotNull(reminder);
        StringBuilder builder = new StringBuilder();
        builder.append(" Message: ")
                .append(reminder.getMessage())
                .append(reminder.getName())
                .append(" Description: ")
                .append(reminder.getEvent().getDescription())
                .append(" Venue: ")
                .append(reminder.getEvent().getVenue())
                .append(" Label: ")
                .append(reminder.getEvent().getLabel())
                .append(" Start Date Time: ")
                .append(reminder.getEvent().getStartDateTime())
                .append(" End Date Time: ")
                .append(reminder.getEvent().getEndDateTime())
                .append(" Interval: ")
                .append(reminder.getInterval());
        String expected = builder.toString();
        assertEquals(expected, reminder.toString());
    }

    @Test
    public void hashCode_testing() {
        Reminder reminder = new Reminder(event1, interval1, message1);
        assertNotNull(reminder);

        assertEquals(Objects.hash(reminder.getEvent(),
                reminder.getInterval(), reminder.getMessage()), reminder.hashCode());
    }
}
