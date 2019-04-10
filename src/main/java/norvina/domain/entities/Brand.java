package norvina.domain.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "brands")
public class Brand extends BaseEntity {

    private String name;
    private List<Product> products;

    public Brand(){}

    @Column(name = "name", nullable = false)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "brand", fetch = FetchType.EAGER)
    //@JoinTable(name = "products_brands",
    //        joinColumns = @JoinColumn(name = "brand_id", referencedColumnName = "id"),
    //        inverseJoinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"))
    public List<Product> getProducts() {
        return this.products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
