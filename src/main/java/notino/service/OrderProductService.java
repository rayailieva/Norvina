package notino.service;

import notino.domain.models.service.OrderProductServiceModel;

import java.util.List;

public interface OrderProductService {

    OrderProductServiceModel addOrderProduct(OrderProductServiceModel orderProductServiceModel);

    List<OrderProductServiceModel> findAllOrderProducts();
}
