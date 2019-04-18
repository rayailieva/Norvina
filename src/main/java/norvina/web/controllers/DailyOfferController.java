package norvina.web.controllers;

import norvina.domain.entities.DailyOffer;
import norvina.domain.models.service.DailyOfferServiceModel;
import norvina.domain.models.service.ProductServiceModel;
import norvina.domain.models.view.DailyOfferViewModel;
import norvina.domain.models.view.ProductViewModel;
import norvina.service.DailyOfferService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class DailyOfferController extends BaseController{

    private final DailyOfferService offerService;
    private final ModelMapper modelMapper;

    @Autowired
    public DailyOfferController(DailyOfferService offerService, ModelMapper modelMapper) {
        this.offerService = offerService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/daily-offers")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView topOffers(ModelAndView modelAndView) {

        DailyOfferServiceModel dailyOfferServiceModel = this.offerService.findActiveOffer();

        DailyOfferViewModel offer = this.modelMapper.map(dailyOfferServiceModel, DailyOfferViewModel.class);
        offer.setProductViewModel(this.modelMapper.map(dailyOfferServiceModel.getProductServiceModel(), ProductViewModel.class));
        modelAndView.addObject("offer", offer);

        return super.view("offer/daily-offers", modelAndView);
    }

}
