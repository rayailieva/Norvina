package notino.domain.entities;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

    private User user;
    private LocalDateTime dateTime;
    private List<OrderProduct> products;

    public Order(){
    }

    @ManyToOne(targetEntity = User.class)
    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column(name = "order_date_time")
    public LocalDateTime getDateTime() {
        return this.dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @OneToMany(targetEntity = OrderProduct.class, mappedBy = "order")
    public List<OrderProduct> getProducts() {
        return this.products;
    }

    public void setProducts(List<OrderProduct> products) {
        this.products = products;
    }

}
