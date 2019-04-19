package norvina.integration.web.controllers;

import norvina.domain.entities.Brand;
import norvina.error.BrandNotFoundException;
import norvina.repository.BrandRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

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
public class BrandControllerTests {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mvc;

    @Autowired
    BrandRepository mockBrandRepository;

    @Before
    public void setUp() {
        this.mockBrandRepository.deleteAll();

        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .defaultRequest(get("/")
                        .with(user("user").password("password").roles("MODERATOR")))
                .apply(springSecurity())
                .build();
    }

    @Test
    public void brands_addBrandReturnsCorrectView() throws Exception {
        this.mvc
                .perform(get("/brands/add"))
                .andExpect(view().name("brand/brand-add"));
    }

    @Test
    public void brands_addBrandCorrectly() throws Exception {
        this.mvc
                .perform(post("/brands/add")
                        .param("name", "loreal"));

       Brand brand = this.mockBrandRepository.findAll().get(0);
        assertEquals("loreal", brand.getName());
        Assert.assertEquals(1, this.mockBrandRepository.count());
    }

    @Test(expected = Exception.class)
    public void brands_addBrandWithNullValues_ThrowsException() throws Exception {
        this.mvc
                .perform(post("/brands/add")
                        .param("name", null));
    }

    @Test
    public void brands_editBrandReturnsCorrectView() throws Exception {
        Brand brand = new Brand();
        brand.setName("maybelline");
        brand = this.mockBrandRepository.saveAndFlush(brand);

        this.mvc
                .perform(get("/brands/edit/" + brand.getId()))
                .andExpect(view().name("brand/brand-edit"));
    }

    @Test
    public void brands_editBrandCorrectly() throws Exception {
       Brand brand = new Brand();
       brand.setName("mixa");
       brand =  this.mockBrandRepository.saveAndFlush(brand);

        this.mvc
                .perform(
                        post("/brands/edit/" + brand.getId())
                                .param("name", "mixa1")
                );

        Brand brandActual = this.mockBrandRepository.findAll().get(0);
        Assert.assertEquals("mixa1", brandActual.getName());
    }

    @Test(expected = Exception.class)
    public void brands_editBrandWithNullValue_ThrowsException() throws Exception {
        Brand brand = new Brand();
        brand.setName("7heaven");
        brand =  this.mockBrandRepository.saveAndFlush(brand);

        this.mvc
                .perform(
                        post("/brands/edit/" + brand.getId())
                                .param("name", null)
                );
    }

    @Test
    public void brands_deleteBrandCorrectly() throws Exception {
        Brand brand = new Brand();
        brand.setName("loreal2");
        brand = this.mockBrandRepository.saveAndFlush(brand);

        Brand brand1 = new Brand();
        brand1.setName("neutrogena");
        brand1 = this.mockBrandRepository.saveAndFlush(brand1);

        this.mvc
                .perform(
                        post("/brands/delete/" + brand1.getId())
                );

        Assert.assertEquals(1, this.mockBrandRepository.count());
        Assert.assertEquals("loreal2", this.mockBrandRepository.findAll().get(0).getName());
    }

    @Test
    public void brands_deleteBrandReturnsCorrectView() throws Exception {
        Brand brand = new Brand();
        brand.setName("loreal3");
        brand = this.mockBrandRepository.saveAndFlush(brand);

        this.mvc
                .perform(get("/brands/delete/" + brand.getId()))
                .andExpect(view().name("brand/brand-delete"));
    }

    @Test
    public void brands_allProductsByBrandReturnsCorrectView() throws Exception {
        Brand brand = new Brand();
        brand.setName("loreal4");
        brand = this.mockBrandRepository.saveAndFlush(brand);

        this.mvc
                .perform(get("/brands/product/" + brand.getId()))
                .andExpect(view().name("brand/brand-products"));
    }

    @Test
    public void brands_allBrandsReturnsCorrectView() throws Exception {

        this.mvc
                .perform(get("/brands/all-brands"))
                .andExpect(view().name("brand/brands-all"));
    }

    @Test(expected = Exception.class)
    public void brandNotFoundException_returnsCorrect() throws BrandNotFoundException {
        Brand brand = new Brand();
        brand.setName("test");
        Brand brand1 = this.mockBrandRepository.findById(brand.getId()).orElse(null);
        if (brand1 == null) {
            throw new BrandNotFoundException("Brand with the given id is not found!");
        }
    }
}
