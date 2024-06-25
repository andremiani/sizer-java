// package br.com.sizer.bootstrap;

// import org.springframework.context.ApplicationListener;
// import org.springframework.context.event.ContextRefreshedEvent;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.stereotype.Component;

// import br.com.sizer.dto.RegisterUserDto;
// import br.com.sizer.model.Role;
// import br.com.sizer.model.RoleName;
// import br.com.sizer.model.User;
// import br.com.sizer.repository.RoleRepository;
// import br.com.sizer.repository.UserRepository;

// import java.util.Optional;

// @Component
// public class AdminSeeder implements
// ApplicationListener<ContextRefreshedEvent> {
// private final RoleRepository roleRepository;
// private final UserRepository userRepository;

// private final PasswordEncoder passwordEncoder;

// public AdminSeeder(
// RoleRepository roleRepository,
// UserRepository userRepository,
// PasswordEncoder passwordEncoder) {
// this.roleRepository = roleRepository;
// this.userRepository = userRepository;
// this.passwordEncoder = passwordEncoder;
// }

// @Override
// public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
// this.createSuperAdministrator();
// }

// private void createSuperAdministrator() {
// RegisterUserDto userDto = new RegisterUserDto();
// userDto.setFullName("Super Admin");
// userDto.setEmail("super.admin@email.com");
// userDto.setPassword("12345678");

// Optional<Role> optionalRole =
// roleRepository.findByName(RoleName.SUPER_ADMIN);
// Optional<User> optionalUser = userRepository.findByEmail(userDto.getEmail());

// if (optionalRole.isEmpty() || optionalUser.isPresent()) {
// return;
// }

// var user = new User();

// user.setFullName(userDto.getFullName());
// user.setEmail(userDto.getEmail());
// user.setPassword(passwordEncoder.encode(userDto.getPassword()));
// user.setRole(optionalRole.get());

// userRepository.save(user);
// }
// }