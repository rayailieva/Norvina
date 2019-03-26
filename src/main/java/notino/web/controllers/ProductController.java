package notino.web.controllers;

import notino.domain.models.binding.ProductCreateBindingModel;
import notino.domain.models.binding.ProductEditBindingModel;
import notino.domain.models.binding.UserEditProfileBindingModel;
import notino.domain.models.service.ProductServiceModel;
import notino.domain.models.view.ProductViewModel;
import notino.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/products")
public class ProductController extends BaseController{

    private final ProductService productService;
    private final ModelMapper modelMapper;

    @Autowired
    public ProductController(ProductService productService, ModelMapper modelMapper) {
        this.productService = productService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/add")
    public ModelAndView addProduct(@ModelAttribute(name = "bindingModel") ProductCreateBindingModel productBindingModel){

       return super.view("product-add", "productBindingModel", productBindingModel);

    }

    @PostMapping("/add")
       public ModelAndView addProductConfirm(@ModelAttribute(name = "productBindingModel") ProductCreateBindingModel productBindingModel){

         ProductServiceModel productServiceModel=
                  this.productService.addProduct(this.modelMapper.map(productBindingModel, ProductServiceModel.class));

          if(productServiceModel == null){
              throw new IllegalArgumentException("Product creation failed!");
          }

          return super.redirect("/home");
      }


    @GetMapping(value = "/edit/{id}")
    public ModelAndView editProduct(@PathVariable(name = "id") String id ){

        ProductEditBindingModel productBindingModel = this.productService.findProductToEditById(id);

        return super.view("product-edit", "productBindingModel", productBindingModel);

    }

    @PostMapping(value = "/edit/{id}")
    public ModelAndView editProductConfirm(@PathVariable(name = "id") String id,
                                           @ModelAttribute("productBindingModel") ProductEditBindingModel productBindingModel) {

         super.view("products/product-edit", "productBindingModel" ,productBindingModel);


        this.productService.editProduct(productBindingModel);

        return super.redirect("/products/all-products");
    }

    @GetMapping(value = "/details/{id}")
    public ModelAndView detailsProduct(@PathVariable(name = "id") String id ){

        ProductEditBindingModel productBindingModel = this.productService.findProductToEditById(id);

        return super.view("product-details", "productBindingModel", productBindingModel);

    }


    @GetMapping(value = "/delete/{id}")
    public ModelAndView deleteProduct(@PathVariable(name = "id") String id ){

        ProductEditBindingModel productBindingModel = this.productService.findProductToEditById(id);

        return super.view("product-delete", "productBindingModel", productBindingModel);

    }

    @PostMapping(value = "/delete/{id}")
    public ModelAndView deleteProductConfirm(@PathVariable(name = "id") String id ){

        if(!this.productService.deleteProduct(id)){
            throw new IllegalArgumentException("Something went wrong!");
        }

        return super.redirect("/home");
    }


    @GetMapping("/all-products")
    public ModelAndView viewAllProducts(){

        List<ProductViewModel> products =
                this.productService.findAllProducts()
                .stream()
                .map(p -> this.modelMapper.map(p, ProductViewModel.class))
                .collect(Collectors.toList());


        return super.view("all-products", "products", products);

    }

}
