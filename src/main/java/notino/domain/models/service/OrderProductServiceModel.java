package notino.domain.models.service;

public class OrderProductServiceModel {

    private String id;
    private OrderServiceModel order;
    private ProductServiceModel product;
    private Integer quantity;

    public OrderProductServiceModel() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public OrderServiceModel getOrder() {
        return this.order;
    }

    public void setOrder(OrderServiceModel order) {
        this.order = order;
    }

    public ProductServiceModel getProduct() {
        return this.product;
    }

    public void setProduct(ProductServiceModel product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
