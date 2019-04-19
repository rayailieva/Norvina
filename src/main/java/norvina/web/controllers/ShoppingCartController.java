package norvina.web.controllers;

import norvina.domain.models.service.OrderProductServiceModel;
import norvina.domain.models.service.OrderServiceModel;
import norvina.domain.models.service.ProductServiceModel;
import norvina.domain.models.view.OrderProductViewModel;
import norvina.domain.models.view.ProductViewModel;
import norvina.domain.models.view.ShoppingCartItem;
import norvina.service.OrderService;
import norvina.service.ProductService;
import norvina.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping("/shopping-cart")
public class ShoppingCartController extends BaseController{

    private final ProductService productService;
    private final UserService userService;
    private final OrderService orderService;
    private final ModelMapper modelMapper;

    @Autowired
    public ShoppingCartController(ProductService productService, UserService userService, OrderService orderService, ModelMapper modelMapper) {
        this.productService = productService;
        this.userService = userService;
        this.orderService = orderService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/add-product")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView addToCartConfirm(String id, int quantity, HttpSession session) {
        ProductViewModel product = this.modelMapper
                .map(this.productService.findProductById(id), ProductViewModel.class);

        OrderProductViewModel orderProductViewModel = new OrderProductViewModel();
        orderProductViewModel.setProductViewModel(product);
        orderProductViewModel.setPrice(product.getPrice());

        ShoppingCartItem cartItem = new ShoppingCartItem();
        cartItem.setProduct(orderProductViewModel);
        cartItem.setQuantity(quantity);

        var cart = this.retrieveCart(session);
        this.addItemToCart(cartItem, cart);

        return super.redirect("/shopping-cart/details");
    }

    @GetMapping("/details")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView cartDetails(ModelAndView modelAndView, HttpSession session) {

        var cart = this.retrieveCart(session);
        modelAndView.addObject("totalPrice", this.calcTotal(cart));

        return super.view("shoppingcart/shopping-cart-details", modelAndView);
    }

    @DeleteMapping("/remove-product")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView removeFromCartConfirm(String id, HttpSession session) {
        this.removeItemFromCart(id, this.retrieveCart(session));

        return super.redirect("/shopping-cart/details");
    }

    @PostMapping("/checkout")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ModelAndView checkoutConfirm(HttpSession session, Principal principal) {
        var cart = this.retrieveCart(session);

        OrderServiceModel orderServiceModel = this.prepareOrder(cart, principal.getName());
        this.orderService.createOrder(orderServiceModel, principal.getName());

        this.emptyCart(session);

        return super.redirect("/home");
    }

    private List<ShoppingCartItem> retrieveCart(HttpSession session) {
        this.initCart(session);

        return (List<ShoppingCartItem>) session.getAttribute("shopping-cart");
    }

    private void emptyCart(HttpSession session) {

        session.setAttribute("shopping-cart", new LinkedList<>());

    }

    private void initCart(HttpSession session) {

        if (session.getAttribute("shopping-cart") == null) {
            session.setAttribute("shopping-cart", new LinkedList<>());
        }
    }

    private void addItemToCart(ShoppingCartItem item, List<ShoppingCartItem> cart) {

        for (ShoppingCartItem shoppingCartItem : cart) {
            if (shoppingCartItem.getProduct().getProductViewModel().getId().equals(item.getProduct().getProductViewModel().getId())) {
                shoppingCartItem.setQuantity(shoppingCartItem.getQuantity() + item.getQuantity());
                return;
            }
        }

        cart.add(item);
    }

    private void removeItemFromCart(String id, List<ShoppingCartItem> cart) {
        cart.removeIf(ci -> ci.getProduct().getProductViewModel().getId().equals(id));
    }

    private BigDecimal calcTotal(List<ShoppingCartItem> cart) {
        BigDecimal result = new BigDecimal(0);

        for (ShoppingCartItem item : cart) {
            result = result.add(item.getProduct().getPrice().multiply(new BigDecimal(item.getQuantity())));
        }

        return result;
    }

    private OrderServiceModel prepareOrder(List<ShoppingCartItem> cart, String customer) {

        OrderServiceModel orderServiceModel = new OrderServiceModel();
        orderServiceModel.setCustomer(this.userService.findUserByUsername(customer));

        List<OrderProductServiceModel> products = new ArrayList<>();

        for (ShoppingCartItem item : cart) {
            OrderProductServiceModel orderProductServiceModel =
            this.modelMapper.map(item.getProduct(), OrderProductServiceModel.class);

            for (int i = 0; i < item.getQuantity(); i++) {
                ProductServiceModel productServiceModel = this.modelMapper.map(item.getProduct().getProductViewModel(), ProductServiceModel.class);
                orderProductServiceModel.setProductServiceModel(productServiceModel);
                products.add(orderProductServiceModel);
            }
        }
        orderServiceModel.setProducts(products);
        orderServiceModel.setTotalPrice(this.calcTotal(cart));

        return orderServiceModel;
    }
}
