package softuni.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Validator;

public abstract class BaseServiceImpl {
    private ModelMapper modelMapper;
    private Validator validator;

    public BaseServiceImpl(ModelMapper modelMapper, Validator validator) {
        this.modelMapper = modelMapper;
        this.validator = validator;
    }

    protected ModelMapper getModelMapper() {
        return this.modelMapper;
    }

    protected Validator getValidator() {
        return this.validator;
    }
}
