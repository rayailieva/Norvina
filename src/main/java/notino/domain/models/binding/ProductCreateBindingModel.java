package notino.domain.models.binding;

import notino.domain.entities.Brand;
import notino.domain.entities.Category;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class ProductCreateBindingModel {

    private String name;
    private String description;
    private String imageUrl;
    private BigDecimal price;
    private Category category;
    private String brand;

    public ProductCreateBindingModel() {
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

    @NotNull(message = "Brand cannot be null!")
    public String getBrand() {
        return this.brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
