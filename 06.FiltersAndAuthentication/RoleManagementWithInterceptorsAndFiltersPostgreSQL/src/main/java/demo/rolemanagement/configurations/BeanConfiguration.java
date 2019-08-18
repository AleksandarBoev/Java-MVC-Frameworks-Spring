package demo.rolemanagement.configurations;

import demo.rolemanagement.domain.models.binding.UserBindingModel;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ModelAttribute;

@Configuration
public class BeanConfiguration {
    @ModelAttribute("userBindingModel")
    public UserBindingModel userBindingModel() { //needed
        return new UserBindingModel();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
