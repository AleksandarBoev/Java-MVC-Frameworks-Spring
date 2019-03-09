package softuni.service;

import org.apache.commons.codec.digest.DigestUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.domain.entities.User;
import softuni.domain.models.service.UserServiceModel;
import softuni.repository.UserRepository;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

@Service
public class UserServiceImpl extends BaseServiceImpl implements UserService {
    private static final String USER_NOT_FOUND_MESSAGE = "User with this username has NOT been found!";
    private static final String USER_WRONG_PASSWORD_MESSAGE = "Password is wrong!";

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(ModelMapper modelMapper, Validator validator, UserRepository userRepository) {
        super(modelMapper, validator);
        this.userRepository = userRepository;
    }

    @Override
    public UserServiceModel register(UserServiceModel userServiceModel) {
        Set<ConstraintViolation<UserServiceModel>> errors = super.getValidator().validate(userServiceModel);
        if (!errors.isEmpty()) {
            String errorMessages = errors.stream().map(e -> e.getMessage() + " | ").toString(); //not done very well
            throw new IllegalArgumentException(errorMessages);
        }

        userServiceModel.setPassword(DigestUtils.sha256Hex(userServiceModel.getPassword()));
        User user = this.userRepository.save(super.getModelMapper().map(userServiceModel, User.class));
        userServiceModel.setId(user.getId());
        return userServiceModel;
    }

    @Override
    public UserServiceModel loginUser(UserServiceModel userServiceModel) {
        UserServiceModel userFound = this.findUserByUsername(userServiceModel.getUsername());
        String hashedPassword = DigestUtils.sha256Hex(userServiceModel.getPassword());

        if (!hashedPassword.equals(userFound.getPassword()))
            throw new IllegalArgumentException(USER_WRONG_PASSWORD_MESSAGE);


        return userFound;
    }

    private UserServiceModel findUserByUsername(String username) {
        User user = this.userRepository.findUserByUsername(username);
        if (user == null)
            throw new IllegalArgumentException(USER_NOT_FOUND_MESSAGE);

        return super.getModelMapper().map(user, UserServiceModel.class);
    }
}
