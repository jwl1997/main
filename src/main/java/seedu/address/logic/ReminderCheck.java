package seedu.address.logic;

import java.util.List;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.Model;
import seedu.address.model.reminder.Reminder;


/**
 *Represent the thread that reminder checking use
 */
public class ReminderCheck implements Runnable {
    private Thread t;
    private Model model;
    private volatile boolean execute;
    private List<Reminder> lastShownReminder;
    //private ReminderList deleteReminderList = new ReminderList();
    private boolean deleteChange = false;

    ReminderCheck(Model model) {
        this.model = model;
        lastShownReminder = model.getFilteredReminderList();
    }

    /**
     * The inner logic of reminder check. Did a while loop to check which reminders should be shown, which reminders
     * should be deleted.
     */
    public void run() {
        //System.out.println("reminder testing thread is running now");
        this.execute = true;
        try {
            while (this.execute) {
                //System.out.println("one model passing");
                ObservableList<Reminder> deleteReminderList = FXCollections.observableArrayList();
                for (int i = 0; i < lastShownReminder.size(); i++) {
                    Reminder r = lastShownReminder.get(i);
                    //System.out.println("compare result"+r.compareWithCurrentTime());
                    if (!r.getShow() && r.compareWithCurrentTime()) {
                        model.setShow(r, true);
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                //System.out.println("should be running right after true");
                                model.addShownReminder(r);
                                model.commitAddressBook();
                            }
                        });
                    } else if (model.isReminderPassed(r)) {
                        //the reminder should end.
                        model.setNotShow(r, true);
                        deleteChange = true;
                        deleteReminderList.add(r);
                    }
                }
                if (deleteChange) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            for (int i = 0; i < deleteReminderList.size(); i++) {
                                model.deleteReminder(deleteReminderList.get(i));
                            }
                            model.commitAddressBook();
                        }
                    });
                    deleteChange = false;
                }
                /*if (model.getAddressBook().getReminderList().size() > 0) {
                    ReminderList temp = model.getAddressBook().getReminderListTest();
                    for (int i = 0; i < model.getAddressBook().getReminderList().size(); i++) {
                        Reminder tempR = temp.get(i);
                        System.out.println("name is" + tempR.getName());
                        System.out.println("time is" + tempR.getInterval());
                        System.out.println("show is" + tempR.getShow());
                        System.out.println("not show is" + tempR.getNotShow());
                        System.out.println("--------------------------------------------------------");
                    }
                }
                //model.commitAddressBook();

                System.out.println("-!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!-");*/
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Call this method when javafx terminate
     */
    public void stopExecuting() {
        this.execute = false;
    }
}
