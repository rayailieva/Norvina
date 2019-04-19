package norvina.service;

import norvina.domain.entities.DailyOffer;
import norvina.domain.entities.Product;
import norvina.domain.models.service.DailyOfferServiceModel;
import norvina.domain.models.service.ProductServiceModel;
import norvina.domain.models.view.ProductViewModel;
import norvina.repository.DailyOfferRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class DailyOfferServiceImpl implements DailyOfferService {

    private final DailyOfferRepository dailyOfferRepository;
    private final ProductService productService;
    private final ModelMapper modelMapper;


    @Autowired
    public DailyOfferServiceImpl(DailyOfferRepository dailyOfferRepository, ProductService productService, ModelMapper modelMapper) {
        this.dailyOfferRepository = dailyOfferRepository;
        this.productService = productService;
        this.modelMapper = modelMapper;
    }


    @Override
    public DailyOfferServiceModel findActiveOffer() {

     DailyOffer dailyOffer = this.dailyOfferRepository.findAll().get(0);

     DailyOfferServiceModel dailyOfferServiceModel =  this.modelMapper.map(this.dailyOfferRepository
                .findAll().get(0), DailyOfferServiceModel.class);

     dailyOfferServiceModel
             .setProductServiceModel(this.modelMapper.map(dailyOffer.getProduct(), ProductServiceModel.class));

     return dailyOfferServiceModel;
    }

    @Scheduled(fixedRate = 500000)
    private void generateOffers() {
        this.dailyOfferRepository.deleteAll();
        List<ProductServiceModel> products = this.productService.findAllProducts();

        if (products.isEmpty()) {
            return;
        }

        Random rnd = new Random();
        List<DailyOffer> offers = new ArrayList<>();

        for (int i = 0; i < 1; i++) {
            DailyOffer offer = new DailyOffer();

            offer.setProduct(this.modelMapper.map(products.get(rnd.nextInt(products.size())), Product.class));
            offer.setPrice(offer.getProduct().getPrice().multiply(new BigDecimal(0.5)));

            if (offers.stream().filter(o -> o.getProduct().getId().equals(offer.getProduct().getId())).count() == 0) {
                offers.add(offer);
            }
        }

        this.dailyOfferRepository.saveAll(offers);
    }
}
