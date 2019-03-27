package notino.web.controllers;

import notino.domain.models.binding.BrandBindingModel;
import notino.domain.models.service.BrandServiceModel;
import notino.domain.models.view.BrandViewModel;
import notino.service.BrandService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.stream.Collectors;

@Controller
@RequestMapping("/brands")
public class BrandController extends BaseController {

    private final BrandService brandService;
    private final ModelMapper modelMapper;

    @Autowired
    public BrandController(BrandService brandService, ModelMapper modelMapper) {
        this.brandService = brandService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/add")
    public ModelAndView addBrand(@ModelAttribute(name = "bindingModel") BrandBindingModel brandBindingModel, ModelAndView modelAndView) {

        modelAndView.addObject( "brandBindingModel", brandBindingModel);
        return super.view("brand-add", modelAndView);
    }

    @PostMapping("/add")
    public ModelAndView addBrandConfirm(@ModelAttribute BrandBindingModel model) {

        this.brandService.addBrand(this.modelMapper.map(model, BrandServiceModel.class));
        return super.redirect("/brands/all-brands");
    }


    @GetMapping(value = "/edit/{id}")
    public ModelAndView editBrand(@PathVariable(name = "id") String id, ModelAndView modelAndView) {

        BrandServiceModel brandServiceModel = this.brandService.findBrandById(id);
        BrandBindingModel brandBindingModel = this.modelMapper.map(brandServiceModel, BrandBindingModel.class);

        modelAndView.addObject("brandBindingModel", brandBindingModel);
        return super.view("brand-edit", modelAndView);
    }

    @PostMapping(value = "/edit/{id}")
    public ModelAndView editBrandConfirm(@PathVariable(name = "id") String id,
                                         @ModelAttribute("brandBindingModel") BrandBindingModel brandBindingModel) {

       this.brandService.editBrand(id, this.modelMapper.map(brandBindingModel, BrandServiceModel.class));

       return super.redirect("/brands/all-brands");
    }

    @GetMapping(value = "/delete/{id}")
    public ModelAndView deleteBrand(@PathVariable(name = "id") String id, ModelAndView modelAndView) {

        BrandServiceModel brandServiceModel = this.brandService.findBrandById(id);
        BrandBindingModel brandBindingModel = this.modelMapper.map(brandServiceModel, BrandBindingModel.class);

        modelAndView.addObject("brandBindingModel", brandBindingModel );
        return super.view("brand-delete", modelAndView);
    }

    @PostMapping(value = "/delete/{id}")
    public ModelAndView deleteBrandConfirm(@PathVariable(name = "id") String id ) {

        if(!this.brandService.deleteBrand(id)){
            throw new IllegalArgumentException("Something went wrong!");
        }

        return super.redirect("/home");
    }

    @GetMapping("/all-brands")
    public ModelAndView allBrands(ModelAndView modelAndView) {


        modelAndView.addObject("brands",
                this.brandService.findAllBrands()
                        .stream()
                        .map(b -> this.modelMapper.map(b, BrandViewModel.class))
                        .collect(Collectors.toList()));
        return super.view("all-brands", modelAndView);
    }
}
