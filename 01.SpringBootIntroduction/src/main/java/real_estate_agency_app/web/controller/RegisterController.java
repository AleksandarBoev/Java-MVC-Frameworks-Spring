package real_estate_agency_app.web.controller;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import real_estate_agency_app.domain.models.binding.OfferRegisterBindingModel;
import real_estate_agency_app.domain.models.service.OfferServiceModel;
import real_estate_agency_app.exceptions.InvalidOfferException;
import real_estate_agency_app.service.OfferService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/reg")
public class RegisterController {
    private OfferService offerService;
    private TypeMap<OfferRegisterBindingModel, OfferServiceModel> bindingToServiceMapper;

    @Autowired
    public RegisterController(OfferService offerService, ModelMapper modelMapper) {
        this.offerService = offerService;
        this.bindingToServiceMapper = this.createBindingToServiceMapper(modelMapper);
    }

    @GetMapping
    public String getPage() {
        return "register.html";
    }

    @PostMapping
    public String registerStuff(@ModelAttribute OfferRegisterBindingModel bindingModel) {
        OfferServiceModel offerServiceModel = this.bindingToServiceMapper.map(bindingModel);

        try {
            this.offerService.registerOffer(offerServiceModel);
            return "redirect:/";
        } catch (InvalidOfferException ioe) {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                    .getRequestAttributes()).getRequest();

            HttpSession session = request.getSession();
            session.setAttribute("error-messages", ioe.getErrorMessages());
            return "redirect:/error-register-offer";
        }
    }


    //Or just change some field names so that the default mapping of the modelmapper works.
    private TypeMap<OfferRegisterBindingModel, OfferServiceModel> createBindingToServiceMapper(ModelMapper modelMapper) {
        TypeMap<OfferRegisterBindingModel, OfferServiceModel> result =
                modelMapper.createTypeMap(OfferRegisterBindingModel.class, OfferServiceModel.class);

        Converter<String, String> trimmer = context -> context.getSource().trim();

        result.addMappings(mapper -> {
            mapper.using(trimmer).map(OfferRegisterBindingModel::getApartmentType, OfferServiceModel::setApartmentType);
            mapper.map(OfferRegisterBindingModel::getCommissionRate, OfferServiceModel::setAgencyCommission);
            mapper.map(OfferRegisterBindingModel::getRentPrice, OfferServiceModel::setApartmentRent);
            mapper.skip(OfferServiceModel::setId);
        });
        result.validate(); //throws error if something goes wrong. Nice to have

        return result;
    }
}
