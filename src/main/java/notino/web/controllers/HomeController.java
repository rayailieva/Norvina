package notino.web.controllers;

import notino.domain.models.view.BrandViewModel;
import notino.service.BrandService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController extends BaseController{

    private final BrandService brandService;
    private final ModelMapper modelMapper;

    @Autowired
    public HomeController(BrandService brandService, ModelMapper modelMapper) {
        this.brandService = brandService;
        this.modelMapper = modelMapper;
    }


    @GetMapping("/")
    public ModelAndView index(){

        return super.view("index");
    }

    @GetMapping("/home")
    public ModelAndView home(ModelAndView modelAndView){
        List<BrandViewModel> brands = this.brandService.findAllBrands()
                .stream()
                .map(b -> this.modelMapper.map(b, BrandViewModel.class))
                .collect(Collectors.toList());

        modelAndView.addObject("brands", brands);

        return super.view("home", modelAndView);
    }

}
