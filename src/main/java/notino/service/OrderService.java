package notino.service;

import notino.domain.models.service.OrderServiceModel;

import java.util.List;

public interface OrderService {


    void createOrder(String productId, String name) throws Exception;

    List<OrderServiceModel> findAllOrders();

    List<OrderServiceModel> findOrdersByCustomer(String username);
}
