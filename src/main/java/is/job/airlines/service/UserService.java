package is.job.airlines.service;

import is.job.airlines.model.service.UserRegisterServiceModel;

public interface UserService {
    void registerAndLoginUser(UserRegisterServiceModel model);

    boolean emailExists(String email);
}
