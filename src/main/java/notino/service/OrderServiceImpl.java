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

import java.time.LocalDateTime;
import java.util.List;

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

            this.orderProductRepository.saveAndFlush(this.modelMapper.map(orderProductServiceModel, OrderProduct.class));
        }

        Order order = this.modelMapper.map(orderServiceModel, Order.class);

        this.orderRepository.saveAndFlush(order);
    }
}
