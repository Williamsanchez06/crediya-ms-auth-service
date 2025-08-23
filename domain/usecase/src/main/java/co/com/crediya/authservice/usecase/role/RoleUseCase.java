package co.com.crediya.authservice.usecase.role;


import co.com.crediya.authservice.model.role.Role;
import co.com.crediya.authservice.model.role.gateways.RoleRepository;
import reactor.core.publisher.Mono;

public class RoleUseCase {

    private final RoleRepository roleRepository;

    public RoleUseCase(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Mono<Role> saveRole(Role role) {
        return roleRepository.save(role);
    }

}
