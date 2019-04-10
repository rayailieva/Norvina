package norvina.integration.web.controllers;

import norvina.domain.entities.Brand;
import norvina.domain.entities.Category;
import norvina.domain.entities.Product;
import norvina.repository.BrandRepository;
import norvina.repository.ProductRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc(secure = false)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ProductControllerTests {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mvc;

    @Autowired
    ProductRepository mockProductRepository;

    @Autowired
    BrandRepository mockBrandRepository;

    @Before
    public void setUp() {
        this.mockProductRepository.deleteAll();
        this.mockBrandRepository.deleteAll();

        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .defaultRequest(get("/")
                        .with(user("user").password("password").roles("ADMIN")))
                .apply(springSecurity())
                .build();
            }

   @Test
   public void products_addProductReturnsCorrectView() throws Exception {
       this.mvc
               .perform(get("/products/add"))
               .andExpect(view().name("product/product-add"));
   }

    @Test
    public void products_addProductCorrectly() throws Exception {

        Brand brand = new Brand();
        brand.setName("maybelline");

        this.mockBrandRepository.saveAndFlush(brand);

        this.mvc
                .perform(post("/products/add")
                        .param("name", "sunscreen")
                                .param("description", "spf 100")
                                .param("imageUrl", "sunscreen")
                                .param("price", "10")
                                .param("category", "Sun")
                                .param("brand", "maybelline"));

        Product product = this.mockProductRepository.findAll().get(0);
        Assert.assertEquals("sunscreen", product.getName());
        Assert.assertEquals("spf 100", product.getDescription());
        Assert.assertEquals(1, this.mockProductRepository.count());
    }

    @Test
    public void products_editViewReturnsCorrectView() throws Exception {
        this.getBrandEntity();

        Product product = new Product();
        product.setName("sunscreen");
        product.setDescription("spf 100");
        product.setImageUrl("lalalal.com");
        product.setPrice(BigDecimal.TEN);
        product.setCategory(Category.Body);
        product.setBrand(getBrandEntity());

        product = this.mockProductRepository.saveAndFlush(product);

        this.mvc
                .perform(get("/products/edit/" + product.getId()))
                .andExpect(view().name("product/product-edit"));
    }

    @Test
    public void products_editProductCorrectly() throws Exception {

        this.getBrandEntity();

        Product product = new Product();
        product.setName("sunscreen");
        product.setDescription("spf 100");
        product.setImageUrl("lalalal.com");
        product.setPrice(BigDecimal.TEN);
        product.setCategory(Category.Body);
        product.setBrand(getBrandEntity());

        product = this.mockProductRepository.saveAndFlush(product);

        this.mvc
                .perform(
                        post("/products/edit/" + product.getId())
                        .param("name", "skin mist")
                        .param("description", "spf 100")
                        .param("imageUrl", "lalalal.bg")
                                .param("price", product.getPrice().toString())
                                .param("category", Category.Body.toString())
                                .param("brand", product.getBrand().toString())
                );

        Product  product1 = this.mockProductRepository.findAll().get(0);
        Assert.assertEquals("skin mist", product1.getName());
    }

    @Test
    public void products_detailsViewReturnsCorrectView() throws Exception {
        this.getBrandEntity();

        Product product = new Product();
        product.setName("sunscreen");
        product.setDescription("spf 100");
        product.setPrice(BigDecimal.TEN);
        product.setCategory(Category.Body);
        product.setBrand(getBrandEntity());

        product = this.mockProductRepository.saveAndFlush(product);


        this.mvc
                .perform(get("/products/details/" + product.getId()))
                .andExpect(view().name("product/product-details"));
    }


    @Test
    public void products_deleteViewReturnsCorrectView() throws Exception {
        this.getBrandEntity();

        Product product = new Product();
        product.setName("sunscreen");
        product.setDescription("spf 100");
        product.setImageUrl("lalalal.com");
        product.setPrice(BigDecimal.TEN);
        product.setCategory(Category.Body);
        product.setBrand(getBrandEntity());

        product = this.mockProductRepository.saveAndFlush(product);

        this.mvc
                .perform(get("/products/delete/" + product.getId()))
                .andExpect(view().name("product/product-delete"));
    }

    @Test
    public void products_deleteProductCorrectly() throws Exception {

        this.getBrandEntity();

        Product product = new Product();
        product.setName("sunscreen");
        product.setDescription("spf 100");
        product.setPrice(BigDecimal.TEN);
        product.setCategory(Category.Body);
        product.setBrand(getBrandEntity());

        product = this.mockProductRepository.saveAndFlush(product);

        this.mvc
                .perform(
                        post("/products/delete/" + product.getId())
                );

       Assert.assertEquals(0, this.mockProductRepository.count());
    }

    @Test
    public void products_allProductsReturnsCorrectView() throws Exception {
        this.mvc
                .perform(get("/products/all-products"))
                .andExpect(view().name("product/all-products"));
    }


    private Brand getBrandEntity() {
        Brand brand = new Brand();
        brand.setName("maybelline");

        brand = this.mockBrandRepository.saveAndFlush(brand);
        return brand;
    }
}


