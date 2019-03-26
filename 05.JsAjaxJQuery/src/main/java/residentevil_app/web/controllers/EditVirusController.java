package residentevil_app.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import residentevil_app.domain.models.binding.VirusEditModel;
import residentevil_app.domain.models.service.CapitalServiceModel;
import residentevil_app.domain.models.service.VirusServiceModel;
import residentevil_app.service.CapitalService;
import residentevil_app.service.VirusService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/edit-virus")
public class EditVirusController {
    private CapitalService capitalService;
    private VirusService virusService;
    private ModelMapper modelMapper;
    private VirusEditModel virusEditModel;
    private VirusEditModel originalEditModel;
    private List<CapitalServiceModel> capitalServiceModels;
    private VirusServiceModel originalServiceModel;

    @Autowired
    public EditVirusController(CapitalService capitalService, VirusService virusService, ModelMapper modelMapper) {
        this.capitalService = capitalService;
        this.virusService = virusService;
        this.modelMapper = modelMapper;
    }

    @ModelAttribute(value = "virusEditModel")
    public VirusEditModel virusEditModel() {
        return new VirusEditModel();
    }

    @GetMapping //not really a "slim" controller...
    public ModelAndView getEditVirusPage(@RequestParam(name = "id", required = true) String virusId,
                                   ModelAndView modelAndView) {
       this.originalServiceModel = this.virusService.findVirusById(virusId);
       this.virusEditModel = this.modelMapper.map
                (this.originalServiceModel, VirusEditModel.class); //TODO not sure if this is good practise
        Set<String> capitalIds = this.originalServiceModel.getCapitals()
                .stream()
                .map(c -> c.getId())
                .collect(Collectors.toSet());
        this.virusEditModel.setCapitalIds(capitalIds);

        if (this.originalEditModel == null) {
            this.originalEditModel = new VirusEditModel(this.virusEditModel);
        }

        this.capitalServiceModels = this.capitalService.getAllCapitalsSortedByNameAsc();
        modelAndView.addObject("allCapitals", this.capitalServiceModels);
        modelAndView.addObject("virusViewEditModel", this.virusEditModel);
        modelAndView.setViewName("edit_virus");
        return modelAndView;
    }

    @PostMapping
    public ModelAndView postEditVirusPage(@Valid @ModelAttribute("virusEditModel") VirusEditModel virusEditModel,
                                    BindingResult bindingResult,
                                    ModelAndView modelAndView) {
        if (bindingResult.hasErrors()) {
            if (virusEditModel.getCapitalIds() == null)
                virusEditModel.setCapitalIds(this.virusEditModel.getCapitalIds());

            this.virusEditModel = virusEditModel;
            modelAndView.addObject("allCapitals", this.capitalServiceModels);
            modelAndView.addObject("virusViewEditModel", this.virusEditModel);
            modelAndView.setViewName("edit_virus");
            return modelAndView;
        }
        modelAndView.setViewName("redirect:/");

        if (virusEditModel.equals(this.originalEditModel)) {
            return modelAndView;
        }

        VirusServiceModel editedVirusServiceModel =
                this.modelMapper.map(virusEditModel, VirusServiceModel.class);
        editedVirusServiceModel.setCapitals(this.capitalService.getAllCapitalsByIds(virusEditModel.getCapitalIds()));
        editedVirusServiceModel.setId(this.originalServiceModel.getId());
        editedVirusServiceModel.setReleasedOn(this.originalServiceModel.getReleasedOn());
        this.virusService.editVirus(editedVirusServiceModel);

        return modelAndView;
    }
}
