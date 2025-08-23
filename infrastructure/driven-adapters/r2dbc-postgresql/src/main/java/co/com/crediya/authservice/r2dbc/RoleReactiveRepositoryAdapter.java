package co.com.crediya.authservice.r2dbc;

import co.com.crediya.authservice.model.role.Role;
import co.com.crediya.authservice.model.role.gateways.RoleRepository;
import co.com.crediya.authservice.r2dbc.entity.RoleEntity;
import co.com.crediya.authservice.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public class RoleReactiveRepositoryAdapter extends ReactiveAdapterOperations<
        Role,
        RoleEntity,
        UUID,
        RoleReactiveRepository
        > implements RoleRepository {

    public RoleReactiveRepositoryAdapter(RoleReactiveRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Role.class));
    }

    @Override
    public Mono<Role> save(Role role) {
        return super.save(role);
    }

    @Override
    public Mono<Role> findById(UUID roleId) {
        return repository.findById(roleId)
                .map(roleEntity -> mapper.map(roleEntity, Role.class));
    }

}
