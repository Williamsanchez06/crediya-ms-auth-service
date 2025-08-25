package co.com.crediya.authservice.usecase.role;


import co.com.crediya.authservice.model.role.Role;
import co.com.crediya.authservice.model.role.gateways.RoleRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RequiredArgsConstructor
public class RoleUseCase {

    private final RoleRepository roleRepository;

    public Mono<Role> saveRole(Role role) {
        return roleRepository.save(role);
    }

    public Mono<Role> getRoleById(UUID id) {
        return roleRepository.findById(id);
    }

}
