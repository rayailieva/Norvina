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

import java.util.List;
import java.util.stream.Collectors;

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

        User user = this.modelMapper.map(userServiceModel, User.class);
        user.setPassword(this.encoder.encode(userServiceModel.getPassword()));
        this.seedRolesInDb();

        if (this.userRepository.count() == 0) {
            user.getAuthorities().add(this.roleRepository.findByAuthority("ROLE_ADMIN"));
            user.getAuthorities().add(this.roleRepository.findByAuthority("ROLE_USER"));
        } else {
            user.getAuthorities().add(this.roleRepository.findByAuthority("ROLE_USER"));
        }

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
    public List<UserServiceModel> findAllUsers() {
        return this.userRepository.findAll()
                .stream()
                .map(u -> this.modelMapper.map(u, UserServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public boolean editUser(UserServiceModel userServiceModel) {
        User userEntity = this.userRepository.findByUsername(userServiceModel.getEmail()).orElse(null);

        if (userEntity == null) {
            throw new UsernameNotFoundException("Wrong or non-existent email.");
        }

        userEntity = this.modelMapper.map(userServiceModel, User.class);
        userEntity.setId(userServiceModel.getId());
        userEntity.setUsername(userServiceModel.getEmail());
        userEntity.setPassword(this.encoder.encode(userEntity.getPassword()));

        this.userRepository.save(userEntity);

        return true;
    }

    private void seedRolesInDb() {
        if (this.roleRepository.count() == 0) {
            Role userRole = new Role();
            userRole.setAuthority("ROLE_USER");

            Role adminRole = new Role();
            adminRole.setAuthority("ROLE_ADMIN");

            this.roleRepository.save(userRole);
            this.roleRepository.save(adminRole);

        }
    }
    }
