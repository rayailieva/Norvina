package notino.service;

import notino.domain.models.service.UserServiceModel;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    boolean registerUser(UserServiceModel userServiceModel);

    boolean editUser(UserServiceModel userServiceModel);

    UserServiceModel extractUserByEmail(String email);

    UserServiceModel findUserByUsername(String name);

    List<UserServiceModel> findAllUsers();
}
