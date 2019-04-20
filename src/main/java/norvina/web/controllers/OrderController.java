package norvina.web.controllers;

import norvina.domain.models.view.OrderViewModel;
import norvina.service.OrderService;
import norvina.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/orders")
public class OrderController extends BaseController{

    private final OrderService orderService;
    private final ModelMapper modelMapper;

    public OrderController(OrderService orderService, ModelMapper modelMapper) {
        this.orderService = orderService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/my")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView getMyOrders(ModelAndView modelAndView, Principal principal) {

        List<OrderViewModel> viewModels = orderService.findOrdersByCustomer(principal.getName())
                .stream()
                .map(o -> this.modelMapper.map(o, OrderViewModel.class))
                .collect(Collectors.toList());

        modelAndView.addObject("orders", viewModels);
        return view("order/orders-all", modelAndView);
    }

    @GetMapping("/my/details/{id}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView myOrderDetails(@PathVariable String id, ModelAndView modelAndView) {

        OrderViewModel orderViewModel =
                this.modelMapper.map(this.orderService.findOrderById(id), OrderViewModel.class);

        modelAndView.addObject("order",orderViewModel);
        return super.view("order/order-details", modelAndView);
    }
}
