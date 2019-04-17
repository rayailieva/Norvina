package norvina.web.controllers;

import norvina.domain.models.binding.BrandBindingModel;
import norvina.domain.models.service.BrandServiceModel;
import norvina.domain.models.service.ProductServiceModel;
import norvina.domain.models.view.BrandViewModel;
import norvina.service.BrandService;
import norvina.service.ProductService;
import norvina.validation.BrandValidator;
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
@RequestMapping("/brands")
public class BrandController extends BaseController {

    private final BrandService brandService;
    private final ProductService productService;
    private final BrandValidator validator;
    private final ModelMapper modelMapper;

    @Autowired
    public BrandController(BrandService brandService, ProductService productService, BrandValidator validator, ModelMapper modelMapper) {
        this.brandService = brandService;
        this.productService = productService;
        this.validator = validator;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/add")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView addBrand(@ModelAttribute(name = "bindingModel") BrandBindingModel brandBindingModel,
                                 BindingResult bindingResult, ModelAndView modelAndView) {

        modelAndView.addObject( "brandBindingModel", brandBindingModel);
        return super.view("brand/brand-add", modelAndView);
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView addBrandConfirm(@ModelAttribute BrandBindingModel brandBindingModel ,
                                        BindingResult bindingResult, ModelAndView modelAndView) {
        this.validator.validate(brandBindingModel, bindingResult);

        if (bindingResult.hasErrors()) {
            modelAndView.addObject("brandBindingModel", brandBindingModel);
            return super.view("brand/brand-add", modelAndView);
        }

        BrandServiceModel brandServiceModel =
                this.modelMapper.map(brandBindingModel, BrandServiceModel.class);
        this.brandService.addBrand(brandServiceModel);


        return super.redirect("/brands/all-brands");
    }


    @GetMapping(value = "/edit/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView editBrand(@PathVariable(name = "id") String id, ModelAndView modelAndView) {

        BrandServiceModel brandServiceModel = this.brandService.findBrandById(id);
        BrandBindingModel brandBindingModel = this.modelMapper.map(brandServiceModel, BrandBindingModel.class);

        modelAndView.addObject("brandBindingModel", brandBindingModel);
        return super.view("brand/brand-edit", modelAndView);
    }

    @PostMapping(value = "/edit/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView editBrandConfirm(@PathVariable(name = "id") String id,
                                         @ModelAttribute("brandBindingModel") BrandBindingModel brandBindingModel,
                                         BindingResult bindingResult, ModelAndView modelAndView) {
        this.validator.validate(brandBindingModel, bindingResult);

        if (bindingResult.hasErrors()) {
            modelAndView.addObject("brandBindingModel", brandBindingModel);
            return super.view("brand/brand-edit", modelAndView);
        }

        BrandServiceModel brandServiceModel = this.modelMapper.map(brandBindingModel, BrandServiceModel.class);
        this.brandService.editBrand(id, brandServiceModel);

       // this.logAction(principal.getName(), "Edited brand " + brandServiceModel.getName());

        return super.redirect("/brands/brands-all");
    }

    @GetMapping(value = "/delete/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView deleteBrand(@PathVariable(name = "id") String id, ModelAndView modelAndView) {

        BrandServiceModel brandServiceModel = this.brandService.findBrandById(id);
        BrandBindingModel brandBindingModel = this.modelMapper.map(brandServiceModel, BrandBindingModel.class);

        modelAndView.addObject("brandBindingModel", brandBindingModel );
        return super.view("brand/brand-delete", modelAndView);
    }

    @PostMapping(value = "/delete/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView deleteBrandConfirm(@PathVariable(name = "id") String id) {

        this.brandService.deleteBrand(id);

        return super.redirect("/brands/brands-all");
    }

    @GetMapping("/all-brands")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView allBrands(ModelAndView modelAndView) {

        modelAndView.addObject("brands",
                this.brandService.findAllBrands()
                        .stream()
                        .map(b -> this.modelMapper.map(b, BrandViewModel.class))
                        .collect(Collectors.toList()));

        return super.view("brand/brands-all", modelAndView);
    }


    @GetMapping("/products/{id}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView allProductsByBrand(@PathVariable String id, ModelAndView modelAndView) {

        BrandServiceModel brandServiceModel = this.brandService.findBrandById(id);

        List<ProductServiceModel>  productServiceModels =
                this.productService.findAllByBrand(brandServiceModel.getName());

        modelAndView.addObject("products", productServiceModels);
        modelAndView.addObject("brand", brandServiceModel);
        return super.view("brand/brand-product", modelAndView);
    }

}
