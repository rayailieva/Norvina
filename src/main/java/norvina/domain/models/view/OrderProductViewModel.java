package norvina.domain.models.view;

import java.math.BigDecimal;

public class OrderProductViewModel {

    private ProductViewModel product;
    private BigDecimal price;

    public OrderProductViewModel() {
    }

    public ProductViewModel getProduct() {
        return product;
    }

    public void setProduct(ProductViewModel product) {
        this.product = product;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
