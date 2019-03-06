package softuni.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.domain.entities.User;
import softuni.domain.models.service.UserServiceModel;
import softuni.repository.UserRepository;

import javax.validation.Validator;

@Service
public class UserServiceImpl extends BaseServiceImpl implements UserService {
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(ModelMapper modelMapper, Validator validator, UserRepository userRepository) {
        super(modelMapper, validator);
        this.userRepository = userRepository;
    }

    @Override
    public boolean save(UserServiceModel userServiceModel) {
        //TODO validations
        User user = this.userRepository.save(super.getModelMapper().map(userServiceModel, User.class));
        userServiceModel.setId(user.getId());
        return true;
    }
}
