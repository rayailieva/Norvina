package notino.service;

import notino.domain.models.service.OrderProductServiceModel;

public interface ShoppingBasketService {

    void addProductToBasket(OrderProductServiceModel orderProductServiceModel);
}
