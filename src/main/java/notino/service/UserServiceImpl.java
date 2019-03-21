package notino.service;

import notino.domain.entities.Role;
import notino.domain.entities.User;
import notino.domain.models.service.UserServiceModel;
import notino.repository.RoleRepository;
import notino.repository.UserRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder encoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, ModelMapper modelMapper, BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
        this.encoder = encoder;
    }


    @Override
    public boolean registerUser(UserServiceModel userServiceModel) {
        this.seedRolesInDb();

        User user = this.modelMapper.map(userServiceModel, User.class);
        user.setPassword(this.encoder.encode(userServiceModel.getPassword()));
        this.giveRolesToUser(user);

        try{
            this.userRepository.saveAndFlush(user);

            return true;
        }catch (Exception e){
            e.printStackTrace();

            return false;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return this.userRepository
                .findByUsername(s)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }



    private void seedRolesInDb() {
        if (this.roleRepository.count() == 0) {
            Role userRole = new Role();
            userRole.setAuthority("ROLE_USER");

            Role adminRole = new Role();
            adminRole.setAuthority("ADMIN_USER");

            this.roleRepository.save(userRole);
            this.roleRepository.save(adminRole);

        }
    }

    private void giveRolesToUser(User user){
        if(this.userRepository.count() == 0){
            user.getAuthorities().add(this.roleRepository.findByAuthority("ADMIN"));
            user.getAuthorities().add(this.roleRepository.findByAuthority("USER"));
        }else {
            user.getAuthorities().add(this.roleRepository.findByAuthority("USER"));
        }
    }


}
