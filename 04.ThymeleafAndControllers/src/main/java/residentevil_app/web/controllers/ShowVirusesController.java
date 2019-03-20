package residentevil_app.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import residentevil_app.domain.models.service.VirusServiceModel;
import residentevil_app.domain.models.view.VirusShowAllViewModel;
import residentevil_app.service.VirusService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/show-viruses")
public class ShowVirusesController {
    private VirusService virusService;
    private ModelMapper modelMapper;

    @Autowired
    public ShowVirusesController(VirusService virusService, ModelMapper modelMapper) {
        this.virusService = virusService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ModelAndView getShowVirusesPage(ModelAndView modelAndView) {
        List<VirusServiceModel> virusServiceModels = this.virusService.allVirusesOrderedByDateAsc();
        List<VirusShowAllViewModel> viewModels = virusServiceModels
                .stream()
                .map(v -> this.modelMapper.map(v, VirusShowAllViewModel.class))
                .collect(Collectors.toList());

        modelAndView.addObject("virusViewModels", viewModels);
        modelAndView.setViewName("show_viruses");
        return modelAndView;
    }
}
