package notino.service;

import notino.domain.models.service.UserServiceModel;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    boolean registerUser(UserServiceModel userServiceModel);
}
