package notino.domain.models.service;

import notino.domain.entities.Brand;
import notino.domain.entities.Category;

import java.math.BigDecimal;
import java.util.List;

public class ProductServiceModel {

    private String id;
    private String name;
    private String description;
    private String imageUrl;
    private BigDecimal price;
    private Category category;
    private BrandServiceModel brand;

    public ProductServiceModel(){}

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public BrandServiceModel getBrand() {
        return this.brand;
    }

    public void setBrand(BrandServiceModel brand) {
        this.brand = brand;
    }
}
