package norvina.unit.service;

import norvina.domain.entities.Brand;
import norvina.domain.entities.Product;
import norvina.domain.models.service.BrandServiceModel;
import norvina.domain.models.service.ProductServiceModel;
import norvina.repository.BrandRepository;
import norvina.repository.ProductRepository;
import norvina.service.BrandServiceImpl;
import norvina.service.ProductServiceImpl;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.Silent.class)
@SpringBootTest
public class ProductServiceTests {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private BrandRepository brandRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ProductServiceImpl productService;


    @Before
    public void initTests() {
        Mockito.when(modelMapper.map(eq(null), any()))
                .thenThrow(new IllegalArgumentException());

        Brand brand = mock(Brand.class);
        BrandServiceModel brandServiceModel = mock(BrandServiceModel.class);
        Mockito.when(modelMapper.map(brandServiceModel, Brand.class)).thenReturn(brand);
        Mockito.when(brandRepository.saveAndFlush(brand)).thenReturn(brand);
        Mockito.when(modelMapper.map(brand, BrandServiceModel.class)).thenReturn(brandServiceModel);
    }

    @Test
    public void saveProduct_withCorrectData_returnsCorrect(){

        Product product = mock(Product.class);
        ProductServiceModel model = mock(ProductServiceModel.class);
        Mockito.when(modelMapper.map(model, Product.class)).thenReturn(product);
        Mockito.when(productRepository.saveAndFlush(product)).thenReturn(product);
        Mockito.when(modelMapper.map(product, ProductServiceModel.class)).thenReturn(model);

        ProductServiceModel result = productService.addProduct(model);

        Mockito.verify(modelMapper).map(model, Product.class);
        Mockito.verify(productRepository).saveAndFlush(product);
        Mockito.verify(modelMapper).map(product, ProductServiceModel.class);
        Assert.assertEquals(model, result);
    }
}
