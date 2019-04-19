package norvina.domain.entities;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "order_products")
public class OrderProduct extends BaseEntity{

    private Product product;
    private BigDecimal price;

    @ManyToOne()
    @JoinColumn(
            name = "product_id",
            referencedColumnName = "id"
    )
    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Column(name = "price")
    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
