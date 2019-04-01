package notino.web.controllers;

import notino.domain.models.service.ProductServiceModel;
import notino.domain.models.view.OrderViewModel;
import notino.domain.models.view.ProductViewModel;
import notino.service.OrderService;
import notino.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/orders")
public class OrderController extends BaseController{

    private final OrderService orderService;
    private final ProductService productService;
    private final ModelMapper modelMapper;

    public OrderController(OrderService orderService, ProductService productService, ModelMapper modelMapper) {
        this.orderService = orderService;
        this.productService = productService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/product/{id}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView orderProduct(@PathVariable String id, ModelAndView modelAndView) {
        ProductServiceModel serviceModel = this.productService.findProductById(id);
        ProductViewModel viewModel = this.modelMapper.map(serviceModel, ProductViewModel.class);
        modelAndView.addObject("product", viewModel);
        return view("order/order-product", modelAndView);
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView getAllOrders(ModelAndView modelAndView) {
        List<OrderViewModel> viewModels = orderService.findAllOrders()
                .stream()
                .map(o -> this.modelMapper.map(o, OrderViewModel.class))
                .collect(Collectors.toList());
        modelAndView.addObject("orders", viewModels);
        return view("order/list-orders", modelAndView);
    }

}
