package norvina.service;

import norvina.domain.models.service.RoleServiceModel;

public interface RoleService {
   // void seedRolesInDb();

   // Set<RoleServiceModel> findAllRoles();

    void seedRolesInDb();

    RoleServiceModel findByAuthority(String authority);
}
