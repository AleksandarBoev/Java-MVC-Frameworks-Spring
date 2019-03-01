package real_estate_agency_app.service;

import real_estate_agency_app.domain.models.service.OfferServiceModel;

import java.util.List;

public interface OfferService {
    OfferServiceModel registerOffer(OfferServiceModel offerServiceModel);

    List<OfferServiceModel> getAll();

    void removeOffer(String id);

    List<OfferServiceModel> getAllByApartmentType(String apartmentType);
}
