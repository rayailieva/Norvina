package norvina.domain.models.view;

import java.math.BigDecimal;

public class DailyOfferViewModel {

    private String id;
    private ProductViewModel productViewModel;
    private BigDecimal price;

    public DailyOfferViewModel(){}


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

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
