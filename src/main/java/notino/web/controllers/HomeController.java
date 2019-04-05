package notino.web.controllers;

import notino.domain.entities.Category;
import notino.domain.models.view.BrandViewModel;
import notino.domain.models.view.ProductViewModel;
import notino.service.BrandService;
import notino.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController extends BaseController{

    private final BrandService brandService;
    private final ProductService productService;
    private final ModelMapper modelMapper;

    @Autowired
    public HomeController(BrandService brandService, ProductService productService, ModelMapper modelMapper) {
        this.brandService = brandService;
        this.productService = productService;
        this.modelMapper = modelMapper;
    }


    @GetMapping("/")
    @PreAuthorize("isAnonymous()")
    public ModelAndView index(){

        return super.view("index");
    }

    @GetMapping("/home")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView home(ModelAndView modelAndView){
        List<BrandViewModel> brands = this.brandService.findAllBrands()
                .stream()
                .map(b -> this.modelMapper.map(b, BrandViewModel.class))
                .collect(Collectors.toList());

        modelAndView.addObject("brands", brands);

        return super.view("home", modelAndView);
    }

    @GetMapping("/makeup-category")
    public ModelAndView makeupCategoryView(ModelAndView modelAndView){
        List<ProductViewModel> products = this.productService.findAllByCategory("Makeup")
                .stream()
                .map(b -> this.modelMapper.map(b, ProductViewModel.class))
                .collect(Collectors.toList());

        modelAndView.addObject("products", products);

        return super.view("category/makeup-category", modelAndView);
    }

}
