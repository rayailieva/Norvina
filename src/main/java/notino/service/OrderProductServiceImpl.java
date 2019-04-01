package notino.service;

import notino.domain.entities.OrderProduct;
import notino.domain.models.service.OrderProductServiceModel;
import notino.repository.OrderProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderProductServiceImpl implements OrderProductService {

    private final OrderProductRepository orderProductRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public OrderProductServiceImpl(OrderProductRepository orderProductRepository, ModelMapper modelMapper) {
        this.orderProductRepository = orderProductRepository;
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
