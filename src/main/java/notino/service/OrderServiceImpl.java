package notino.service;

import notino.domain.entities.Order;
import notino.domain.entities.OrderProduct;
import notino.domain.entities.User;
import notino.domain.models.service.OrderProductServiceModel;
import notino.domain.models.service.OrderServiceModel;
import notino.repository.OrderProductRepository;
import notino.repository.OrderRepository;
import notino.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, OrderProductRepository orderProductRepository, UserService userService, ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        this.orderProductRepository = orderProductRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public void addOrder(List<OrderProductServiceModel> orderProductServiceModels) {

        OrderServiceModel orderServiceModel = new OrderServiceModel();
        orderServiceModel.setDate(LocalDateTime.now());

        //User user = this.modelMapper.map(this.userService.findUserByUsername(name), User.class);

        orderServiceModel.setOrderItems(orderProductServiceModels);

        for(OrderProductServiceModel orderProductServiceModel : orderProductServiceModels){
            orderProductServiceModel.setOrder(orderServiceModel);

            this.orderProductRepository
                    .saveAndFlush(this.modelMapper.map(orderProductServiceModel, OrderProduct.class));
        }

        Order order = this.modelMapper.map(orderServiceModel, Order.class);

        this.orderRepository.saveAndFlush(order);
    }



    @Override
    public List<OrderServiceModel> findAllOrders() {
        return this.orderRepository.findAll()
                .stream()
                .map(o -> this.modelMapper.map(o, OrderServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public OrderServiceModel setTotalPrice(OrderServiceModel orderServiceModel) {
        Order order = this.modelMapper.map(orderServiceModel, Order.class);
        BigDecimal totalPrice = BigDecimal.ZERO;

        for(OrderProduct orderProduct : order.getOrderItems()){
            totalPrice = totalPrice.add(orderProduct.getProduct().getPrice());
        }

        order.setTotalPrice(totalPrice);
        this.orderRepository.saveAndFlush(order);

        return this.modelMapper.map(order, OrderServiceModel.class);
    }
}
