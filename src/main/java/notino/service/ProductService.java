package notino.service;

import notino.domain.models.binding.ProductEditBindingModel;
import notino.domain.models.service.ProductServiceModel;

import java.util.List;

public interface ProductService {

    List<ProductServiceModel> findAllProducts();

    ProductEditBindingModel findProductToEditById(String id);

    void editProduct(ProductEditBindingModel productEditBindingModel);

    ProductServiceModel addProduct(ProductServiceModel productServiceModel);

    boolean deleteProduct(String id);

    ProductServiceModel addProductToBasket(ProductServiceModel productServiceModel);


}
