package demo.rolemanagement.configurations;

import demo.rolemanagement.domain.models.binding.UserBindingModel;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ModelAttribute;

@Configuration
public class ModelAttributeConfiguration {
    @ModelAttribute("userBindingModel")
    public UserBindingModel userBindingModel() { //needed
        return new UserBindingModel();
    }
}
