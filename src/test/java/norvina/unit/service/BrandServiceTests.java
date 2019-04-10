package norvina.unit.service;

import norvina.domain.entities.Brand;
import norvina.domain.models.service.BrandServiceModel;
import norvina.repository.BrandRepository;
import norvina.service.BrandServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.Silent.class)
@SpringBootTest
public class BrandServiceTests {

    @Mock
    private BrandRepository brandRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private BrandServiceImpl brandService;

    @Before
    public void initTests() {
        Mockito.when(modelMapper.map(eq(null), any()))
                .thenThrow(new IllegalArgumentException());
    }

    @Test
    public void saveBrand_withCorrectData_returnsCorrect() {
        Brand brand = mock(Brand.class);
        BrandServiceModel model = mock(BrandServiceModel.class);

        Mockito.when(modelMapper.map(model, Brand.class)).thenReturn(brand);
        Mockito.when(brandRepository.saveAndFlush(brand)).thenReturn(brand);
        Mockito.when(modelMapper.map(brand, BrandServiceModel.class)).thenReturn(model);

        BrandServiceModel result = brandService.addBrand(model);

        Mockito.verify(modelMapper).map(model, Brand.class);
        Mockito.verify(brandRepository).saveAndFlush(brand);
        Mockito.verify(modelMapper).map(brand, BrandServiceModel.class);

        Assert.assertEquals(model, result);
    }

    @Test(expected = Exception.class)
    public void saveBrand_withNullData_throwsException(){
        brandService.addBrand(null);
    }

    @Test
    public void editBrand_withCorrectData_returnsCorrect() {
        Brand brand = mock(Brand.class);
        BrandServiceModel model = mock(BrandServiceModel.class);

        Mockito.when(model.getId()).thenReturn("id");
        Mockito.when(model.getName()).thenReturn("name");
        Mockito.when(brandRepository.findById(eq("id"))).thenReturn(Optional.of(brand));
        Mockito.when(brandRepository.saveAndFlush(brand)).thenReturn(brand);
        Mockito.when(modelMapper.map(brand, BrandServiceModel.class)).thenReturn(model);

        BrandServiceModel result = brandService.editBrand(model.getId(), model);

        Mockito.verify(brandRepository).findById("id");
        Mockito.verify(brand).setName("name");
        Mockito.verify(brandRepository).saveAndFlush(brand);
        Mockito.verify(modelMapper).map(brand, BrandServiceModel.class);

        Assert.assertEquals(model, result);
    }

    @Test(expected = Exception.class)
    public void editBrand_withNullId_returnsCorrect() {
        Brand brand = mock(Brand.class);
        BrandServiceModel model = mock(BrandServiceModel.class);

        Mockito.when(model.getId()).thenReturn("id");
        Mockito.when(model.getName()).thenReturn("name");
        Mockito.when(brandRepository.findById(eq("id"))).thenReturn(Optional.of(brand));

        brandService.editBrand(null, model);
    }

    @Test(expected = Exception.class)
    public void editBrand_withNullModel_returnsCorrect() {
        Brand brand = mock(Brand.class);
        BrandServiceModel model = mock(BrandServiceModel.class);

        Mockito.when(model.getId()).thenReturn("id");
        Mockito.when(model.getName()).thenReturn("name");
        Mockito.when(brandRepository.findById(eq("id"))).thenReturn(Optional.of(brand));

        brandService.editBrand(model.getId(), null);
    }

    @Test
    public void deleteBrand_withCorrectData_returnsCorrect() {
        Brand brand = mock(Brand.class);
        BrandServiceModel model = mock(BrandServiceModel.class);
        Mockito.when(brandRepository.findById("id")).thenReturn(Optional.of(brand));
        Mockito.when(modelMapper.map(brand, BrandServiceModel.class)).thenReturn(model);

        BrandServiceModel result = brandService.deleteBrand("id");

        Mockito.verify(brandRepository).findById("id");
        Mockito.verify(brandRepository).delete(brand);
        Mockito.verify(modelMapper).map(brand, BrandServiceModel.class);
        Assert.assertEquals(model, result);
    }

    @Test(expected = Exception.class)
    public void deleteBrand_withNullData_returnsCorrect() {
        Brand brand = mock(Brand.class);
        BrandServiceModel model = mock(BrandServiceModel.class);
        Mockito.when(brandRepository.findById("id")).thenReturn(Optional.of(brand));
        Mockito.when(modelMapper.map(brand, BrandServiceModel.class)).thenReturn(model);

        brandService.deleteBrand(null);
    }

}
