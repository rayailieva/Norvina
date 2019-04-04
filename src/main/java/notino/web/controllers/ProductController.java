package notino.web.controllers;

import notino.domain.models.binding.ProductCreateBindingModel;
import notino.domain.models.binding.ProductEditBindingModel;
import notino.domain.models.service.BrandServiceModel;
import notino.domain.models.service.ProductServiceModel;
import notino.domain.models.view.BrandViewModel;
import notino.domain.models.view.ProductViewModel;
import notino.service.BrandService;
import notino.service.CloudinaryService;
import notino.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/products")
public class ProductController extends BaseController {

    private final ProductService productService;
    private final BrandService brandService;
    private final CloudinaryService cloudinaryService;
    private final ModelMapper modelMapper;

    @Autowired
    public ProductController(ProductService productService, BrandService brandService, CloudinaryService cloudinaryService, ModelMapper modelMapper) {
        this.productService = productService;
        this.brandService = brandService;
        this.cloudinaryService = cloudinaryService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/add")
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
    public ModelAndView editProduct(@PathVariable(name = "id") String id, ModelAndView modelAndView) {

        ProductServiceModel productServiceModel = this.productService.findProductById(id);
        ProductEditBindingModel productEditBindingModel = this.modelMapper.map(productServiceModel, ProductEditBindingModel.class);

        modelAndView.addObject("productBindingModel", productEditBindingModel);
        return super.view("product/product-edit", modelAndView);

    }

    @PostMapping(value = "/edit/{id}")
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

    @GetMapping(value = "/details/{id}")
    public ModelAndView detailsProduct(@PathVariable(name = "id") String id, ModelAndView modelAndView) {

        ProductServiceModel productServiceModel = this.productService.findProductById(id);
        ProductViewModel productViewModel = this.modelMapper.map(productServiceModel, ProductViewModel.class);

        modelAndView.addObject("productBindingModel", productViewModel);
        return super.view("product/product-details", modelAndView);

    }


    @GetMapping(value = "/delete/{id}")
    public ModelAndView deleteProduct(@PathVariable(name = "id") String id, ModelAndView modelAndView) {

        ProductServiceModel productServiceModel = this.productService.findProductById(id);
        ProductViewModel productViewModel = this.modelMapper.map(productServiceModel, ProductViewModel.class);

        modelAndView.addObject("productBindingModel", productViewModel);
        return super.view("product/product-delete", modelAndView);

    }

    @PostMapping(value = "/delete/{id}")
    public ModelAndView deleteProductConfirm(@PathVariable(name = "id") String id) {

        if (!this.productService.deleteProduct(id)) {
            throw new IllegalArgumentException("Something went wrong!");
        }

        return super.redirect("/home");
    }


    @GetMapping("/all-products")
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
