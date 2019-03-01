package real_estate_agency_app.domain.models.service;

import javax.validation.constraints.*;
import java.math.BigDecimal;

public class OfferServiceModel {
    private static final String APARTMENT_RENT_NO_VALUE_ERROR = "You must enter apartment rent!";
    private static final String APARTMENT_RENT_NON_POSITIVE_ERROR = "Apartment rent must be a positive number!";

    private static final String APARTMENT_TYPE_NO_VALUE_ERROR = "You must enter apartment type!";

    private static final String COMMISSION_VALUE_MIN_ERROR = "Commission must be over 0.00!";
    private static final String COMMISSION_VALUE_MAX_ERROR = "Commission must NOT be over 100.00!";

    private String id;
    private BigDecimal apartmentRent;
    private String apartmentType;
    private BigDecimal agencyCommission;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Positive(message = APARTMENT_RENT_NON_POSITIVE_ERROR)//greater than 0
    @NotNull(message = APARTMENT_RENT_NO_VALUE_ERROR)
    public BigDecimal getApartmentRent() {
        return this.apartmentRent;
    }

    public void setApartmentRent(BigDecimal apartmentRent) {
        this.apartmentRent = apartmentRent;
    }

    //checks if it is null or empty. Should do a "trim" before setting this field so that empty spaces are not valid
    @NotEmpty(message = APARTMENT_TYPE_NO_VALUE_ERROR)
    public String getApartmentType() {
        return this.apartmentType;
    }

    public void setApartmentType(String apartmentType) {
        this.apartmentType = apartmentType;
    }

    @DecimalMin(value = "0.00", message = COMMISSION_VALUE_MIN_ERROR)
    @DecimalMax(value = "100.00", message = COMMISSION_VALUE_MAX_ERROR)
    @NotNull
    public BigDecimal getAgencyCommission() {
        return this.agencyCommission;
    }

    public void setAgencyCommission(BigDecimal agencyCommission) {
        this.agencyCommission = agencyCommission;
    }
}
