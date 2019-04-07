package norvina.service;

import norvina.domain.models.service.ProductServiceModel;

import java.util.List;

public interface ProductService {


    ProductServiceModel addProduct(ProductServiceModel productServiceModel);

    List<ProductServiceModel> findAllProducts();

    ProductServiceModel findProductById(String id);

    ProductServiceModel editProduct(String id, ProductServiceModel productServiceModel);

    boolean deleteProduct(String id);

    List<ProductServiceModel> findAllByBrand(String brandName);

    List<ProductServiceModel> findAllByCategory(String categoryName);
}
