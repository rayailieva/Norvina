package notino.web.controllers;

import notino.domain.models.service.OrderProductServiceModel;
import notino.domain.models.view.OrderProductViewModel;
import notino.service.OrderProductService;
import notino.service.OrderService;
import notino.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class OrderController extends BaseController{

    private final OrderService orderService;
    private final ProductService productService;
    private final OrderProductService orderProductService;
    private final ModelMapper modelMapper;

    public OrderController(OrderService orderService, ProductService productService, OrderProductService orderProductService, ModelMapper modelMapper) {
        this.orderService = orderService;
        this.productService = productService;
        this.orderProductService = orderProductService;
        this.modelMapper = modelMapper;
    }


    @GetMapping("/all-orders")
    public ModelAndView viewAllOrderProducts(ModelAndView modelAndView) {

        List<OrderProductServiceModel> orders =
                this.orderProductService.findAllOrderProducts()
                        .stream()
                        .map(p -> this.modelMapper.map(p, OrderProductServiceModel.class))
                        .collect(Collectors.toList());

        List<OrderProductViewModel> models = new LinkedList<>();
        for(OrderProductServiceModel orderProductServiceModel : orders){
            OrderProductViewModel model =
                    this.modelMapper.map(orderProductServiceModel, OrderProductViewModel.class);
            model.setName(orderProductServiceModel.getProduct().getName());
            model.setPrice(orderProductServiceModel.getProduct().getPrice());
            model.setImageUrl(orderProductServiceModel.getProduct().getImageUrl());

            models.add(model);
        }

        modelAndView.addObject("orders", models);
        return super.view("order/all-order-products", modelAndView);
    }

    @PostMapping("/all-orders")
    public ModelAndView viewAllOrderProductsConfirm(ModelAndView modelAndView) {

        List<OrderProductServiceModel> orders =
                this.orderProductService.findAllOrderProducts()
                        .stream()
                        .map(p -> this.modelMapper.map(p, OrderProductServiceModel.class))
                        .collect(Collectors.toList());


        this.orderService.addOrder(orders);

        return super.redirect("/home");
    }

}
