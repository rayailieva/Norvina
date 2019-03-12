package notino.domain.models.service;

import java.util.List;

public class CategoryServiceModel {

    private String id;
    private String name;
    private List<ProductServiceModel> products;

    public CategoryServiceModel() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ProductServiceModel> getProducts() {
        return this.products;
    }

    public void setProducts(List<ProductServiceModel> products) {
        this.products = products;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
