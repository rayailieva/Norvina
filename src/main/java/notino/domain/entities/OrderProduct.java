package notino.domain.entities;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "order_products")
public class OrderProduct extends BaseEntity{

    private Order order;
    private Product product;
    private Integer quantity;
    private User user;
    private ShoppingBasket shoppingBasket;

    public OrderProduct(){}

    @ManyToOne(targetEntity = Order.class)
    @JoinColumn(name = "order_id", referencedColumnName = "id", nullable = false)
    public Order getOrder() {
        return this.order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @ManyToOne(targetEntity = Product.class)
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Column(name = "quantity", nullable = false)
    public Integer getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @ManyToOne(targetEntity = User.class)
    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne(targetEntity = ShoppingBasket.class)
    public ShoppingBasket getShoppingBasket() {
        return this.shoppingBasket;
    }

    public void setShoppingBasket(ShoppingBasket shoppingBasket) {
        this.shoppingBasket = shoppingBasket;
    }
}
