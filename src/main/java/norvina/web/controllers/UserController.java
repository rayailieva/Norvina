package norvina.web.controllers;

import norvina.domain.models.binding.UserEditProfileBindingModel;
import norvina.domain.models.binding.UserRegisterBindingModel;
import norvina.domain.models.service.UserServiceModel;
import norvina.service.UserService;
import norvina.validation.UserEditValidator;
import norvina.validation.UserRegisterValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
public class UserController extends BaseController {

    private final UserService userService;
    private final UserRegisterValidator userRegisterValidator;
    private final UserEditValidator userEditValidator;
    private final ModelMapper modelMapper;

    @Autowired
    public UserController(UserService userService, UserRegisterValidator userRegisterValidator, UserEditValidator userEditValidator, ModelMapper modelMapper) {
        this.userService = userService;
        this.userRegisterValidator = userRegisterValidator;
        this.userEditValidator = userEditValidator;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/register")
    @PreAuthorize("isAnonymous()")
    public ModelAndView register(@ModelAttribute("userRegisterBindingModel") UserRegisterBindingModel userRegisterBindingModel,
                                 ModelAndView modelAndView) {
        modelAndView.setViewName("register");

        modelAndView.addObject("userRegisterBindingModel", userRegisterBindingModel);
        return super.view("register", modelAndView);
    }

    @PostMapping("/register")
    public ModelAndView registerConfirm(@ModelAttribute("userRegisterBindingModel") UserRegisterBindingModel userRegisterBindingModel,
                                        BindingResult bindingResult, ModelAndView modelAndView) {

        this.userRegisterValidator.validate(userRegisterBindingModel, bindingResult);

        if (!userRegisterBindingModel.getPassword().equals(userRegisterBindingModel.getConfirmPassword())) {
            throw new IllegalArgumentException(("Passwords don't match!"));
        }

        if (bindingResult.hasErrors()) {
            modelAndView.addObject("userRegisterBindingModel", userRegisterBindingModel);

            return super.view("register", modelAndView);
        }

        UserServiceModel userServiceModel =
                this.modelMapper.map(userRegisterBindingModel, UserServiceModel.class);

        this.userService.registerUser(userServiceModel);

        return super.redirect("/login");
    }

    @GetMapping("/login")
    @PreAuthorize("isAnonymous()")
    public ModelAndView login() {

        return super.view("login");
    }

    @GetMapping("/logout")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView logout(HttpSession session) {

        session.invalidate();

        return super.redirect("/");
    }

    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView userProfile(@ModelAttribute("userEditBindingModel") UserEditProfileBindingModel userEditBindingModel,
                                    Principal principal, ModelAndView modelAndView) {

        modelAndView.addObject("username", principal.getName());
        userEditBindingModel =
                this.modelMapper.map(this.userService.loadUserByUsername(principal.getName()), UserEditProfileBindingModel.class);

        modelAndView.addObject("userEditBindingModel", userEditBindingModel);
        return super.view("user/user-profile", modelAndView);

    }

    @GetMapping("/edit")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView userEditProfile(Principal principal, ModelAndView modelAndView) {

        UserEditProfileBindingModel userBindingModel =
                this.modelMapper.map(this.userService.findUserByUsername(principal.getName()), UserEditProfileBindingModel.class);

        modelAndView.addObject("userEditBindingModel", userBindingModel);
        return super.view("user/user-edit-profile", modelAndView);
    }


    @PatchMapping("/edit")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView userEditProfileConfirm(@ModelAttribute("userEditBindingModel") UserEditProfileBindingModel userEditBindingModel,
                                               BindingResult bindingResult, ModelAndView modelAndView) {

        this.userEditValidator.validate(userEditBindingModel, bindingResult);

        if (bindingResult.hasErrors()) {
            userEditBindingModel.setOldPassword(null);
            userEditBindingModel.setPassword(null);
            userEditBindingModel.setConfirmPassword(null);
            modelAndView.addObject("userEditBindingModel", userEditBindingModel);

            return super.view("user/user-edit-profile", modelAndView);
        }

        UserServiceModel userServiceModel = this.modelMapper.map(userEditBindingModel, UserServiceModel.class);
        this.userService.editUser(userServiceModel, userEditBindingModel.getOldPassword());

        return super.redirect("/profile");
    }
}



