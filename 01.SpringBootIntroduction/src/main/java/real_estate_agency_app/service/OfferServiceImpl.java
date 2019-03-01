package real_estate_agency_app.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import real_estate_agency_app.domain.entities.Offer;
import real_estate_agency_app.domain.models.service.OfferServiceModel;
import real_estate_agency_app.exceptions.InvalidOfferException;
import real_estate_agency_app.repository.OfferRepository;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OfferServiceImpl implements OfferService {
    private OfferRepository offerRepository;
    private ModelMapper modelMapper;
    private Validator validator;

    @Autowired
    public OfferServiceImpl(OfferRepository offerRepository, ModelMapper modelMapper, Validator validator) {
        this.offerRepository = offerRepository;
        this.modelMapper = modelMapper;
        this.validator = validator;
    }

    @Override
    public OfferServiceModel registerOffer(OfferServiceModel offerServiceModel) {
        Set<ConstraintViolation<OfferServiceModel>> violations =
                this.validator.validate(offerServiceModel);

        if (!violations.isEmpty()) {
            List<String> violationMessages = violations
                    .stream().map(v -> v.getMessage())
                    .collect(Collectors.toList());
            throw new InvalidOfferException(violationMessages);
        }

        Offer offer = this.modelMapper.map(offerServiceModel, Offer.class);

        this.offerRepository.save(offer);
        offerServiceModel.setId(offer.getId()); //after saving the offer, an id should be given to it.
        return offerServiceModel;
    }

    @Override
    public List<OfferServiceModel> getAll() {
        return this.offerRepository.findAll()
                .stream()
                .map(offer -> this.modelMapper.map(offer, OfferServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public void removeOffer(String id) {
        this.offerRepository.deleteById(id);
    }

    @Override
    public List<OfferServiceModel> getAllByApartmentType(String apartmentType) {
        return this.offerRepository.findAllByApartmentType(apartmentType)
                .stream()
                .map(o -> this.modelMapper.map(o, OfferServiceModel.class))
                .collect(Collectors.toList());
    }
}
