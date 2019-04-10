package norvina.service;

import norvina.domain.entities.Brand;
import norvina.domain.models.service.BrandServiceModel;
import norvina.error.BrandNotFoundException;
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
                .orElseThrow(() -> new BrandNotFoundException("Brand with the given id is not found!"));
    }

    @Override
    public BrandServiceModel findBrandByName(String name) {
        Brand brand = this.brandRepository.findByName(name).orElse(null);

        if (brand == null) {
            throw new BrandNotFoundException("Brand with the given name is not found!");
        }

        return this.modelMapper.map(brand, BrandServiceModel.class);
    }

    @Override
    public BrandServiceModel editBrand(String id, BrandServiceModel brandServiceModel) {
        Brand brand = this.brandRepository.findById(id)
                .orElseThrow(() -> new BrandNotFoundException("Brand with the given id is not found!"));

        brand.setName(brandServiceModel.getName());

        this.brandRepository.saveAndFlush(brand);

        return this.modelMapper.map(brand, BrandServiceModel.class);
    }

    @Override
    public BrandServiceModel deleteBrand(String id) {
        Brand brand = this.brandRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);

        this.brandRepository.delete(brand);

        return this.modelMapper.map(brand, BrandServiceModel.class);

    }
}
