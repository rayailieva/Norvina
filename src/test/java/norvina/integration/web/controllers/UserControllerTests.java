package norvina.integration.web.controllers;

import norvina.domain.entities.User;
import norvina.repository.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.security.Principal;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserControllerTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserRepository mockUserRepository;


    @Test
    public void login_ReturnsCorrectView() throws Exception {
        this.mvc
                .perform(get("/login"))
                .andExpect(view().name("login"));
    }

    @Test
    public void register_ReturnsCorrectView() throws Exception {
        this.mvc
                .perform(get("/register"))
                .andExpect(view().name("register"));
    }

    @Test
    public void users_registersUserCorrectly() throws Exception {
        this.mvc
                .perform(post("/register")
                        .param("username", "pesho")
                        .param("firstName", "Pesho")
                        .param("lastName", "Peshov")
                        .param("email", "p@pesho.pesho")
                        .param("password", "123456")
                        .param("confirmPassword", "123456"));

        Assert.assertEquals(1, this.mockUserRepository.count());
    }


}
