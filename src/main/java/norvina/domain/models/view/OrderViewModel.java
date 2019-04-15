package norvina.domain.models.view;

import norvina.domain.entities.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderViewModel {

    private String id;
    private LocalDateTime date;
    private BigDecimal totalPrice;
    private UserViewModel customer;
    private OrderStatus orderStatus;
    private List<ProductViewModel> products;


    public OrderViewModel() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return this.date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public BigDecimal getTotalPrice() {
        return this.totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public UserViewModel getCustomer() {
        return this.customer;
    }

    public void setCustomer(UserViewModel customer) {
        this.customer = customer;
    }

    public OrderStatus getOrderStatus() {
        return this.orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public List<ProductViewModel> getProducts() {
        return this.products;
    }

    public void setProducts(List<ProductViewModel> products) {
        this.products = products;
    }
}
