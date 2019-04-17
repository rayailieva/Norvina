package norvina.validation;

import norvina.domain.models.binding.ProductEditBindingModel;
import norvina.repository.ProductRepository;
import norvina.validation.annotation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;

@Validator
public class ProductEditValidator implements org.springframework.validation.Validator  {

    private final ProductRepository productRepository;

    @Autowired
    public ProductEditValidator(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return ProductEditBindingModel.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ProductEditBindingModel productEditBindingModel = (ProductEditBindingModel) o;

        if(productEditBindingModel.getName().length() < 2 ||
                productEditBindingModel.getName().length() > 50){
            errors.rejectValue("name", Constants.NAME_LENGTH, Constants.NAME_LENGTH);
        }


        if(productEditBindingModel.getDescription().length() < 5){
            errors.rejectValue("description", Constants.DESCRIPTION_LENGTH, Constants.DESCRIPTION_LENGTH);
        }

        if(productEditBindingModel.getPrice().intValueExact() < 1){
            errors.rejectValue("price", Constants.PRICE_VALUE, Constants.PRICE_VALUE);
        }

        if(productEditBindingModel.getImageUrl() == null){
            errors.rejectValue("imageUrl", Constants.IMAGE_NULL, Constants.IMAGE_NULL);
        }
    }
}
