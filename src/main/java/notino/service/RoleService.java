package notino.service;

import notino.domain.models.service.RoleServiceModel;

import java.util.Set;

public interface RoleService {
    Set<RoleServiceModel> findAllRoles();

    RoleServiceModel findByAuthority(String authority);
}
