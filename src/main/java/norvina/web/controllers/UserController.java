package norvina.web.controllers;

import norvina.domain.models.binding.UserEditProfileBindingModel;
import norvina.domain.models.binding.UserRegisterBindingModel;
import norvina.domain.models.service.RoleServiceModel;
import norvina.domain.models.service.UserServiceModel;
import norvina.domain.models.view.UserViewModel;
import norvina.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class UserController extends BaseController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
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
    public ModelAndView registerConfirm(@Valid @ModelAttribute("userRegisterBindingModel") UserRegisterBindingModel userRegisterBindingModel,
                                        BindingResult bindingResult, ModelAndView modelAndView) {

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
    public ModelAndView login(ModelAndView modelAndView) {

        return super.view("login");
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpSession session) {

        session.invalidate();

        return super.redirect("/");
    }

    @GetMapping("/user-profile")
    public ModelAndView userProfile(@ModelAttribute("userEditBindingModel") UserEditProfileBindingModel userEditBindingModel,
                                    Principal principal, ModelAndView modelAndView) {

        modelAndView.addObject("username", principal.getName());
        userEditBindingModel =
                this.modelMapper.map(this.userService.loadUserByUsername(principal.getName()), UserEditProfileBindingModel.class);

        modelAndView.addObject("userEditBindingModel", userEditBindingModel);
        return super.view("user/user-profile", modelAndView);

    }

    @GetMapping("/user-edit-profile/{id}")
    public ModelAndView userEditProfile(@PathVariable("id") String id, ModelAndView modelAndView) {
        UserEditProfileBindingModel userBindingModel =
                this.modelMapper.map(this.userService.findUserById(id), UserEditProfileBindingModel.class);

        modelAndView.addObject("userEditBindingModel", userBindingModel);

        return super.view("user/user-edit-profile", modelAndView);
    }

    @PostMapping("/user-edit-profile/{id}")
    public ModelAndView userEditProfileConfirm(@ModelAttribute("userEditBindingModel") UserEditProfileBindingModel userEditBindingModel,
                                                BindingResult bindingResult, ModelAndView modelAndView) {

        if (bindingResult.hasErrors()) {
            modelAndView.addObject("userEditBindingModel", userEditBindingModel);
            return super.view("user/user-edit-profile", modelAndView);
        }

        if (!userEditBindingModel.getPassword().equals(userEditBindingModel.getConfirmPassword())) {
            return super.view("user/user-edit-profile");
        }

        this.userService.editUser(this.modelMapper.map(userEditBindingModel, UserServiceModel.class), userEditBindingModel.getOldPassword());

        return super.redirect("/user-profile");
    }
}
