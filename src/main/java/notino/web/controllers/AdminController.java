package notino.web.controllers;

import notino.domain.models.view.UserViewModel;
import notino.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AdminController extends BaseController{

    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public AdminController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/all-users")
    public ModelAndView allUsers(ModelAndView modelAndView){

        List<UserViewModel> activeUsers =
                this.userService.findAllUsers()
                .stream()
                .map(u -> this.modelMapper.map(u, UserViewModel.class))
                .collect(Collectors.toList());

        modelAndView.addObject("activeUsers", activeUsers);
        return super.view("all-users", modelAndView);
    }
}
