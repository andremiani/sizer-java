package br.com.sizer.service;

// import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.sizer.dto.LoginUserDto;
import br.com.sizer.dto.RegisterUserDto;
// import br.com.sizer.model.Role;
// import br.com.sizer.model.RoleName;
import br.com.sizer.model.User;
// import br.com.sizer.repository.RoleRepository;
import br.com.sizer.repository.UserRepository;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;

    // private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
            UserRepository userRepository,
            // RoleRepository roleRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        // this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User signup(RegisterUserDto input) {

        // Optional<Role> optionalRole = roleRepository.findByName(RoleName.USER);

        // if (optionalRole.isEmpty()) {
        // return null;
        // }

        User user = new User();
        // user.setFullName(input.getFullName());
        user.setEmail(input.getEmail());
        user.setPassword(passwordEncoder.encode(input.getPassword()));
        // user.setRole(optionalRole.get());

        return userRepository.save(user);
    }

    public User authenticate(LoginUserDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()));

        return userRepository.findByUsername(input.getEmail())
                .orElseThrow();
    }
}