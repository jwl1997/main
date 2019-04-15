package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.DateTime;
import seedu.address.model.event.Description;
import seedu.address.model.event.Event;
import seedu.address.model.event.Label;
import seedu.address.model.event.Name;
import seedu.address.model.event.Venue;
import seedu.address.model.reminder.Interval;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.reminder.Unit;


/**
 * Jackson-friendly version of {@link Reminder}.
 */
public class JsonAdaptedReminder {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Event's %s field is missing!";

    private final String nameE;
    private final String descriptionE;
    private final String venueE;
    private final String startTimeE;
    private final String endTimeE;
    private final String labelE;
    //private final Event event;
    private final String message;
    private final String remindTime;
    private final String unit;
    private final boolean show;
    private final boolean notShow;

    /**
     * Constructs a {@code JsonAdaptedReminder} with the given reminder details.
     */
    @JsonCreator
    public JsonAdaptedReminder(@JsonProperty("nameE") String nameE,
                            @JsonProperty("phoneE") String descriptionE,
                            @JsonProperty("emailE") String venueE,
                            @JsonProperty("addressE") String startTimeE,
                            @JsonProperty("photoE") String endTimeE,
                            @JsonProperty("taggedE") String labelE,
                            @JsonProperty("message") String message,
                            @JsonProperty("show") boolean show, @JsonProperty("notShow") boolean notShow,
                            @JsonProperty("remindTime") String interval, @JsonProperty("unit") String unit) {
        this.nameE = nameE;
        this.descriptionE = descriptionE;
        this.venueE = venueE;
        this.startTimeE = startTimeE;
        this.endTimeE = endTimeE;
        this.labelE = labelE;
        this.message = message;
        this.remindTime = interval;
        this.unit = unit;
        this.show = show;
        this.notShow = notShow;
    }


    /**
     * Converts a given {@code Reminder} into this class for Jackson use.
     */
    public JsonAdaptedReminder(Reminder reminder) {
        nameE = reminder.getEvent().getName().fullName;
        descriptionE = reminder.getEvent().getDescription().value;
        venueE = reminder.getEvent().getVenue().value;
        startTimeE = reminder.getEvent().getStartDateTime().value;
        endTimeE = reminder.getEvent().getEndDateTime().value;
        labelE = reminder.getEvent().getLabel().getLabelName();
        message = reminder.getMessage();
        remindTime = reminder.getInterval().getIntervalInt();
        show = reminder.getShow();
        unit = reminder.getInterval().getUnit();
        notShow = reminder.getNotShow();
    }


    /**
     * Converts this Jackson-friendly adapted reminder object into the model's {@code Reminder} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted reminder.
     */
    public Reminder toModelType() throws IllegalValueException {

        if (nameE == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(nameE)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelNameE = new Name(nameE);

        if (descriptionE == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Description.class.getSimpleName()));
        }
        if (!Description.isValidDescription(descriptionE)) {
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
        }
        final Description modelDescription = new Description(descriptionE);

        if (venueE == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Venue.class.getSimpleName()));
        }
        if (!Venue.isValidVenue(venueE)) {
            throw new IllegalValueException(Venue.MESSAGE_CONSTRAINTS);
        }
        final Venue modelVenue = new Venue(venueE);

        if (startTimeE == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    DateTime.class.getSimpleName()));
        }
        if (!DateTime.isValidDateTime(startTimeE)) {
            throw new IllegalValueException(DateTime.MESSAGE_CONSTRAINTS);
        }
        final DateTime modelStartTime = new DateTime(startTimeE);

        if (endTimeE == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    DateTime.class.getSimpleName()));
        }
        if (!DateTime.isValidDateTime(endTimeE)) {
            throw new IllegalValueException(DateTime.MESSAGE_CONSTRAINTS);
        }
        final DateTime modelEndTime = new DateTime(endTimeE);

        if (labelE == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Label.class.getSimpleName()));
        }
        if (!Label.isValidLabelName(labelE)) {
            throw new IllegalValueException(Label.MESSAGE_CONSTRAINTS);
        }
        final Label modelLabel = new Label(labelE);

        if (unit == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Unit.class.getSimpleName()));
        }
        if (!Unit.isValidUnit(unit)) {
            throw new IllegalValueException(Unit.MESSAGE_CONSTRAINTS);
        }
        final Unit modelunit = new Unit(unit);

        //to do in future: add the reminder time
        Event toAdd = new Event(modelNameE, modelDescription, modelVenue, modelStartTime, modelEndTime, modelLabel);
        Interval toAddInterval = new Interval(remindTime, unit);
        return new Reminder(toAdd, toAddInterval, "Reminder: You have an Event!");
    }
}
