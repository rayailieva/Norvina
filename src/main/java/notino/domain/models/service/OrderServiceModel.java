package notino.domain.models.service;

import notino.domain.entities.OrderProduct;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderServiceModel {

    private String id;
    private LocalDateTime date;
    private BigDecimal totalPrice;
    private List<OrderProductServiceModel> orderItems;


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

    public List<OrderProductServiceModel> getOrderItems() {
        return this.orderItems;
    }

    public void setOrderItems(List<OrderProductServiceModel> orderItems) {
        this.orderItems = orderItems;
    }
}
