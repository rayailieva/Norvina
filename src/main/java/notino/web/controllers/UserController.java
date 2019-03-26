package notino.web.controllers;

import notino.domain.models.binding.UserEditProfileBindingModel;
import notino.domain.models.binding.UserRegisterBindingModel;
import notino.domain.models.service.UserServiceModel;
import notino.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;

@Controller
public class UserController extends BaseController {

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder encoder;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.encoder = bCryptPasswordEncoder;
    }

    @GetMapping("/register")
    @PreAuthorize("isAnonymous()")
    public ModelAndView register() {

        return super.view("register");
    }

    @PostMapping("/register")
    public ModelAndView registerConfirm(@ModelAttribute("userRegisterBindingModel") UserRegisterBindingModel userRegisterBindingModel) {


        if (!userRegisterBindingModel.getPassword().equals(userRegisterBindingModel.getConfirmPassword())) {
            throw new IllegalArgumentException(("Passwords don't match!"));
        }

        UserServiceModel userServiceModel = this.modelMapper.map(userRegisterBindingModel, UserServiceModel.class);

        if (!this.userService.registerUser(userServiceModel)) {
            throw new IllegalArgumentException("Registering user " + userServiceModel.getUsername() + " failed.");
        }

        return super.redirect("/login");
    }

    @GetMapping("/login")
    @PreAuthorize("isAnonymous()")
    public ModelAndView login() {

        return super.view("login");
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpSession session) {

        session.invalidate();

        return super.redirect("/");
    }

    @GetMapping("/user-profile")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView userProfile(@ModelAttribute("userEditBindingModel") UserEditProfileBindingModel userEditBindingModel,
                                    Principal principal) {

        userEditBindingModel =
                this.modelMapper.map(this.userService.loadUserByUsername(principal.getName()), UserEditProfileBindingModel.class);

        return super.view("user-profile", "userEditBindingModel", userEditBindingModel);

    }


    @PostMapping("/user-edit-profile")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView userEditProfileConfirm(@Valid @ModelAttribute("userEditBindingModel") UserEditProfileBindingModel userEditBindingModel,
                                    BindingResult bindingResult) {
        UserServiceModel userServiceModel = this.userService.extractUserByEmail(userEditBindingModel.getEmail());

        if (bindingResult.hasErrors()) {
            return super.view("user-edit-profile", "userEditBindingModel", userEditBindingModel);
        }

        if (!userEditBindingModel.getNewPassword().equals("")) {
            userEditBindingModel.setPassword(userEditBindingModel.getNewPassword());
        }

        if (!this.userService.editUser(this.modelMapper.map(userEditBindingModel, UserServiceModel.class))) {
            throw new IllegalArgumentException("Editing user " + userServiceModel.getEmail() + " failed.");
        }

        return super.redirect("/user-profile");
    }

}
