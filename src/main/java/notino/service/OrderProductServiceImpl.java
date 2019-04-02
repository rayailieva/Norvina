package notino.service;

import notino.domain.entities.Order;
import notino.domain.entities.OrderProduct;
import notino.domain.entities.User;
import notino.domain.models.service.OrderProductServiceModel;
import notino.domain.models.service.UserServiceModel;
import notino.repository.OrderProductRepository;
import notino.repository.OrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderProductServiceImpl implements OrderProductService {

    private final OrderProductRepository orderProductRepository;
    private final OrderRepository orderRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public OrderProductServiceImpl(OrderProductRepository orderProductRepository, OrderRepository orderRepository, UserService userService, ModelMapper modelMapper) {
        this.orderProductRepository = orderProductRepository;
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public OrderProductServiceModel addOrderProduct(OrderProductServiceModel orderProductServiceModel) {
        OrderProduct orderProduct = this.modelMapper.map(orderProductServiceModel, OrderProduct.class);

        orderProduct.setQuantity(orderProductServiceModel.getQuantity());
        this.orderProductRepository.saveAndFlush(orderProduct);

        return this.modelMapper.map(orderProduct, OrderProductServiceModel.class);
    }

    @Override
    public List<OrderProductServiceModel> findAllOrderProducts() {
        return this.orderProductRepository.findAll()
                .stream()
                .map(op -> this.modelMapper.map(op, OrderProductServiceModel.class))
                .collect(Collectors.toList());
    }


}
