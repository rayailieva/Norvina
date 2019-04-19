package norvina.web.controllers;

import norvina.domain.entities.Order;
import norvina.domain.entities.OrderStatus;
import norvina.domain.models.service.OrderServiceModel;
import norvina.domain.models.service.ProductServiceModel;
import norvina.domain.models.service.RoleServiceModel;
import norvina.domain.models.view.OrderViewModel;
import norvina.domain.models.view.UserViewModel;
import norvina.service.OrderService;
import norvina.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AdminController extends BaseController{

    private final OrderService orderService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public AdminController(OrderService orderService, UserService userService, ModelMapper modelMapper) {
        this.orderService = orderService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/orders/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView getAllOrders(ModelAndView modelAndView) {
        List<OrderViewModel> viewModels = orderService.findAllOrders()
                .stream()
                .map(o -> this.modelMapper.map(o, OrderViewModel.class))
                .collect(Collectors.toList());
        modelAndView.addObject("orders", viewModels);

        return view("order/orders-all", modelAndView);
    }

    @GetMapping("/orders/ship/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView shipOrder(@PathVariable String id, ModelAndView modelAndView) {

        OrderServiceModel orderServiceModel = this.orderService.findOrderById(id);

        OrderViewModel orderViewModel =
                this.modelMapper.map(orderServiceModel, OrderViewModel.class);


        orderViewModel.setOrderStatus(OrderStatus.Shipped);
        modelAndView.addObject("order",orderViewModel);

        return super.view("order/order-details", modelAndView);
    }

    @PostMapping("/orders/ship/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView shipOrderConfirm(@PathVariable String id, ModelAndView modelAndView) {

        OrderServiceModel orderServiceModel = this.orderService.findOrderById(id);
       orderService.editOrder(id, orderServiceModel);

        return super.view("home", modelAndView);
    }

    @GetMapping("/all-users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView allUsers(ModelAndView modelAndView){

        List<UserViewModel> users = this.userService
                .findAllUsers()
                .stream()
                .map(u -> {
                    UserViewModel user = this.modelMapper.map(u, UserViewModel.class);
                    user.setAuthorities(u.getAuthorities().stream().map(RoleServiceModel::getAuthority).collect(Collectors.toSet()));

                    return user;
                })
                .collect(Collectors.toList());

        modelAndView.addObject("users", users);
        return super.view("user/users-all", modelAndView);
    }

    @PostMapping("/set-user/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView setUser(@PathVariable String id) {
        this.userService.setUserRole(id, "user");

        return super.redirect("/all-users");
    }

    @PostMapping("/set-moderator/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView setModerator(@PathVariable String id) {
        this.userService.setUserRole(id, "moderator");

        return super.redirect("/all-users");
    }

    @PostMapping("/set-admin/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView setAdmin(@PathVariable String id) {
        this.userService.setUserRole(id, "admin");

        return super.redirect("/all-users");
    }

}
