package real_estate_agency_app.domain.models.binding;

import java.math.BigDecimal;

public class OfferRegisterBindingModel {
    private BigDecimal rentPrice;
    private String apartmentType;
    private BigDecimal commissionRate;

    public BigDecimal getRentPrice() {
        return this.rentPrice;
    }

    public void setRentPrice(BigDecimal rentPrice) {
        this.rentPrice = rentPrice;
    }

    public String getApartmentType() {
        return this.apartmentType;
    }

    public void setApartmentType(String apartmentType) {
        this.apartmentType = apartmentType;
    }

    public BigDecimal getCommissionRate() {
        return this.commissionRate;
    }

    public void setCommissionRate(BigDecimal commissionRate) {
        this.commissionRate = commissionRate;
    }
}
