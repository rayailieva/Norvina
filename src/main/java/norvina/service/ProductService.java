package norvina.service;

import norvina.domain.models.service.ProductServiceModel;
import norvina.domain.models.view.ProductViewModel;

import java.util.List;

public interface ProductService {


    ProductServiceModel addProduct(ProductServiceModel productServiceModel);

    List<ProductServiceModel> findAllProducts();

    List<ProductViewModel> findAll();

    ProductServiceModel findProductById(String id);

    ProductServiceModel editProduct(String id, ProductServiceModel productServiceModel);

    ProductServiceModel deleteProduct(String id);

    List<ProductServiceModel> findAllByBrand(String brandName);

    List<ProductServiceModel> findAllByCategory(String categoryName);
}
