package co.com.crediya.authservice.usecase.role;

import co.com.crediya.authservice.model.role.Role;
import co.com.crediya.authservice.model.role.gateways.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.mockito.Mockito.*;

class RoleUseCaseTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleUseCase roleUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Obtener rol por ID exitosamente")
    void getRoleByIdSuccessfully() {
        UUID roleId = UUID.randomUUID();
        Role role = new Role(roleId, "Admin", "prueba rol");
        when(roleRepository.findById(roleId)).thenReturn(Mono.just(role));

        StepVerifier.create(roleUseCase.getRoleById(roleId))
                .expectNext(role)
                .verifyComplete();

        verify(roleRepository).findById(roleId);
    }

    @Test
    @DisplayName("Devolver vacío cuando el ID del rol no existe")
    void returnEmptyWhenRoleIdDoesNotExist() {
        UUID roleId = UUID.randomUUID();
        when(roleRepository.findById(roleId)).thenReturn(Mono.empty());

        StepVerifier.create(roleUseCase.getRoleById(roleId))
                .verifyComplete();

        verify(roleRepository).findById(roleId);
    }
}
