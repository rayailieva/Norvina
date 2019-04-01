package notino.domain.entities;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

    private User user;
    private LocalDateTime date;
    private BigDecimal totalPrice;
    private List<OrderProduct> orderItems;

    public Order(){
    }

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id", nullable = false)
    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column(name = "date", nullable = false)
    public LocalDateTime getDate() {
        return this.date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Column(name = "total_price", nullable = false)
    public BigDecimal getTotalPrice() {
        return this.totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    @OneToMany(targetEntity = OrderProduct.class, mappedBy = "order")
    public List<OrderProduct> getOrderItems() {
        return this.orderItems;
    }

    public void setOrderItems(List<OrderProduct> orderItems) {
        this.orderItems = orderItems;
    }
}
