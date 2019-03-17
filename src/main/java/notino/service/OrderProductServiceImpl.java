package notino.service;

import notino.domain.entities.Product;
import notino.domain.models.service.OrderProductServiceModel;
import notino.repository.OrderProductRepository;
import notino.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderProductServiceImpl implements OrderProductService {

    private final OrderProductRepository orderProductRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public OrderProductServiceImpl(OrderProductRepository orderProductRepository, ProductRepository productRepository, ModelMapper modelMapper) {
        this.orderProductRepository = orderProductRepository;
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }


}
