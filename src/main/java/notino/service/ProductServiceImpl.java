package notino.service;

import notino.domain.entities.Product;
import notino.domain.models.binding.ProductEditBindingModel;
import notino.domain.models.service.ProductServiceModel;
import notino.repository.ProductRepository;
import notino.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public List<ProductServiceModel> findAllProducts() {
        return this.productRepository.findAll()
                .stream()
                .map(p -> this.modelMapper.map(p, ProductServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public ProductEditBindingModel findProductToEditById(String id) {
       Product productFromDb = this.productRepository.findById(id).orElse(null);

       if(productFromDb == null){
           throw new IllegalArgumentException("Non-existent product.");
       }

        return this.modelMapper.map(productFromDb, ProductEditBindingModel.class);
    }

    @Override
    public void editProduct(ProductEditBindingModel productEditBindingModel) {

        Product product = this.productRepository.findByName(productEditBindingModel.getName())
                .orElse(null);

        if(product == null){
            throw new IllegalArgumentException("Non-existent product.");
        }

        this.productRepository.save(product);
    }


    @Override
    public ProductServiceModel addProduct(ProductServiceModel productServiceModel) {

        Product product =
                this.modelMapper.map(productServiceModel, Product.class);

        this.productRepository.saveAndFlush(product);
        return this.modelMapper.map(product, ProductServiceModel.class);

    }

    @Override
    public boolean deleteProduct(String id) {
        try {
            this.productRepository.deleteById(id);

            return true;
        } catch (Exception e) {
            e.printStackTrace();

            return false;
        }
    }

    @Override
    public ProductServiceModel addProductToBasket(ProductServiceModel productServiceModel) {
        return null;
    }
}
