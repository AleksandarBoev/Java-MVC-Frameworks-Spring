package real_estate_agency_app.domain.models.binding;

import java.math.BigDecimal;

public class OfferFindBindingModel {
    private BigDecimal familyBudget;
    private String familyApartmentType;
    private String familyName;

    public BigDecimal getFamilyBudget() {
        return this.familyBudget;
    }

    public void setFamilyBudget(BigDecimal familyBudget) {
        this.familyBudget = familyBudget;
    }

    public String getFamilyApartmentType() {
        return this.familyApartmentType;
    }

    public void setFamilyApartmentType(String familyApartmentType) {
        this.familyApartmentType = familyApartmentType;
    }

    public String getFamilyName() {
        return this.familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }
}
