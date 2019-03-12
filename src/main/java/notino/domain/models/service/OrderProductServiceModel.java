package notino.domain.models.service;

public class OrderProductServiceModel {

    private String id;
    private OrderServiceModel orderServiceModel;
    private ProductServiceModel productServiceModel;
    private Integer quantity;

    public OrderProductServiceModel() {
    }

    public OrderServiceModel getOrderServiceModel() {
        return this.orderServiceModel;
    }

    public void setOrderServiceModel(OrderServiceModel orderServiceModel) {
        this.orderServiceModel = orderServiceModel;
    }

    public ProductServiceModel getProductServiceModel() {
        return this.productServiceModel;
    }

    public void setProductServiceModel(ProductServiceModel productServiceModel) {
        this.productServiceModel = productServiceModel;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
