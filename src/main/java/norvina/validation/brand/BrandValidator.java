package norvina.validation.brand;

import norvina.domain.models.binding.BrandBindingModel;
import norvina.repository.BrandRepository;
import norvina.validation.Constants;
import norvina.validation.annotation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;

@Validator
public class BrandValidator implements org.springframework.validation.Validator{

    private final BrandRepository brandRepository;

    @Autowired
    public BrandValidator(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return BrandBindingModel.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        BrandBindingModel brandBindingModel = (BrandBindingModel) o;

        if(brandBindingModel.getName().length() < 2 ||
                brandBindingModel.getName().length() > 15){
            errors.rejectValue("name", Constants.NAME_LENGTH, Constants.NAME_LENGTH);
        }

        if(this.brandRepository.findByName(brandBindingModel.getName()).isPresent()){
            errors.rejectValue(
                    "name",
                    String.format(Constants.NAME_ALREADY_EXISTS, "Brand", brandBindingModel.getName()),
                    String.format(Constants.NAME_ALREADY_EXISTS, "Brand", brandBindingModel.getName())
            );
        }
    }
}
