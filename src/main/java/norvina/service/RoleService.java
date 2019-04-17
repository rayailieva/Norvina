package norvina.service;

import norvina.domain.models.service.RoleServiceModel;

public interface RoleService {


    void seedRolesInDb();

    RoleServiceModel findByAuthority(String authority);
}
