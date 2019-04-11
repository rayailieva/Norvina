package norvina.integration.web.controllers;

import norvina.domain.entities.Order;
import norvina.repository.OrderRepository;
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

import java.time.LocalDateTime;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc(secure = false)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class AdminControllerTests {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mvc;

    @Autowired
    OrderRepository mockOrderRepository;

    @Before
    public void setUp() {
        this.mockOrderRepository.deleteAll();

        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .defaultRequest(get("/")
                        .with(user("user").password("password").roles("ADMIN")))
                .apply(springSecurity())
                .build();
    }

    @Test
    public void orders_allOrderReturnsCorrectView() throws Exception {
        this.mvc
                .perform(get("/orders/all"))
                .andExpect(view().name("order/all-orders"));
    }

    @Test
    public void orders_allOrdersDetailsReturnsCorrectView() throws Exception {

        Order order = new Order();
        order.setDate(LocalDateTime.now());

        order = this.mockOrderRepository.saveAndFlush(order);

        this.mvc
                .perform(get("/orders/all/details/" + order.getId()))
                .andExpect(view().name("order/order-details"));
    }

}
