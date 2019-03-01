package real_estate_agency_app.exceptions;

import java.util.List;

public class InvalidOfferException extends IllegalArgumentException {
    private List<String> errorMessages;

    public InvalidOfferException(List<String> errorMessages) {
        this.errorMessages = errorMessages;
    }

    public List<String> getErrorMessages() {
        return this.errorMessages;
    }
}
