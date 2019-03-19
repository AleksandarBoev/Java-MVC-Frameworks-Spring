package residentevil_app.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import residentevil_app.domain.models.binding.VirusBindingModel;

import javax.validation.Valid;

@Controller
@RequestMapping("/add-virus")
public class AddVirusController {
    @GetMapping
    public String getAddVirusPage() {
        return "add_virus";
    }

    @ModelAttribute(value = "virusModelAttribute")
    public VirusBindingModel virusBindingModel() {
        return new VirusBindingModel();
    }

    @PostMapping
    public String postAddVirusPage(@Valid @ModelAttribute("virusModelAttribute") VirusBindingModel virusModel,
                                   BindingResult bindingResult, //needs to be before ModelAndView
                                   ModelAndView modelAndView) { //TODO is this modelAndView needed?
        if (bindingResult.hasErrors())
            return "add_virus"; //not redirecting, but returning.This is how the entered field values stay (i think)

        return "redirect:/";
    }

}
