package norvina.validation;

import norvina.domain.models.binding.ProductCreateBindingModel;
import norvina.repository.ProductRepository;
import norvina.validation.Constants;
import norvina.validation.annotation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;


@Validator
public class ProductCreateValidator implements org.springframework.validation.Validator {

    private final ProductRepository productRepository;

    @Autowired
    public ProductCreateValidator(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return ProductCreateBindingModel.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ProductCreateBindingModel productCreateBindingModel = (ProductCreateBindingModel) o;

        if(productCreateBindingModel.getName().length() < 2 ||
                productCreateBindingModel.getName().length() > 50){
            errors.rejectValue("name", Constants.NAME_LENGTH, Constants.NAME_LENGTH);
        }

        if(this.productRepository.findByName(productCreateBindingModel.getName()).isPresent()){
            errors.rejectValue(
                    "name",
                    String.format(Constants.NAME_ALREADY_EXISTS, "Product", productCreateBindingModel.getName()),
                    String.format(Constants.NAME_ALREADY_EXISTS, "Product", productCreateBindingModel.getName())
            );
        }

        if(productCreateBindingModel.getDescription().length() < 5){
            errors.rejectValue("description", Constants.DESCRIPTION_LENGTH, Constants.DESCRIPTION_LENGTH);
        }

        if(productCreateBindingModel.getPrice().intValueExact() < 1){
            errors.rejectValue("price", Constants.PRICE_VALUE, Constants.PRICE_VALUE);
        }

        if(productCreateBindingModel.getImageUrl() == null){
            errors.rejectValue("imageUrl", Constants.IMAGE_NULL, Constants.IMAGE_NULL);
        }
    }
}
