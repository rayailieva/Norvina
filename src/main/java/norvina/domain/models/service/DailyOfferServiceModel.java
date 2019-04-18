package norvina.domain.models.service;

import java.math.BigDecimal;

public class DailyOfferServiceModel {

    private String id;
    private ProductServiceModel productServiceModel;
    private BigDecimal price;

    public DailyOfferServiceModel(){}


    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ProductServiceModel getProductServiceModel() {
        return this.productServiceModel;
    }

    public void setProductServiceModel(ProductServiceModel productServiceModel) {
        this.productServiceModel = productServiceModel;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
