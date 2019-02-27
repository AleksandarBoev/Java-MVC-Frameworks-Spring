package real_estate_agency_app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import real_estate_agency_app.domain.models.binding.OfferRegisterBindingModel;

@Controller
@RequestMapping("/reg")
public class RegisterController {
    @GetMapping
    public String getPage() {
        return "register.html";
    }

    @PostMapping
    public void registerStuff(@ModelAttribute OfferRegisterBindingModel bindingModel) {
        int a = 5;
    }
}
