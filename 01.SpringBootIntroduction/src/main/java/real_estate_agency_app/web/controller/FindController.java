package real_estate_agency_app.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import real_estate_agency_app.domain.models.binding.OfferFindBindingModel;
import real_estate_agency_app.domain.models.service.OfferServiceModel;
import real_estate_agency_app.service.OfferService;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/find")
public class FindController {
    private OfferService offerService;

    @Autowired
    public FindController(OfferService offerService) {
        this.offerService = offerService;
    }

    @GetMapping
    public String getFindPage() {
        return "find.html";
    }

    @PostMapping
    public String postFindPage(@ModelAttribute OfferFindBindingModel bindingModel) {
        List<OfferServiceModel> apartmentTypeOffers =
                this.offerService.getAllByApartmentType(bindingModel.getFamilyApartmentType());

        OfferServiceModel offer = apartmentTypeOffers.stream()
                .filter(o -> bindingModel.getFamilyBudget().compareTo(this.calculateBudgetNeeded(o)) >= 0)
                .findFirst()
                .orElse(null);

        if (offer == null) {
            return "redirect:/find";
        } else {
            this.offerService.removeOffer(offer.getId());
            return "redirect:/";
        }
    }

    /*
    - The family budget needs to be enough to take the current apartment:
    rent + commission in percent of the current rental of the apartment.
     */
    private BigDecimal calculateBudgetNeeded(OfferServiceModel serviceModel) {
        return serviceModel.getApartmentRent().add(
                        serviceModel.getApartmentRent().multiply(
                                serviceModel.getAgencyCommission().multiply(
                                        new BigDecimal("0.01"))));
    }
}
