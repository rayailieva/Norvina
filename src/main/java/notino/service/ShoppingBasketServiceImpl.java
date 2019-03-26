package notino.service;

import notino.domain.entities.OrderProduct;
import notino.domain.entities.ShoppingBasket;
import notino.domain.models.service.OrderProductServiceModel;
import notino.repository.ShoppingBasketRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ShoppingBasketServiceImpl implements ShoppingBasketService {

    private final ShoppingBasketRepository shoppingBasketRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ShoppingBasketServiceImpl(ShoppingBasketRepository shoppingBasketRepository, ModelMapper modelMapper) {
        this.shoppingBasketRepository = shoppingBasketRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void addProductToBasket(OrderProductServiceModel orderProductServiceModel) {
        ShoppingBasket shoppingBasket = new ShoppingBasket();
        OrderProduct orderProduct = this.modelMapper.map(orderProductServiceModel, OrderProduct.class);

        shoppingBasket.getProducts().add(orderProduct);

        this.shoppingBasketRepository.saveAndFlush(shoppingBasket);
    }
}
