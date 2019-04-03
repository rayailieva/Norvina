package notino.service;

import notino.domain.entities.Role;
import notino.domain.models.service.RoleServiceModel;
import notino.repository.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository, ModelMapper modelMapper) {
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }
/*
     @Override
     public void seedRolesInDb() {
         if (this.roleRepository.count() == 0) {
             this.roleRepository.saveAndFlush(new Role("ROLE_USER"));
             this.roleRepository.saveAndFlush(new Role("ROLE_ADMIN"));
         }
     }

    @Override
    public Set<RoleServiceModel> findAllRoles() {
        return this.roleRepository.findAll()
                .stream()
                .map(r -> this.modelMapper.map(r, RoleServiceModel.class))
                .collect(Collectors.toSet());
    }
    */

    @Override
    public RoleServiceModel findByAuthority(String authority) {
        Role role = this.roleRepository.findByAuthority(authority);
        return this.modelMapper.map(role, RoleServiceModel.class);
    }
}
