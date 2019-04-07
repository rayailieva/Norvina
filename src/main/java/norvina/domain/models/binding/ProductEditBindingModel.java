package norvina.domain.models.binding;

import norvina.domain.entities.Category;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class ProductEditBindingModel {

    private String id;
    private String name;
    private String imageUrl;
    private String description;
    private BigDecimal price;
    private Category category;

    public ProductEditBindingModel() {
    }

    @NotNull(message = "Name cannot be null!")
    @Length(min = 2, max = 50, message = "Name must be at least 2 symbols long.")
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull(message = "Description cannot be null!")
    @Length(min = 5, message = "Description must be at least 5 symbols long.")
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NotNull(message = "Price cannot be null!")
    @Min(value = 1, message = "Price cannot be less than 1")
    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @NotNull(message = "Image url cannot be null!")
    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @NotNull(message = "Category cannot be null!")
    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
