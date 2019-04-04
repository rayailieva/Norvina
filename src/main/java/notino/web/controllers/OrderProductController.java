package notino.web.controllers;

import notino.domain.models.binding.OrderProductBindingModel;
import notino.domain.models.service.OrderProductServiceModel;
import notino.domain.models.service.ProductServiceModel;
import notino.domain.models.view.OrderProductViewModel;
import notino.service.OrderProductService;
import notino.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class OrderProductController extends BaseController {

    private final OrderProductService orderProductService;
    private final ProductService productService;
    private final ModelMapper modelMapper;

    @Autowired
    public OrderProductController(OrderProductService orderProductService, ProductService productService, ModelMapper modelMapper) {
        this.orderProductService = orderProductService;
        this.productService = productService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/add-order-product/{id}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView addProduct(@PathVariable(name = "id") String id, ModelAndView modelAndView) {

        ProductServiceModel productServiceModel = this.productService.findProductById(id);
        OrderProductBindingModel orderProductBindingModel = new OrderProductBindingModel();
        orderProductBindingModel.setId(productServiceModel.getId());
       orderProductBindingModel.setName(productServiceModel.getName());
       orderProductBindingModel.setImageUrl(productServiceModel.getImageUrl());
       orderProductBindingModel.setPrice(productServiceModel.getPrice());

        modelAndView.addObject("orderProductBindingModel", orderProductBindingModel);

        return super.view("order/order-product", modelAndView);
    }

    @PostMapping("/add-order-product/{id}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView addProductConfirm(@PathVariable(name = "id") String id, @ModelAttribute("orderProductBindingModel") OrderProductBindingModel orderProductBindingModel) {

        OrderProductServiceModel orderProductServiceModel = this.modelMapper
                .map(this.productService.findProductById(id), OrderProductServiceModel.class);

        orderProductServiceModel.setQuantity(orderProductBindingModel.getQuantity());
        this.orderProductService.addOrderProduct(orderProductServiceModel);

        return super.redirect("order/all-order-products");
    }

}
