package norvina.web.controllers;


import norvina.domain.models.view.ProductViewModel;
import norvina.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/category")
public class CategoryController extends BaseController{

    private final ProductService productService;
    private final ModelMapper modelMapper;

    @Autowired
    public CategoryController(ProductService productService, ModelMapper modelMapper) {
        this.productService = productService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/makeup")
    public ModelAndView makeup(ModelAndView modelAndView){
        List<ProductViewModel> products = this.productService.findAllByCategory("Makeup")
                .stream()
                .map(b -> this.modelMapper.map(b, ProductViewModel.class))
                .collect(Collectors.toList());

        modelAndView.addObject("products", products);

        return super.view("category/product-by-category", modelAndView);
    }

    @GetMapping("/hair")
    public ModelAndView hair(ModelAndView modelAndView){
        List<ProductViewModel> products = this.productService.findAllByCategory("Hair")
                .stream()
                .map(b -> this.modelMapper.map(b, ProductViewModel.class))
                .collect(Collectors.toList());

        modelAndView.addObject("products", products);

        return super.view("category/product-by-category", modelAndView);
    }

    @GetMapping("/sun")
    public ModelAndView sun(ModelAndView modelAndView){
        List<ProductViewModel> products = this.productService.findAllByCategory("Sun")
                .stream()
                .map(b -> this.modelMapper.map(b, ProductViewModel.class))
                .collect(Collectors.toList());

        modelAndView.addObject("products", products);

        return super.view("category/product-by-category", modelAndView);
    }

    @GetMapping("/skin")
    public ModelAndView skin(ModelAndView modelAndView){
        List<ProductViewModel> products = this.productService.findAllByCategory("Skincare")
                .stream()
                .map(b -> this.modelMapper.map(b, ProductViewModel.class))
                .collect(Collectors.toList());

        modelAndView.addObject("products", products);

        return super.view("category/product-by-category", modelAndView);
    }

    @GetMapping("/fragrances")
    public ModelAndView fragrances(ModelAndView modelAndView){
        List<ProductViewModel> products = this.productService.findAllByCategory("Fragrance")
                .stream()
                .map(b -> this.modelMapper.map(b, ProductViewModel.class))
                .collect(Collectors.toList());

        modelAndView.addObject("products", products);

        return super.view("category/product-by-category", modelAndView);
    }

    @GetMapping("/body")
    public ModelAndView body(ModelAndView modelAndView){
        List<ProductViewModel> products = this.productService.findAllByCategory("Body")
                .stream()
                .map(b -> this.modelMapper.map(b, ProductViewModel.class))
                .collect(Collectors.toList());

        modelAndView.addObject("products", products);

        return super.view("category/product-by-category", modelAndView);
    }
}
