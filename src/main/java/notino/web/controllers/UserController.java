package notino.web.controllers;

import notino.domain.models.binding.UserLoginBindingModel;
import notino.domain.models.binding.UserRegisterBindingModel;
import notino.domain.models.service.UserServiceModel;
import notino.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/register")
    @PreAuthorize("isAnonymous()")
    public ModelAndView register(ModelAndView modelAndView){

        modelAndView.setViewName("register");

        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView registerConfirm(@ModelAttribute UserRegisterBindingModel model,
                                        ModelAndView modelAndView){
        if(!model.getPassword().equals(model.getConfirmPassword())){
            throw new IllegalArgumentException(("Passwords don't match!"));
        }

        this.userService.registerUser(this.modelMapper.map(model, UserServiceModel.class));

        modelAndView.setViewName("redirect:/login");
        return modelAndView;
    }

    @GetMapping("/login")
    @PreAuthorize("isAnonymous()")
    public ModelAndView login(ModelAndView modelAndView){

        modelAndView.setViewName("login");
        return modelAndView;
    }

    @GetMapping("/logout")
    public ModelAndView logout(ModelAndView modelAndView, HttpSession session){

        session.invalidate();

        modelAndView.setViewName("redirect:/");

        return modelAndView;
    }
}
