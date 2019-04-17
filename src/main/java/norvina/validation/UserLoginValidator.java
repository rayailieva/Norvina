package norvina.validation;

import norvina.domain.models.binding.UserLoginBindingModel;
import norvina.repository.UserRepository;
import norvina.validation.Constants;
import norvina.validation.annotation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;

@Validator
public class UserLoginValidator implements org.springframework.validation.Validator {

    private final UserRepository userRepository;

    @Autowired
    public UserLoginValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return UserLoginBindingModel.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserLoginBindingModel userLoginBindingModel = (UserLoginBindingModel) o;

        if(this.userRepository.findByUsername(userLoginBindingModel.getUsername()).isPresent()){
            errors.rejectValue("username", Constants.INVALID_USERNAME, Constants.INVALID_USERNAME);
        }

    }
}
