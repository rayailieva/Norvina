package norvina.service;

import norvina.domain.models.service.BrandServiceModel;

import java.util.List;

public interface BrandService {

    BrandServiceModel addBrand(BrandServiceModel brandServiceModel);

    List<BrandServiceModel> findAllBrands();

    BrandServiceModel findBrandById(String id);

    BrandServiceModel findBrandByName(String name);

    BrandServiceModel editBrand(String id, BrandServiceModel brandServiceModel);

    boolean deleteBrand(String id);


}
