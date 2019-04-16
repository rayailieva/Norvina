package norvina.service;

import norvina.domain.entities.Order;
import norvina.domain.entities.OrderStatus;
import norvina.domain.models.service.OrderServiceModel;
import norvina.error.OrderNotFoundException;
import norvina.repository.OrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, UserService userService, ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public void createOrder(OrderServiceModel orderServiceModel) {
        orderServiceModel.setDate(LocalDateTime.now());
        orderServiceModel.setOrderStatus(OrderStatus.Pending);
        Order order = this.modelMapper.map(orderServiceModel, Order.class);
        this.orderRepository.saveAndFlush(order);
    }

    @Override
    public List<OrderServiceModel> findAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(o -> modelMapper.map(o, OrderServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderServiceModel> findOrdersByCustomer(String username) {
        return this.orderRepository.findAllByCustomer_Username(username)
                .stream()
                .map(o -> modelMapper.map(o, OrderServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public OrderServiceModel findOrderById(String id) {
        return this.orderRepository.findById(id)
                .map(o -> this.modelMapper.map(o, OrderServiceModel.class))
                .orElseThrow(() -> new OrderNotFoundException("Order with the given id is not found!"));
    }

    @Override
    public OrderServiceModel editOrder(String id, OrderServiceModel orderServiceModel) {
        Order order = this.orderRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);

        order.setOrderStatus(OrderStatus.Shipped);

        this.orderRepository.saveAndFlush(order);

        return this.modelMapper.map(order, OrderServiceModel.class);
    }
}
