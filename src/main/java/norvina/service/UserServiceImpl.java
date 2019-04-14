package norvina.service;

import norvina.domain.entities.Role;
import norvina.domain.entities.User;
import norvina.domain.models.service.RoleServiceModel;
import norvina.domain.models.service.UserServiceModel;
import norvina.error.IdNotFoundException;
import norvina.repository.RoleRepository;
import norvina.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder encoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleService roleService, RoleRepository roleRepository, ModelMapper modelMapper, BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
        this.encoder = encoder;
    }

    @Override
    public UserServiceModel registerUser(UserServiceModel userServiceModel) {

        this.roleService.seedRolesInDb();

        User user = this.modelMapper.map(userServiceModel, User.class);
        user.setPassword(this.encoder.encode(user.getPassword()));

        if (this.userRepository.count() == 0) {
            user.setAuthorities(new HashSet<>());
            user.getAuthorities().add(this.roleRepository.findByAuthority("ROLE_ADMIN"));
            user.getAuthorities().add(this.roleRepository.findByAuthority("ROLE_MODERATOR"));
            user.getAuthorities().add(this.roleRepository.findByAuthority("ROLE_USER"));
        } else {
           user.setAuthorities(new HashSet<>());
            user.getAuthorities().add(this.roleRepository.findByAuthority("ROLE_USER"));
        }

        this.userRepository.save(user);

        return this.modelMapper.map(user, UserServiceModel.class);
    }


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return this.userRepository
                .findByUsername(s)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }


    @Override
    public UserServiceModel extractUserByEmail(String email) {
        User userEntity = this.userRepository.findByEmail(email).orElse(null);

        if (userEntity == null) {
            throw new IllegalArgumentException("User with this email does not exist.");
        }

        UserServiceModel userServiceModel = this.modelMapper.map(userEntity, UserServiceModel.class);
        userServiceModel.setEmail(userEntity.getEmail());

        return userServiceModel;
    }

    @Override
    public UserServiceModel findUserByUsername(String name) {
        return this.modelMapper.map(this.userRepository.findByUsername(name), UserServiceModel.class);
    }

    @Override
    public UserServiceModel findUserById(String id) {

        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException("User with the given id is not found"));

        return this.modelMapper.map(user, UserServiceModel.class);
    }

    @Override
    public List<UserServiceModel> findAllUsers() {
        return this.userRepository.findAll()
                .stream()
                .map(u -> this.modelMapper.map(u, UserServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserServiceModel editUser(UserServiceModel userServiceModel, String oldPassword) {
        User user = this.userRepository.findByUsername(userServiceModel.getUsername())
                .orElseThrow(()-> new UsernameNotFoundException("Username not found!"));

        if (!this.encoder.matches(oldPassword, user.getPassword())) {
            throw new IllegalArgumentException("Incorrect password!");
        }

        user.setPassword(!"".equals(userServiceModel.getPassword()) ?
                this.encoder.encode(userServiceModel.getPassword()) :
                user.getPassword());
        user.setEmail(userServiceModel.getEmail());

        this.userRepository.saveAndFlush(user);
        return this.modelMapper.map(user, UserServiceModel.class);
    }

    @Override
    public void setUserRole(String id, String role) {
        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException("User with the given id is not found"));

       // UserServiceModel userServiceModel = this.modelMapper.map(user, UserServiceModel.class);
       // userServiceModel.getAuthorities().clear();

        switch (role) {
            case "user":
                Role roleUser = this.roleRepository.findByAuthority("ROLE_USER");
                user.getAuthorities().add(roleUser);
                break;
            case "moderator":
                Role roleModerator = this.roleRepository.findByAuthority("ROLE_MODERATOR");
                user.getAuthorities().add(roleModerator);
                break;
            case "admin":
                Role roleAdmin = this.roleRepository.findByAuthority("ROLE_ADMIN");
                user.getAuthorities().add(roleAdmin);
                break;
        }

       // user = this.modelMapper.map(userServiceModel, User.class);
        this.userRepository.saveAndFlush(user);
    }
}
