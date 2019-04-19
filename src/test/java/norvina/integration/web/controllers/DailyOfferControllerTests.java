package norvina.integration.web.controllers;

import norvina.domain.entities.Brand;
import norvina.domain.entities.Category;
import norvina.domain.entities.DailyOffer;
import norvina.domain.entities.Product;
import norvina.repository.BrandRepository;
import norvina.repository.DailyOfferRepository;
import norvina.repository.OrderRepository;
import norvina.repository.ProductRepository;
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

import java.math.BigDecimal;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc(secure = false)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class DailyOfferControllerTests {

   @Autowired
   private WebApplicationContext context;

    @Autowired
    private MockMvc mvc;

    @Autowired
    DailyOfferRepository mockDailyOfferRepository;

    @Autowired
    ProductRepository mockProductRepository;

    @Autowired
    BrandRepository mockBrandRepository;


    @Before
    public void setUp() {
        this.mockDailyOfferRepository.deleteAll();

        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .defaultRequest(get("/")
                        .with(user("user").password("password").roles("ADMIN")))
                .apply(springSecurity())
                .build();
    }

    @Test
    public void dailyOffer_ReturnsCorrectView() throws Exception {
        Brand brand = new Brand();
        brand.setName("mixa");

        brand = this.mockBrandRepository.saveAndFlush(brand);

        Product product = new Product();
        product.setName("sunscreen");
        product.setDescription("spf 100");
        product.setPrice(BigDecimal.TEN);
        product.setCategory(Category.Body);
        product.setBrand(brand);

        DailyOffer dailyOffer = new DailyOffer();
        dailyOffer.setProduct(product);
        dailyOffer.setPrice(product.getPrice().multiply(new BigDecimal(0.5)));
        this.mockDailyOfferRepository.saveAndFlush(dailyOffer);


        this.mvc
                .perform(get("/daily-offers"))
                .andExpect(view().name("offer/daily-offers"));
    }

}
