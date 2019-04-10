package norvina.integration.web.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc(secure = false)
public class CategoryControllerTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @Before
    public void setUp() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .defaultRequest(get("/")
                        .with(user("user").password("password").roles("ADMIN")))
                .apply(springSecurity())
                .build();
    }

    @Test
    public void makeup_ReturnsCorrectView() throws Exception {
        this.mvc
                .perform(get("/category/makeup"))
                .andExpect(view().name("category/product-by-category"));
    }

    @Test
    public void hair_ReturnsCorrectView() throws Exception {
        this.mvc
                .perform(get("/category/hair"))
                .andExpect(view().name("category/product-by-category"));
    }

    @Test
    public void sun_ReturnsCorrectView() throws Exception {
        this.mvc
                .perform(get("/category/sun"))
                .andExpect(view().name("category/product-by-category"));
    }

    @Test
    public void skin_ReturnsCorrectView() throws Exception {
        this.mvc
                .perform(get("/category/skin"))
                .andExpect(view().name("category/product-by-category"));
    }

    @Test
    public void fragrances_ReturnsCorrectView() throws Exception {
        this.mvc
                .perform(get("/category/fragrances"))
                .andExpect(view().name("category/product-by-category"));
    }

    @Test
    public void body_ReturnsCorrectView() throws Exception {
        this.mvc
                .perform(get("/category/body"))
                .andExpect(view().name("category/product-by-category"));
    }
}
