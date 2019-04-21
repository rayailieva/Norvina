package norvina.integration.web.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
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
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class GlobalExceptionControllerTests {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mvc;

    @Before
    public void setUp() {

        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .defaultRequest(get("/")
                        .with(user("user").password("password").roles("USER")))
                .apply(springSecurity())
                .build();
    }

    @Test()
    public void unauthorized_allOrdersReturnsCorrectView() throws Exception {
        this.mvc
                .perform(get("/orders/all"))
                .andExpect(view().name("/unauthorized"));
    }

    @Test()
    public void unauthorized_allUsersReturnsCorrectView() throws Exception {
        this.mvc
                .perform(get("/all-users"))
                .andExpect(view().name("/unauthorized"));
    }


}

