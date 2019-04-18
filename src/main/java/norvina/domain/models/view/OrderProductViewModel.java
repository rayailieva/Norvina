package norvina.domain.models.view;

import java.math.BigDecimal;

public class OrderProductViewModel {

    private ProductViewModel productViewModel;
    private BigDecimal price;

    public OrderProductViewModel(){}


    public ProductViewModel getProductViewModel() {
        return this.productViewModel;
    }

    public void setProductViewModel(ProductViewModel productViewModel) {
        this.productViewModel = productViewModel;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
