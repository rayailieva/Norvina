package norvina.service;

import norvina.domain.models.service.UserServiceModel;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    UserServiceModel registerUser(UserServiceModel userServiceModel);

    UserServiceModel editUser(UserServiceModel userServiceModel, String oldPassword);

    UserServiceModel extractUserByEmail(String email);

    UserServiceModel findUserByUsername(String name);

    UserServiceModel findUserById(String id);

    List<UserServiceModel> findAllUsers();

    void setUserRole(String id, String role);
}
