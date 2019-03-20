package residentevil_app.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import residentevil_app.domain.models.binding.VirusBindingModel;
import residentevil_app.domain.models.service.CapitalServiceModel;
import residentevil_app.domain.models.service.VirusServiceModel;
import residentevil_app.service.CapitalService;
import residentevil_app.service.VirusService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/add-virus")
public class AddVirusController {
    private CapitalService capitalService;
    private VirusService virusService;
    private ModelMapper modelMapper;

    @Autowired
    public AddVirusController(CapitalService capitalService, VirusService virusService, ModelMapper modelMapper) {
        this.capitalService = capitalService;
        this.virusService = virusService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ModelAndView getAddVirusPage(ModelAndView modelAndView, HttpSession session) {
        //TODO using session in this scenario is ok, but if there was a way to change the capitals in the
        //website, then this needs to be done in another way. Also if user does not make a get request
        //but does a post request before his first get request, then there would be problems
        if (session.getAttribute("allCapitals") == null)
            session.setAttribute("allCapitals", this.capitalService.getAllCapitalsSortedByNameAsc());

        modelAndView.addObject("allCapitals", session.getAttribute("allCapitals"));
        modelAndView.setViewName("add_virus");
        return modelAndView;
    }

    @ModelAttribute(value = "virusModelAttribute")
    public VirusBindingModel virusBindingModel() {
        return new VirusBindingModel();
    }

    @PostMapping
    public ModelAndView postAddVirusPage(@Valid @ModelAttribute("virusModelAttribute") VirusBindingModel virusBindingModel,
                                         BindingResult bindingResult, //needs to be before ModelAndView
                                         ModelAndView modelAndView) {
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("add_virus");
            modelAndView.addObject("allCapitals", this.capitalService.getAllCapitalsSortedByNameAsc());
            return modelAndView; //not redirecting, but returning.This is how the entered field values stay (i think)
        }

        VirusServiceModel virusServiceModel =
                modelMapper.map(virusBindingModel, VirusServiceModel.class);
        Set<CapitalServiceModel> capitalServiceModels = this.capitalService.getAllCapitalsByIds(virusBindingModel.getCapitalIds());
        virusServiceModel.setCapitals(capitalServiceModels);
        virusServiceModel = this.virusService.saveVirus(virusServiceModel);

        modelAndView.setViewName("redirect:/");

        return modelAndView;
    }

}
