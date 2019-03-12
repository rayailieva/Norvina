package notino.domain.models.service;

import java.time.LocalDateTime;
import java.util.List;

public class OrderServiceModel {

    private String id;
    private UserServiceModel userServiceModel;
    private LocalDateTime dateTime;
    private String status;
    private List<OrderProductServiceModel> products;

    public OrderServiceModel(){}

    public UserServiceModel getUserServiceModel() {
        return this.userServiceModel;
    }

    public void setUserServiceModel(UserServiceModel userServiceModel) {
        this.userServiceModel = userServiceModel;
    }

    public LocalDateTime getDateTime() {
        return this.dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<OrderProductServiceModel> getProducts() {
        return this.products;
    }

    public void setProducts(List<OrderProductServiceModel> products) {
        this.products = products;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
