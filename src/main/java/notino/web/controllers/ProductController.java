package notino.web.controllers;

import notino.domain.models.binding.ProductCreateBindingModel;
import notino.domain.models.service.ProductServiceModel;
import notino.domain.models.view.ProductViewModel;
import notino.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ProductController {

    private ProductService productService;
    private ModelMapper modelMapper;

    @Autowired
    public ProductController(ProductService productService, ModelMapper modelMapper) {
        this.productService = productService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/product-add")
    public ModelAndView addProduct(ModelAndView modelAndView){

        modelAndView.setViewName("product-add");

        return modelAndView;
    }

    @PostMapping("/product-add")
       public ModelAndView addProductConfirm(@ModelAttribute ProductCreateBindingModel model,
                                        ModelAndView modelAndView){
         ProductServiceModel productServiceModel=
                  this.productService.addProduct(this.modelMapper.map(model, ProductServiceModel.class));

          if(productServiceModel == null){
              throw new IllegalArgumentException("Product creation failed!");
          }

          modelAndView.setViewName("redirect:/login");

          return modelAndView;
      }

    @GetMapping("/hair-products")
    public ModelAndView viewHairProducts(ModelAndView modelAndView){

        List<ProductViewModel> products =
                this.productService.findProductsByCategory("Hair")
                .stream()
                .map(p -> this.modelMapper.map(p, ProductViewModel.class))
                .collect(Collectors.toList());


        modelAndView.setViewName("hair-products");
        modelAndView.addObject("products", products);

        return modelAndView;
    }

    @GetMapping("/body-products")
    public ModelAndView viewBodyProducts(ModelAndView modelAndView){

        List<ProductViewModel> products =
                this.productService.findProductsByCategory("Body")
                        .stream()
                        .map(p -> this.modelMapper.map(p, ProductViewModel.class))
                        .collect(Collectors.toList());


        modelAndView.setViewName("body-products");
        modelAndView.addObject("products", products);

        return modelAndView;
    }
}
