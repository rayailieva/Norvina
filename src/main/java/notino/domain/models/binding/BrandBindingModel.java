package notino.domain.models.binding;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

public class BrandBindingModel {

    private String id;
    private String name;

    public BrandBindingModel() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @NotNull(message = "Username cannot be null!")
    @Length(min = 2, max = 15, message = "Name must be at least 2 symbols long.")
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
