package norvina.service;

import norvina.domain.entities.Brand;
import norvina.domain.models.service.BrandServiceModel;
import norvina.repository.BrandRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public BrandServiceImpl(BrandRepository brandRepository, ModelMapper modelMapper) {
        this.brandRepository = brandRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public BrandServiceModel addBrand(BrandServiceModel brandServiceModel) {
        Brand brand = this.modelMapper.map(brandServiceModel, Brand.class);

        this.brandRepository.saveAndFlush(brand);

        return this.modelMapper.map(brand, BrandServiceModel.class);
    }

    @Override
    public List<BrandServiceModel> findAllBrands() {
        return this.brandRepository.findAll()
                .stream()
                .map(b -> this.modelMapper.map(b, BrandServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public BrandServiceModel findBrandById(String id) {
        return this.brandRepository.findById(id)
                .map(p -> this.modelMapper.map(p, BrandServiceModel.class))
                .orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public BrandServiceModel findBrandByName(String name) {
        Brand brand = this.brandRepository.findByName(name).orElse(null);

        if(brand == null){
            throw new IllegalArgumentException("Brand name is null!");
        }

        return this.modelMapper.map(brand, BrandServiceModel.class);
    }

    @Override
    public BrandServiceModel editBrand(String id, BrandServiceModel brandServiceModel) {
        Brand brand = this.brandRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);

        brand.setName(brandServiceModel.getName());

        return this.modelMapper
                .map(this.brandRepository.saveAndFlush(brand), BrandServiceModel.class);
    }

    @Override
    public boolean deleteBrand(String id) {
        try {
            this.brandRepository.deleteById(id);

            return true;
        } catch (Exception e) {
            e.printStackTrace();

            return false;
        }
    }
}
