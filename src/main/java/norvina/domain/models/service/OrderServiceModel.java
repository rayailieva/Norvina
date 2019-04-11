package norvina.domain.models.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderServiceModel {

    private String id;
    private LocalDateTime date;
    private BigDecimal totalPrice;
    private UserServiceModel customer;
    private List<OrderProductServiceModel> products;

    public OrderServiceModel(){}

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

    public UserServiceModel getCustomer() {
        return this.customer;
    }

    public void setCustomer(UserServiceModel customer) {
        this.customer = customer;
    }

    public List<OrderProductServiceModel> getProducts() {
        return this.products;
    }

    public void setProducts(List<OrderProductServiceModel> products) {
        this.products = products;
    }
}
