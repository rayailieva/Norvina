package norvina.web.controllers;

import norvina.domain.models.binding.ProductCreateBindingModel;
import norvina.domain.models.binding.ProductEditBindingModel;
import norvina.domain.models.service.BrandServiceModel;
import norvina.domain.models.service.ProductServiceModel;
import norvina.domain.models.view.BrandViewModel;
import norvina.domain.models.view.ProductViewModel;
import norvina.service.BrandService;
import norvina.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/products")
public class ProductController extends BaseController {

    private final ProductService productService;
    private final BrandService brandService;
    private final ModelMapper modelMapper;

    @Autowired
    public ProductController(ProductService productService, BrandService brandService, ModelMapper modelMapper) {
        this.productService = productService;
        this.brandService = brandService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView addProductConfirm(@Valid @ModelAttribute(name = "productBindingModel") ProductCreateBindingModel productBindingModel,
                                          BindingResult bindingResult, ModelAndView modelAndView){

        ProductServiceModel productServiceModel =
                this.modelMapper.map(productBindingModel, ProductServiceModel.class);

        if (bindingResult.hasErrors()) {
            modelAndView.addObject("productBindingModel", productBindingModel);
            return super.view("product/product-add", modelAndView);
        }


        BrandServiceModel brandServiceModel = this.brandService.findBrandByName(productBindingModel.getBrand());
        productServiceModel.setBrand(brandServiceModel);

        brandServiceModel.getProducts().add(productServiceModel);

        this.productService.addProduct(productServiceModel);

        return super.redirect("/home");
    }


    @GetMapping(value = "/edit/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView editProduct(@PathVariable(name = "id") String id, ModelAndView modelAndView) {

        ProductServiceModel productServiceModel = this.productService.findProductById(id);
        ProductEditBindingModel productEditBindingModel = this.modelMapper.map(productServiceModel, ProductEditBindingModel.class);

        modelAndView.addObject("productBindingModel", productEditBindingModel);
        return super.view("product/product-edit", modelAndView);

    }

    @PostMapping(value = "/edit/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView editProductConfirm(@PathVariable(name = "id") String id,
                                           @Valid @ModelAttribute("productBindingModel") ProductEditBindingModel productBindingModel,
                                           BindingResult bindingResult, ModelAndView modelAndView) {

        if (bindingResult.hasErrors()) {
            modelAndView.addObject("productBindingModel", productBindingModel);
            return super.view("product/product-edit", modelAndView);
        }

        this.productService.editProduct(id, this.modelMapper.map(productBindingModel, ProductServiceModel.class));

        return super.redirect("/products/details/" + id);
    }

    @GetMapping("/details/{id}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView detailsProduct(@PathVariable String id, ModelAndView modelAndView) {
        ProductViewModel productViewModel =  this.modelMapper.map(this.productService.findProductById(id), ProductViewModel.class);
        modelAndView.addObject("product", productViewModel);

        return super.view("product/product-details", modelAndView);
    }


    @GetMapping(value = "/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView deleteProduct(@PathVariable(name = "id") String id, ModelAndView modelAndView) {

        ProductServiceModel productServiceModel = this.productService.findProductById(id);
        ProductViewModel productViewModel = this.modelMapper.map(productServiceModel, ProductViewModel.class);

        modelAndView.addObject("productBindingModel", productViewModel);
        return super.view("product/product-delete", modelAndView);

    }

    @PostMapping(value = "/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView deleteProductConfirm(@PathVariable(name = "id") String id) {

        if (!this.productService.deleteProduct(id)) {
            throw new IllegalArgumentException("Something went wrong!");
        }

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
        return super.view("product/all-products", modelAndView);

    }

    @GetMapping("/fetch/{brand}")
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    public List<ProductViewModel> fetchByBrand(@PathVariable String brand) {
        if(brand.equals("all")) {
            return this.productService.findAllProducts()
                    .stream()
                    .map(product -> this.modelMapper.map(product, ProductViewModel.class))
                    .collect(Collectors.toList());
        }

        return this.productService.findAllByBrand(brand)
                .stream()
                .map(product -> this.modelMapper.map(product, ProductViewModel.class))
                .collect(Collectors.toList());
    }


}
