package co.com.crediya.authservice.r2dbc;

import co.com.crediya.authservice.model.role.Role;
import co.com.crediya.authservice.r2dbc.entity.RoleEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivecommons.utils.ObjectMapper;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleReactiveRepositoryAdapterTest {

    @InjectMocks
    RoleReactiveRepositoryAdapter repositoryAdapter;

    @Mock
    RoleReactiveRepository repository;

    @Mock
    ObjectMapper mapper;

    @Test
    @DisplayName("buscar rol por ID exitosamente")
    void findRoleByIdSuccessfully() {
        UUID roleId = UUID.randomUUID();
        RoleEntity entity = new RoleEntity();
        entity.setId(roleId);
        entity.setName("Admin");
        entity.setDescription("rol prueba");

        Role role = new Role();
        role.setId(roleId);
        role.setName("Admin");
        role.setDescription("rol prueba");

        when(repository.findById(roleId)).thenReturn(Mono.just(entity));
        when(mapper.map(entity, Role.class)).thenReturn(role);

        StepVerifier.create(repositoryAdapter.findById(roleId))
                .expectNext(role)
                .verifyComplete();

        verify(repository).findById(roleId);
        verify(mapper).map(entity, Role.class);
    }

    @Test
    @DisplayName("buscar rol por ID retorna vacío cuando no se encuentra")
    void findRoleByIdReturnsEmptyWhenNotFound() {
        UUID roleId = UUID.randomUUID();

        when(repository.findById(roleId)).thenReturn(Mono.empty());

        StepVerifier.create(repositoryAdapter.findById(roleId))
                .verifyComplete();

        verify(repository).findById(roleId);
        verifyNoInteractions(mapper);
    }

    @Test
    @DisplayName("buscar rol por ID lanza excepción cuando el repositorio falla")
    void findRoleByIdThrowsExceptionWhenRepositoryFails() {
        UUID roleId = UUID.randomUUID();

        when(repository.findById(roleId)).thenReturn(Mono.error(new RuntimeException("Database error")));

        StepVerifier.create(repositoryAdapter.findById(roleId))
                .expectError(RuntimeException.class)
                .verify();

        verify(repository).findById(roleId);
        verifyNoInteractions(mapper);
    }

}
