package norvina.validation;

import norvina.domain.entities.User;
import norvina.domain.models.binding.UserEditProfileBindingModel;
import norvina.repository.UserRepository;
import norvina.validation.annotation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.Errors;

@Validator
public class UserEditValidator implements org.springframework.validation.Validator  {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserEditValidator(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return UserEditProfileBindingModel.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserEditProfileBindingModel userEditProfileBindingModel = (UserEditProfileBindingModel) o;

        User user = this.userRepository
                .findByUsername(userEditProfileBindingModel.getUsername())
                .orElse(null);

        if (!this.bCryptPasswordEncoder
                .matches(userEditProfileBindingModel.getOldPassword(), user.getPassword())) {
            errors.rejectValue(
                    "oldPassword",
                    Constants.WRONG_PASSWORD,
                    Constants.WRONG_PASSWORD
            );
        }

        if (userEditProfileBindingModel.getPassword() != null && !userEditProfileBindingModel.getPassword().equals(userEditProfileBindingModel.getConfirmPassword())) {
            errors.rejectValue(
                    "password",
                    Constants.PASSWORDS_DO_NOT_MATCH,
                    Constants.PASSWORDS_DO_NOT_MATCH
            );
        }

        if(userEditProfileBindingModel.getUsername().length() < 3 ||
                userEditProfileBindingModel.getUsername().length() > 15){
            errors.rejectValue("username", Constants.USERNAME_LENGTH, Constants.USERNAME_LENGTH);
        }

        if(this.userRepository.findByUsername(userEditProfileBindingModel.getUsername()).isPresent()){
            errors.rejectValue("username", Constants.USERNAME_ALREADY_EXISTS, Constants.USERNAME_ALREADY_EXISTS);
        }

        if(userEditProfileBindingModel.getFirstName().length() < 3 ){
            errors.rejectValue("firstName", Constants.FIRST_NAME_LENGTH, Constants.FIRST_NAME_LENGTH);
        }

        if(userEditProfileBindingModel.getLastName().length() < 3 ){
            errors.rejectValue("lastName", Constants.LAST_NAME_LENGTH, Constants.LAST_NAME_LENGTH);
        }

        if(!userEditProfileBindingModel.getEmail().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")){
            errors.rejectValue("email", Constants.EMAIL_VALUE, Constants.EMAIL_VALUE);
        }
    }
}
