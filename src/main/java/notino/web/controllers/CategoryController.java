package notino.web.controllers;

import notino.domain.models.binding.CategoryCreateBindingModel;
import notino.domain.models.service.CategoryServiceModel;
import notino.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CategoryController {

    private CategoryService categoryService;
    private ModelMapper modelMapper;

    @Autowired
    public CategoryController(CategoryService categoryService, ModelMapper modelMapper) {
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/category-add")
    public ModelAndView addProduct(ModelAndView modelAndView){

        modelAndView.setViewName("category-add");

        return modelAndView;
    }

    @PostMapping("/category-add")
    public ModelAndView addProductConfirm(@ModelAttribute CategoryCreateBindingModel model,
                                          ModelAndView modelAndView){
        CategoryServiceModel categoryServiceModel=
                this.categoryService.addCategory(this.modelMapper.map(model, CategoryServiceModel.class));

        if(categoryServiceModel == null){
            throw new IllegalArgumentException("Category creation failed!");
        }

        modelAndView.setViewName("redirect:/login");

        return modelAndView;
    }

}
