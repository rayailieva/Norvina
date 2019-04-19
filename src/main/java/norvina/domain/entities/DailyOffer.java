package norvina.domain.entities;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "daily_offers")
public class DailyOffer extends BaseEntity{

    private Product product;
    private BigDecimal price;

    public DailyOffer() {
    }

    @ManyToOne(targetEntity = Product.class, cascade = CascadeType.ALL)
    @JoinColumn(
            name = "product_id",
            referencedColumnName = "id"
    )
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Column(name = "price")
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
