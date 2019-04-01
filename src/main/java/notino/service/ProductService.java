package notino.service;

import notino.domain.models.binding.ProductEditBindingModel;
import notino.domain.models.service.ProductServiceModel;

import java.util.List;

public interface ProductService {


    ProductServiceModel addProduct(ProductServiceModel productServiceModel);

    List<ProductServiceModel> findAllProducts();

    ProductServiceModel findProductById(String id);

    ProductServiceModel editProduct(String id, ProductServiceModel productServiceModel);

    boolean deleteProduct(String id);

    List<ProductServiceModel> findAllByBrand(String brandName);
}
