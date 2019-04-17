package norvina.validation.user;

import norvina.domain.models.binding.UserRegisterBindingModel;
import norvina.repository.UserRepository;
import norvina.validation.Constants;
import norvina.validation.annotation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;

@Validator
public class UserRegisterValidator implements org.springframework.validation.Validator {

    private final UserRepository userRepository;

    @Autowired
    public UserRegisterValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return UserRegisterBindingModel.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserRegisterBindingModel userRegisterBindingModel = (UserRegisterBindingModel) o;

        if(userRegisterBindingModel.getUsername().length() < 3 ||
                userRegisterBindingModel.getUsername().length() > 15){
            errors.rejectValue("username", Constants.USERNAME_LENGTH, Constants.USERNAME_LENGTH);
        }

        if(this.userRepository.findByUsername(userRegisterBindingModel.getUsername()).isPresent()){
            errors.rejectValue("username", Constants.USERNAME_ALREADY_EXISTS, Constants.USERNAME_ALREADY_EXISTS);
        }

        if(userRegisterBindingModel.getFirstName().length() < 3 ){
            errors.rejectValue("firstName", Constants.FIRST_NAME_LENGTH, Constants.FIRST_NAME_LENGTH);
        }

        if(userRegisterBindingModel.getLastName().length() < 3 ){
            errors.rejectValue("lastName", Constants.LAST_NAME_LENGTH, Constants.LAST_NAME_LENGTH);
        }

        if(!userRegisterBindingModel.getEmail().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")){
            errors.rejectValue("email", Constants.EMAIL_VALUE, Constants.EMAIL_VALUE);
        }

        if(userRegisterBindingModel.getPassword().length() < 6){
            errors.rejectValue("password", Constants.PASSWORD_LENGTH, Constants.PASSWORD_LENGTH);
        }

        if(!userRegisterBindingModel.getPassword().equals(userRegisterBindingModel.getConfirmPassword())){
            errors.rejectValue("confirmPassword", Constants.PASSWORDS_DO_NOT_MATCH, Constants.PASSWORDS_DO_NOT_MATCH);
        }
    }
}
