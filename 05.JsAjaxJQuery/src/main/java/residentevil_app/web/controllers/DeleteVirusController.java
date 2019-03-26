package residentevil_app.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import residentevil_app.service.VirusService;

@Controller
@RequestMapping("/delete-virus")
public class DeleteVirusController {
    private VirusService virusService;

    @Autowired
    public DeleteVirusController(VirusService virusService) {
        this.virusService = virusService;
    }

    @GetMapping
    public String deleteVirus(@RequestParam(name = "id") String virusId) {
        this.virusService.deleteVirus(virusId);
        return "redirect:/show-viruses";
    }
}
