package seedu.address.model.reminder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static seedu.address.testutil.TypicalEvents.EVENT1;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


public class ReminderListTest {
    private static Interval interval1 = new Interval("2", "min");

    private static final Reminder reminder = new Reminder(EVENT1, interval1, "Reminder message");

    @Rule
    public ExpectedException thrown = ExpectedException.none();


    @Test
    public void add_hasDuplicate_checkCorrectness() throws Exception {
        thrown.expect(DuplicateReminderException.class);

        ReminderList list = new ReminderList();
        list.add(reminder);
        list.add(reminder);
    }

    @Test
    public void equals_checkCorrectness() {
        ReminderList list1 = new ReminderList();
        ReminderList list2 = new ReminderList();

        assertEquals(list1, list1);
        assertEquals(list1, list2);
        assertNotEquals(list1, null);
        assertNotEquals(list1, 1);
    }
}


