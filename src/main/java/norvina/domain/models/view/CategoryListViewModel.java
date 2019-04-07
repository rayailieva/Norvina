package norvina.domain.models.view;

public class CategoryListViewModel {

    private String id;
    private String name;

    public CategoryListViewModel() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
