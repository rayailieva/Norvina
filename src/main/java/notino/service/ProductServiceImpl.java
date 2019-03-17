package notino.service;

import notino.domain.entities.Category;
import notino.domain.entities.Product;
import notino.domain.entities.User;
import notino.domain.models.service.ProductServiceModel;
import notino.repository.CategoryRepository;
import notino.repository.ProductRepository;
import notino.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, UserRepository userRepository, CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ProductServiceModel> findProductsByCategory(String categoryName) {
        return this.productRepository.
                        findAllByCategory_Name(categoryName)
                        .stream()
                        .map(p -> this.modelMapper.map(p, ProductServiceModel.class))
                        .collect(Collectors.toList());
    }

    @Override
    public ProductServiceModel addProduct(ProductServiceModel productServiceModel) {

        Product product =
                this.modelMapper.map(productServiceModel, Product.class);

        this.productRepository.saveAndFlush(product);
        return this.modelMapper.map(product, ProductServiceModel.class);

    }

    @Override
    public ProductServiceModel addProductToBasket(ProductServiceModel productServiceModel) {
        return null;
    }
}
