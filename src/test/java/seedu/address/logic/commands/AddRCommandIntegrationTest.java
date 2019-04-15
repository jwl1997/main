package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertEventCommandSuccess;
import static seedu.address.testutil.TypicalEvents.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.reminder.Interval;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.reminder.Unit;
import seedu.address.testutil.TypicalReminders;

/**
 * Contains integration tests (interaction with the Model) for {@code AddECommand}.
 */
public class AddRCommandIntegrationTest {

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newReminder_success() {
        Reminder validReminder = new Reminder(TypicalReminders.EVENT1, new Interval("1", "hour"),
                "Reminder: You have an Event!");
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addReminder(validReminder);
        expectedModel.commitAddressBook();

        assertEventCommandSuccess(new AddRCommand(Index.fromZeroBased(1), new Interval("1", "min"),
                        new Unit("min")), model, commandHistory,
                String.format(AddRCommand.MESSAGE_SUCCESS, validReminder), expectedModel);

        //duplicate exception
        assertCommandFailure(new AddRCommand(Index.fromZeroBased(1), new Interval("1", "Min"),
                        new Unit("mIN")), model, commandHistory,
                AddRCommand.MESSAGE_DUPLICATE_REMINDER);

        //different unit
        assertEventCommandSuccess(new AddRCommand(Index.fromZeroBased(1), new Interval("5", "hour"),
                        new Unit("min")), model, commandHistory,
                String.format(AddRCommand.MESSAGE_SUCCESS, validReminder), expectedModel);

    }


}
