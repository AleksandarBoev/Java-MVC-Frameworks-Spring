package softuni.service;

import softuni.domain.models.service.UserServiceModel;

public interface UserService extends BaseService<UserServiceModel> {

    UserServiceModel loginUser(UserServiceModel userServiceModel);
}
