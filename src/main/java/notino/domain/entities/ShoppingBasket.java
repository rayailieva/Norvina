package notino.domain.entities;

import jdk.jfr.Enabled;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "active_shopping_baskets")
public class ShoppingBasket extends BaseEntity{

    private List<OrderProduct> products;
    private BigDecimal totalPrice;
    private User user;

    public ShoppingBasket() {
    }

    @OneToMany(targetEntity = OrderProduct.class, mappedBy = "shoppingBasket")
    public List<OrderProduct> getProducts() {
        return this.products;
    }

    public void setProducts(List<OrderProduct> products) {
        this.products = products;
    }

    @Column(name = "total_price")
    public BigDecimal getTotalPrice() {
        return this.totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    @OneToOne(targetEntity = User.class)
    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
