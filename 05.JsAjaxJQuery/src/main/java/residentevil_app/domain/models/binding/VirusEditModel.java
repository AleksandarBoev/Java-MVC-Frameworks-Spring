package residentevil_app.domain.models.binding;

import org.springframework.format.annotation.DateTimeFormat;
import residentevil_app.domain.enums.Magnitude;
import residentevil_app.domain.enums.Mutation;

import javax.validation.constraints.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class VirusEditModel {
    private static final int NAME_MIN_LENGTH = 3;
    private static final int NAME_MAX_LENGTH = 10;
    private static final String INVALID_NAME_LENGTH_MESSAGE =
            "Name should be between " + NAME_MIN_LENGTH + " and " + NAME_MAX_LENGTH + " symbols long!";
    private static final String INVALID_NAME_NULL_MESSAGE = "Name can't be null!";

    private static final int DESCRIPTION_MIN_LENGTH = 5;
    private static final int DESCRIPTION_MAX_LENGTH = 100;
    private static final String INVALID_DESCRIPTION_LENGTH_MESSAGE =
            "Description should be between " + DESCRIPTION_MIN_LENGTH + " and " + DESCRIPTION_MAX_LENGTH + " symbols long!";

    private static final int SIDE_EFFECTS_MAX_LENGTH = 50;
    private static final String INVALID_SIDE_EFFECTS_LENGTH_MESSAGE =
            "Side effects description should not be more than " + SIDE_EFFECTS_MAX_LENGTH + " symbols long!";

    private static final String INVALID_CREATOR_MESSAGE = "Creator should be either `Corp` or `corp`!";
    private static final String INVALID_MUTATION_NULL_MESSAGE = "Please select a mutation!";

    private static final String INVALID_TURN_OVER_MIN_MESSAGE = "Turn over rate can't be lower than 0.0!";
    private static final String INVALID_TURN_OVER_MAX_MESSAGE = "Turn over rate can't be higher than 100.0!";

    private static final int HOURS_UNTIL_TURN_MIN = 1;
    private static final int HOURS_UNTIL_TURN_MAX = 12;
    private static final String INVALID_HOURS_UNTIL_TURN_MIN_MESSAGE =
            "Hours until turn should not be fewer than " + HOURS_UNTIL_TURN_MIN;
    private static final String INVALID_HOURS_UNTIL_TURN_MAX_MESSAGE =
            "Hours until turn should not be more than " + HOURS_UNTIL_TURN_MAX;

    private static final String INVALID_MAGNITUDE_NULL_MESSAGE = "Magnitude can't be null!";
    private static final String INVALID_DATE_MESSAGE = "Date should be present or future!";
    private static final String INVALID_NUMBER_OF_CAPITALS_SELECTED_MESSAGE =
            "Number of capitals selected should be at least 1!";
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd";

    private String id;
    private String name;
    private String description;
    private String sideEffects;
    private String creator;
    private Boolean isDeadly;
    private Boolean isCurable;
    private Mutation mutation;
    private Double turnoverRate;
    private Integer hoursUntilTurn;
    private Magnitude magnitude;
    private Set<String> capitalIds;

    public VirusEditModel() {
    }

    public VirusEditModel(VirusEditModel anotherVirusEditModel) {
        this.id = anotherVirusEditModel.id;
        this.name = anotherVirusEditModel.name;
        this.description = anotherVirusEditModel.description;
        this.sideEffects = anotherVirusEditModel.sideEffects;
        this.creator = anotherVirusEditModel.creator;
        this.isDeadly = anotherVirusEditModel.isDeadly;
        this.isCurable = anotherVirusEditModel.isCurable;
        this.mutation = anotherVirusEditModel.mutation;
        this.turnoverRate = anotherVirusEditModel.turnoverRate;
        this.hoursUntilTurn = anotherVirusEditModel.hoursUntilTurn;
        this.magnitude = anotherVirusEditModel.magnitude;
        this.capitalIds = new HashSet<>();
        this.capitalIds.addAll(anotherVirusEditModel.capitalIds);
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Size(min = NAME_MIN_LENGTH, max = NAME_MAX_LENGTH, message = INVALID_NAME_LENGTH_MESSAGE)
    @NotNull(message = INVALID_NAME_NULL_MESSAGE)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Size(min = DESCRIPTION_MIN_LENGTH, max = DESCRIPTION_MAX_LENGTH, message = INVALID_DESCRIPTION_LENGTH_MESSAGE)
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Size(max = SIDE_EFFECTS_MAX_LENGTH, message = INVALID_SIDE_EFFECTS_LENGTH_MESSAGE)
    public String getSideEffects() {
        return this.sideEffects;
    }

    public void setSideEffects(String sideEffects) {
        this.sideEffects = sideEffects;
    }

    @Pattern(regexp = "^[Cc]orp$", message = INVALID_CREATOR_MESSAGE)
    public String getCreator() {
        return this.creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Boolean getDeadly() {
        return this.isDeadly;
    }

    public void setDeadly(Boolean deadly) {
        isDeadly = deadly;
    }

    public Boolean getCurable() {
        return this.isCurable;
    }

    public void setCurable(Boolean curable) {
        isCurable = curable;
    }

    @NotNull(message = INVALID_MUTATION_NULL_MESSAGE)
    public Mutation getMutation() {
        return this.mutation;
    }

    public void setMutation(Mutation mutation) {
        this.mutation = mutation;
    }

    @DecimalMin(value = "0.0", message = INVALID_TURN_OVER_MIN_MESSAGE)
    @DecimalMax(value = "100.0", message = INVALID_TURN_OVER_MAX_MESSAGE)
    public Double getTurnoverRate() {
        return this.turnoverRate;
    }

    public void setTurnoverRate(Double turnoverRate) {
        this.turnoverRate = turnoverRate;
    }

    @Min(value = HOURS_UNTIL_TURN_MIN, message = INVALID_HOURS_UNTIL_TURN_MIN_MESSAGE)
    @Max(value = HOURS_UNTIL_TURN_MAX, message = INVALID_HOURS_UNTIL_TURN_MAX_MESSAGE)
    public Integer getHoursUntilTurn() {
        return this.hoursUntilTurn;
    }

    public void setHoursUntilTurn(Integer hoursUntilTurn) {
        this.hoursUntilTurn = hoursUntilTurn;
    }

    @NotNull(message = INVALID_MAGNITUDE_NULL_MESSAGE)
    public Magnitude getMagnitude() {
        return this.magnitude;
    }

    public void setMagnitude(Magnitude magnitude) {
        this.magnitude = magnitude;
    }

    @NotEmpty(message = INVALID_NUMBER_OF_CAPITALS_SELECTED_MESSAGE)
    public Set<String> getCapitalIds() {
        return this.capitalIds;
    }

    public void setCapitalIds(Set<String> capitalIds) {
        this.capitalIds = capitalIds;
    }

    @Override
    public boolean equals(Object object) {
        // self check
        if (this == object)
            return true;
        // null check
        if (object == null)
            return false;
        // type check and cast
        if (getClass() != object.getClass())
            return false;
        VirusEditModel otherVirus = (VirusEditModel) object;
        // field comparison

        boolean areEqual = this.name.equals(otherVirus.name) &&
                this.isCurable.equals(otherVirus.isCurable) &&
                this.isDeadly.equals(otherVirus.isDeadly) &&
                this.id.equals(otherVirus.id) &&
                this.sideEffects.equals(otherVirus.sideEffects) &&
                this.turnoverRate.equals(otherVirus.turnoverRate) &&
                this.description.equals(otherVirus.description) &&
                this.hoursUntilTurn.equals(otherVirus.hoursUntilTurn) &&
                this.mutation.equals(otherVirus.mutation) &&
                this.magnitude.equals(otherVirus.magnitude) &&
                this.creator.equals(otherVirus.creator) &&
                this.capitalIds.size() == otherVirus.capitalIds.size();

        if (!areEqual)
            return false;

        for (String capitalId : this.capitalIds) {
            if (!otherVirus.capitalIds.contains(capitalId))
                return false;
        }

        return true;
    }
}
