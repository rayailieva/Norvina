package norvina.service;

import norvina.domain.entities.Brand;
import norvina.domain.entities.Product;
import norvina.domain.models.service.ProductServiceModel;
import norvina.error.BrandNotFoundException;
import norvina.error.ProductNotFoundException;
import norvina.repository.BrandRepository;
import norvina.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, BrandRepository brandRepository,ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.brandRepository = brandRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ProductServiceModel addProduct(ProductServiceModel productServiceModel) {

        Product product = this.modelMapper.map(productServiceModel, Product.class);
        Brand brand = this.brandRepository
                .findByName(productServiceModel.getBrand().getName())
                .orElse(null);
        if(brand == null){
            throw new BrandNotFoundException("Brand with the given id is not found!");
        }

        product.setBrand(brand);
        this.productRepository.saveAndFlush(product);

        brand.getProducts().add(product);
        this.brandRepository.saveAndFlush(brand);

        return this.modelMapper.map(product, ProductServiceModel.class);

    }

    @Override
    public List<ProductServiceModel> findAllProducts() {
        return this.productRepository.findAll()
                .stream()
                .map(p -> this.modelMapper.map(p, ProductServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public ProductServiceModel findProductById(String id) {
        return this.productRepository.findById(id)
                .map(p -> this.modelMapper.map(p, ProductServiceModel.class))
                .orElseThrow(() -> new ProductNotFoundException("Product with the given id is not found!"));
    }

    @Override
    public ProductServiceModel editProduct(String id, ProductServiceModel productServiceModel) {
        Product product = this.productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with the given id is not found!"));

        product.setName(productServiceModel.getName());
        product.setDescription(productServiceModel.getDescription());
        product.setImageUrl(productServiceModel.getImageUrl());
        product.setPrice(productServiceModel.getPrice());

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
    public List<ProductServiceModel> findAllByBrand(String brandName) {

       Brand brand = this.brandRepository.findByName(brandName).orElse(null);

       if(brand == null){
           throw new IllegalArgumentException("Brand is null :|");
       }

       return brand.getProducts()
               .stream()
               .map(p -> this.modelMapper.map(p, ProductServiceModel.class))
               .collect(Collectors.toList());
    }

    @Override
    public List<ProductServiceModel> findAllByCategory(String categoryName) {

       return this.productRepository
                .findAll()
                .stream()
                .filter(p -> p.getCategory().name().equals(categoryName))
                .map(p -> this.modelMapper.map(p, ProductServiceModel.class))
                .collect(Collectors.toList());


    }
}
