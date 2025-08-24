package co.com.crediya.authservice.r2dbc;

import co.com.crediya.authservice.model.role.Role;
import co.com.crediya.authservice.model.user.User;
import co.com.crediya.authservice.r2dbc.entity.UserEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivecommons.utils.ObjectMapper;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserReactiveRepositoryAdapterTest {

    @InjectMocks
    UserReactiveRepositoryAdapter repositoryAdapter;

    @Mock
    UserReactiveRepository repository;

    @Mock
    ObjectMapper mapper;

    @Test
    @DisplayName("guardar usuario exitosamente con rol")
    void saveUserSuccessfullyWithRole() {

        User user = new User();
        user.setEmail("williamsanchez@crediya.com");
        user.setFirstName("William");
        user.setLastName("Sanchez");
        user.setDocumentNumber("123456789");
        user.setPhone("3001234567");
        user.setRole(null);
        user.setBaseSalary(BigDecimal.valueOf(100.0));

        UUID roleId = UUID.randomUUID();
        user.setRole(new Role(roleId, "Admin", "rol prueba"));
        UserEntity entity = new UserEntity();
        entity.setEmail(user.getEmail());
        entity.setFirstName(user.getFirstName());
        entity.setLastName(user.getLastName());
        entity.setDocumentNumber(user.getDocumentNumber());
        entity.setPhone(user.getPhone());
        entity.setBaseSalary(user.getBaseSalary());
        entity.setRoleId(user.getRole().getId());

        when(mapper.map(user, UserEntity.class)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(Mono.just(entity));
        when(mapper.map(entity, User.class)).thenReturn(user);

        StepVerifier.create(repositoryAdapter.save(user))
                .expectNext(user)
                .verifyComplete();

        verify(repository).save(entity);
        verify(mapper).map(user, UserEntity.class);
        verify(mapper).map(entity, User.class);

    }

    @Test
    @DisplayName("Buscar usuario por email exitosamente")
    void findUserByEmailSuccessfully() {
        String email = "test@example.com";
        UserEntity entity = new UserEntity();
        entity.setEmail(email);
        User user = new User();
        user.setEmail("williamsanchez@crediya.com");
        user.setFirstName("William");
        user.setLastName("Sanchez");
        user.setDocumentNumber("123456789");
        user.setPhone("3001234567");
        user.setRole(null);
        user.setBaseSalary(BigDecimal.valueOf(100.0));

        when(repository.findByEmail(email)).thenReturn(Mono.just(entity));
        when(mapper.map(entity, User.class)).thenReturn(user);

        StepVerifier.create(repositoryAdapter.findByEmail(email))
                .expectNext(user)
                .verifyComplete();

        verify(repository).findByEmail(email);
        verify(mapper).map(entity, User.class);
    }

    @Test
    @DisplayName("Devolver vacío cuando el email del usuario no existe")
    void returnEmptyWhenUserEmailDoesNotExist() {
        String email = "nonexistent@example.com";

        when(repository.findByEmail(email)).thenReturn(Mono.empty());

        StepVerifier.create(repositoryAdapter.findByEmail(email))
                .verifyComplete();

        verify(repository).findByEmail(email);
        verifyNoInteractions(mapper);
    }

}
