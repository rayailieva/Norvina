package norvina.service;

import norvina.domain.models.service.DailyOfferServiceModel;

import java.util.List;

public interface DailyOfferService {

    DailyOfferServiceModel findActiveOffer();
}
