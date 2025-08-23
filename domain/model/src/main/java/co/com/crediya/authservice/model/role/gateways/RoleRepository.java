package co.com.crediya.authservice.model.role.gateways;

import co.com.crediya.authservice.model.role.Role;
import reactor.core.publisher.Mono;

public interface RoleRepository {

    Mono<Role> save(Role user);

}
