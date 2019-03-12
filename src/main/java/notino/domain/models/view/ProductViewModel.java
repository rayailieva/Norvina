package notino.domain.models.view;

import notino.domain.entities.Category;

import java.math.BigDecimal;

public class ProductViewModel {

    private String name;
    private String description;
    private BigDecimal price;
    private Category category;

    public ProductViewModel(){}

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

    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
