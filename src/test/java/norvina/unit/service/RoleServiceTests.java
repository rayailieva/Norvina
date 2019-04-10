package norvina.unit.service;

import norvina.domain.entities.Role;
import norvina.repository.RoleRepository;
import norvina.service.RoleServiceImpl;
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
public class RoleServiceTests {

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private RoleServiceImpl roleService;

    @Before
    public void initTests() {
        Mockito.when(modelMapper.map(eq(null), any()))
                .thenThrow(new IllegalArgumentException());
    }

}
