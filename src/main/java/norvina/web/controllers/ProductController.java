package norvina.web.controllers;

import norvina.domain.models.binding.ProductCreateBindingModel;
import norvina.domain.models.binding.ProductEditBindingModel;
import norvina.domain.models.service.BrandServiceModel;
import norvina.domain.models.service.ProductServiceModel;
import norvina.domain.models.view.BrandViewModel;
import norvina.domain.models.view.ProductViewModel;
import norvina.service.BrandService;
import norvina.service.ProductService;
import norvina.validation.ProductCreateValidator;
import norvina.validation.ProductEditValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/products")
public class ProductController extends BaseController {

    private final ProductService productService;
    private final BrandService brandService;
    private final ProductCreateValidator productCreateValidator;
    private final ProductEditValidator productEditValidator;
    private final ModelMapper modelMapper;

    @Autowired
    public ProductController(ProductService productService, BrandService brandService, ProductCreateValidator productCreateValidator, ProductEditValidator productEditValidator, ModelMapper modelMapper) {
        this.productService = productService;
        this.brandService = brandService;
        this.productCreateValidator = productCreateValidator;
        this.productEditValidator = productEditValidator;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/add")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView addProduct(@ModelAttribute(name = "productBindingModel") ProductCreateBindingModel productBindingModel, ModelAndView modelAndView) {

        modelAndView.addObject("productBindingModel", productBindingModel);

        List<BrandViewModel> brands = this.brandService.findAllBrands()
                .stream()
                .map(p -> this.modelMapper.map(p, BrandViewModel.class))
                .collect(Collectors.toList());

        modelAndView.addObject("brands", brands);
        return super.view("product/product-add", modelAndView);

    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView addProductConfirm(@ModelAttribute(name = "productBindingModel") ProductCreateBindingModel productBindingModel,
                                          BindingResult bindingResult, ModelAndView modelAndView){
        this.productCreateValidator.validate(productBindingModel, bindingResult);

        ProductServiceModel productServiceModel =
                this.modelMapper.map(productBindingModel, ProductServiceModel.class);

        if (bindingResult.hasErrors()) {
            modelAndView.addObject("productBindingModel", productBindingModel);

            List<BrandViewModel> brands = this.brandService.findAllBrands()
                    .stream()
                    .map(p -> this.modelMapper.map(p, BrandViewModel.class))
                    .collect(Collectors.toList());

            modelAndView.addObject("brands", brands);
            return super.view("product/product-add", modelAndView);
        }

        BrandServiceModel brandServiceModel = this.brandService.findBrandByName(productBindingModel.getBrand());
        productServiceModel.setBrand(brandServiceModel);

        brandServiceModel.getProducts().add(productServiceModel);

        this.productService.addProduct(productServiceModel);
        return super.redirect("/home");
    }


    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView editProduct(@PathVariable(name = "id") String id, ModelAndView modelAndView) {

        ProductEditBindingModel productEditBindingModel =
                this.modelMapper.map(this.productService.findProductById(id), ProductEditBindingModel.class);

        modelAndView.addObject("productBindingModel", productEditBindingModel);
        return super.view("product/product-edit", modelAndView);

    }

    @PostMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView editProductConfirm(@PathVariable(name = "id") String id,
                                           @ModelAttribute("productBindingModel") ProductEditBindingModel productBindingModel,
                                           BindingResult bindingResult, ModelAndView modelAndView) {

       this.productEditValidator.validate(productBindingModel, bindingResult);

        if (bindingResult.hasErrors()) {
            modelAndView.addObject("productBindingModel", productBindingModel);
            return super.view("product/product-edit", modelAndView);
        }

        this.productService.editProduct(id, this.modelMapper.map(productBindingModel, ProductServiceModel.class));

        return super.redirect("/home");
    }

    @GetMapping("/details/{id}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView detailsProduct(@PathVariable String id, ModelAndView modelAndView) {
        ProductViewModel productViewModel =
                this.modelMapper.map(this.productService.findProductById(id), ProductViewModel.class);

        modelAndView.addObject("product", productViewModel);
        return super.view("product/product-details", modelAndView);
    }


    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView deleteProduct(@PathVariable(name = "id") String id, ModelAndView modelAndView) {

        ProductViewModel productViewModel =
                this.modelMapper.map(this.productService.findProductById(id), ProductViewModel.class);

        modelAndView.addObject("productBindingModel", productViewModel);
        return super.view("product/product-delete", modelAndView);

    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView deleteProductConfirm(@PathVariable(name = "id") String id) {

        this.productService.deleteProduct(id);

        return super.redirect("/home");
    }


    @GetMapping("/all-products")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView viewAllProducts(ModelAndView modelAndView) {

        List<ProductViewModel> products =
                this.productService.findAllProducts()
                        .stream()
                        .map(p -> this.modelMapper.map(p, ProductViewModel.class))
                        .collect(Collectors.toList());

        modelAndView.addObject("products", products);
        return super.view("product/products-all", modelAndView);
    }

}
