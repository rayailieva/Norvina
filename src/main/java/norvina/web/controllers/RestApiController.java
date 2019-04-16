package norvina.web.controllers;

import norvina.domain.models.view.ProductViewModel;
import norvina.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class RestApiController {

    private final ProductService productService;
    private final ModelMapper modelMapper;

    @Autowired
    public RestApiController(ProductService productService, ModelMapper modelMapper) {
        this.productService = productService;
        this.modelMapper = modelMapper;
    }

    @ResponseBody
    @GetMapping(value = "/fetch/products", produces = "application/json")
    public List<ProductViewModel> products(){
        return this.productService.findAll();

    }

    @ResponseBody
    @GetMapping(value = "/fetch/category/makeup", produces = "application/json")
    public List<ProductViewModel> makeup(){
        return this.productService.findAllByCategory("Makeup")
          .stream()
          .map(b -> this.modelMapper.map(b, ProductViewModel.class))
          .collect(Collectors.toList());
    }

    @ResponseBody
    @GetMapping(value = "/fetch/category/sun", produces = "application/json")
    public List<ProductViewModel> sun(){
        return this.productService.findAllByCategory("Sun")
                .stream()
                .map(b -> this.modelMapper.map(b, ProductViewModel.class))
                .collect(Collectors.toList());
    }

    @ResponseBody
    @GetMapping(value = "/fetch/category/hair", produces = "application/json")
    public List<ProductViewModel> hair(){
        return this.productService.findAllByCategory("Hair")
                .stream()
                .map(b -> this.modelMapper.map(b, ProductViewModel.class))
                .collect(Collectors.toList());
    }

    @ResponseBody
    @GetMapping(value = "/fetch/category/skin", produces = "application/json")
    public List<ProductViewModel> skin(){
        return this.productService.findAllByCategory("Skin")
                .stream()
                .map(b -> this.modelMapper.map(b, ProductViewModel.class))
                .collect(Collectors.toList());
    }

    @ResponseBody
    @GetMapping(value = "/fetch/category/fragrances", produces = "application/json")
    public List<ProductViewModel> fragrances(){
        return this.productService.findAllByCategory("Fragrance")
                .stream()
                .map(b -> this.modelMapper.map(b, ProductViewModel.class))
                .collect(Collectors.toList());
    }

    @ResponseBody
    @GetMapping(value = "/fetch/category/body", produces = "application/json")
    public List<ProductViewModel> body(){
        return this.productService.findAllByCategory("Body")
                .stream()
                .map(b -> this.modelMapper.map(b, ProductViewModel.class))
                .collect(Collectors.toList());
    }


}
