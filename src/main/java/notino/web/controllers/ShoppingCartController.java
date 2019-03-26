package notino.web.controllers;

import notino.service.ShoppingBasketService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ShoppingCartController extends BaseController{

    private final ShoppingBasketService shoppingBasketService;
    private final ModelMapper modelMapper;

    @Autowired
    public ShoppingCartController(ShoppingBasketService shoppingBasketService, ModelMapper modelMapper) {
        this.shoppingBasketService = shoppingBasketService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/shopping-basket")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView userShoppingBasket() {

        return super.view("shopping-basket");
    }
}
