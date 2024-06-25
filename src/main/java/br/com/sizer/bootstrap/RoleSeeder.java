package br.com.sizer.bootstrap;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import br.com.sizer.model.Role;
import br.com.sizer.model.RoleName;
import br.com.sizer.repository.RoleRepository;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class RoleSeeder implements ApplicationListener<ContextRefreshedEvent> {
    private final RoleRepository roleRepository;

    public RoleSeeder(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        this.loadRoles();
    }

    private void loadRoles() {
        RoleName[] roleNames = new RoleName[] { RoleName.ROLE_USER, RoleName.ROLE_ADMIN,
                RoleName.ROLE_SUPER_ADMIN };
        Map<RoleName, String> roleDescriptionMap = Map.of(
                RoleName.ROLE_USER, "Default user role",
                RoleName.ROLE_ADMIN, "Administrator role",
                RoleName.ROLE_SUPER_ADMIN, "Super Administrator role");

        Arrays.stream(roleNames).forEach((roleName) -> {

            Iterable<Role> rolesIterable = roleRepository.findAll();
            Set<Role> roles = StreamSupport.stream(rolesIterable.spliterator(), false)
                    .collect(Collectors.toSet());

            Optional<Role> optionalRole = roles.stream()
                    .filter(role -> role.getName().equals(roleName))
                    .findFirst();

            optionalRole.ifPresentOrElse(System.out::println, () -> {
                Role roleToCreate = new Role();

                roleToCreate.setName(roleName);
                roleToCreate.setDescription(roleDescriptionMap.get(roleName));

                roleRepository.save(roleToCreate);
            });
        });
    }
}