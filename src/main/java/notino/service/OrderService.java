package notino.service;

import notino.domain.models.service.OrderProductServiceModel;
import notino.domain.models.service.OrderServiceModel;

import java.util.List;

public interface OrderService {

    void addOrder(List<OrderProductServiceModel> orderProductServiceModels);

    List<OrderServiceModel> findAllOrders();

    OrderServiceModel setTotalPrice(OrderServiceModel orderServiceModel);
}
