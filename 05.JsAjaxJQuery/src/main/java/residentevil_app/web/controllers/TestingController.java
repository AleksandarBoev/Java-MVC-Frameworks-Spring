package residentevil_app.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import residentevil_app.domain.models.view.CapitalAllViewModel;
import residentevil_app.service.CapitalService;
import residentevil_app.service.VirusService;

import java.util.List;

@Controller
@RequestMapping("/testing")
public class TestingController {
    private VirusService virusService;
    private CapitalService capitalService;

    @Autowired
    public TestingController(VirusService virusService, CapitalService capitalService) {
        this.virusService = virusService;
        this.capitalService = capitalService;
    }

    @GetMapping
    public String getTestingPage() {
        return "just_testing";
    }

    @GetMapping(value = "/fetch-capitals", produces = "application/json")
    @ResponseBody
    public Object getCapitals() {
        return this.capitalService.getCapitalViewModelsSortedByNameAsc();
    }

    @GetMapping(value = "/fetch-viruses", produces = "application/json")
    @ResponseBody
    public Object getViruses() {
        return this.virusService.allVirusesOrderedByDateAsc();
    }
}
