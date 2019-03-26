package residentevil_app.domain.models.view;

import residentevil_app.domain.enums.Magnitude;

import java.util.Date;

public class VirusShowAllViewModel {
    private String id;
    private String name;
    private Magnitude magnitude;
    private Date releasedOn;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Magnitude getMagnitude() {
        return this.magnitude;
    }

    public void setMagnitude(Magnitude magnitude) {
        this.magnitude = magnitude;
    }

    public Date getReleasedOn() {
        return this.releasedOn;
    }

    public void setReleasedOn(Date releasedOn) {
        this.releasedOn = releasedOn;
    }
}
