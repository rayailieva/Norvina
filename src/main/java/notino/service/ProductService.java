package notino.service;

import notino.domain.models.service.ProductServiceModel;

import java.util.List;

public interface ProductService {

    List<ProductServiceModel> findProductsByCategory(String categoryName);

    ProductServiceModel addProduct(ProductServiceModel productServiceModel);
}
