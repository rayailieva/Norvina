package notino.service;

import notino.domain.entities.Order;
import notino.domain.models.service.OrderServiceModel;
import notino.repository.OrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public OrderServiceModel addOrder(OrderServiceModel orderServiceModel) {

        Order order = this.modelMapper.map(orderServiceModel, Order.class);

        this.orderRepository.saveAndFlush(order);

        return this.modelMapper.map(order, OrderServiceModel.class);
    }
}
