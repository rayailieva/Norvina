package norvina.domain.models.view;

import java.io.Serializable;

public class ShoppingCartItem implements Serializable {

    private String id;
    private ProductViewModel product;
    private int quantity;

    public ShoppingCartItem() {
    }

    public ProductViewModel getProduct() {
        return product;
    }

    public void setProduct(ProductViewModel product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
