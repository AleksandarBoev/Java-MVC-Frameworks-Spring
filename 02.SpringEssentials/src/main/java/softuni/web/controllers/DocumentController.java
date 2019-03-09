package softuni.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import softuni.domain.models.binding.DocumentCreateBindingModel;
import softuni.domain.models.service.DocumentServiceModel;
import softuni.domain.models.view.DocumentDetailsView;
import softuni.domain.models.view.DocumentPrintView;
import softuni.service.DocumentService;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/document")
public class DocumentController extends BaseController {
    private DocumentService documentService;
    private ModelMapper modelMapper;

    @Autowired
    public DocumentController(HttpSession httpSession, DocumentService documentService, ModelMapper modelMapper) {
        super(httpSession);
        this.documentService = documentService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/schedule")
    public String getSchedulePage() {
        if (!super.isLoggedIn())
            return "redirect:/";

        return "schedule";
    }

    @PostMapping("/schedule")
    public String postSchedulePage(@ModelAttribute DocumentCreateBindingModel bindingModel,
                                   RedirectAttributes redirectAttributes) {
        DocumentServiceModel serviceModel =
                this.modelMapper.map(bindingModel, DocumentServiceModel.class);
        this.documentService.register(serviceModel);
        redirectAttributes.addAttribute("id", serviceModel.getId());
        return "redirect:/document/details";
    }

    @GetMapping("/details")
    public ModelAndView getDetailsPage(ModelAndView modelAndView,
                                       @RequestParam("id") String id) {
        if (!super.isLoggedIn()) {
            modelAndView.setViewName("redirect:/");
            return modelAndView;
        }

        DocumentServiceModel serviceModel = this.documentService.findById(id);
        DocumentDetailsView documentDetailsView =
                this.modelMapper.map(serviceModel, DocumentDetailsView.class);
        documentDetailsView.setId("Document - " + serviceModel.getId());

        modelAndView.setViewName("details");
        modelAndView.addObject(documentDetailsView);
        return modelAndView;
    }

    @GetMapping("/print")
    public ModelAndView getPrintPage(ModelAndView modelAndView,
                                     @RequestParam("id") String id) {
        if (!super.isLoggedIn()) {
            modelAndView.setViewName("redirect:/");
            return modelAndView;
        }

        modelAndView.setViewName("print");
        DocumentServiceModel documentServiceModel = this.documentService.findById(id);
        DocumentPrintView documentPrintView =
                this.modelMapper.map(documentServiceModel, DocumentPrintView.class);

        modelAndView.addObject("documentPrintView", documentPrintView);
        return modelAndView;
    }


    @PostMapping("/print")
    public String postPrintPage(@RequestParam("id") String id) {
        this.documentService.deleteById(id);
        return "redirect:/home";
    }
}
