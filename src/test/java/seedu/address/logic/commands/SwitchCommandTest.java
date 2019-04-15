package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.assertEventCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Tests for SwitchCommand.
 */
public class SwitchCommandTest {

    @Test
    public void switchTest() {
        SwitchCommand switcher = new SwitchCommand();
        assertCommandSuccess(switcher,
                new ModelManager(getTypicalAddressBook(), new UserPrefs()),
                new CommandHistory(),
                "Switched to events view.",
                new ModelManager(getTypicalAddressBook(), new UserPrefs()));
        assertEventCommandSuccess(switcher,
                new ModelManager(getTypicalAddressBook(), new UserPrefs()),
                new CommandHistory(),
                "Switched to persons view.",
                new ModelManager(getTypicalAddressBook(), new UserPrefs()));
    }
}
