package norvina.domain.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "products")
public class Product extends BaseEntity{

    private String name;
    private String description;
    private String imageUrl;
    private BigDecimal price;
    private Category category;
    private Brand brand;
    private List<OrderProduct> orderProducts;
    private List<DailyOffer> dailyOffers;

    public Product(){}

    @Column(name = "name", nullable = false, unique = true)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "description", columnDefinition = "text")
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "price")
    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

   @Enumerated(EnumType.STRING)
   @Column(name = "category")
    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }


    @Column(name = "image_src")
    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @ManyToOne()
    public Brand getBrand() {
        return this.brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    @OneToMany(targetEntity = OrderProduct.class, mappedBy = "product", cascade = CascadeType.ALL)
    public List<OrderProduct> getOrderProducts() {
        return this.orderProducts;
    }

    public void setOrderProducts(List<OrderProduct> orderProducts) {
        this.orderProducts = orderProducts;
    }

    @OneToMany(targetEntity = DailyOffer.class, mappedBy = "product", cascade = CascadeType.ALL)
    public List<DailyOffer> getDailyOffers() {
        return this.dailyOffers;
    }

    public void setDailyOffers(List<DailyOffer> dailyOffers) {
        this.dailyOffers = dailyOffers;
    }
}
