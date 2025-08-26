package co.com.crediya.authservice.usecase.user;


import co.com.crediya.authservice.model.exception.BusinessException;
import co.com.crediya.authservice.model.user.User;
import co.com.crediya.authservice.model.user.gateways.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserUseCase userUseCase;

    @Test
    @DisplayName("guardar usuario exitosamente cuando el email no está registrado y el numero de documento no está registrado")
    void saveUserSuccessfullyWhenEmailNotRegistered() {
        User user = new User();
        user.setEmail("williamsanchez@crediya.com");
        user.setFirstName("William");
        user.setLastName("Sanchez");
        user.setDocumentNumber("123456789");
        user.setPhone("3001234567");
        user.setRole(null);
        user.setBaseSalary(BigDecimal.valueOf(100.0));
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Mono.empty());
        when(userRepository.existsByDocumentNumber(user.getDocumentNumber())).thenReturn(Mono.just(false));
        when(userRepository.save(user)).thenReturn(Mono.just(user));

        StepVerifier.create(userUseCase.saveUser(user))
                .expectNext(user)
                .verifyComplete();

        verify(userRepository).findByEmail(user.getEmail());
        verify(userRepository).save(user);
    }

    @Test
    @DisplayName("Lanzar exepcion cuando el email ya está registrado")
    void throwExceptionWhenEmailAlreadyRegistered() {
        User user = new User();
        user.setEmail("williamsanchez@crediya.com");
        user.setFirstName("William 2");
        user.setLastName("Sanchez 2");
        user.setDocumentNumber("1234567891");
        user.setPhone("3001234567");
        user.setRole(null);
        user.setBaseSalary(BigDecimal.valueOf(100.0));
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Mono.just(user));
        when(userRepository.existsByDocumentNumber(user.getDocumentNumber())).thenReturn(Mono.just(false));

        StepVerifier.create(userUseCase.saveUser(user))
                .expectErrorMatches(throwable -> throwable instanceof BusinessException &&
                            throwable.getMessage().equals("El correo ya está registrado: " + user.getEmail()))
                .verify();

        verify(userRepository).findByEmail(user.getEmail());
        verify(userRepository, never()).save(user);
    }

    @Test
    @DisplayName("Lanzar exepcion cuando el documento ya está registrado")
    void throwExceptionWhenDocumentAlreadyRegistered() {
        User user = new User();
        user.setEmail("newuser@crediya.com");
        user.setFirstName("New");
        user.setLastName("User");
        user.setDocumentNumber("987654321");
        user.setPhone("3009876543");
        user.setRole(null);
        user.setBaseSalary(BigDecimal.valueOf(200.0));
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Mono.empty());
        when(userRepository.existsByDocumentNumber(user.getDocumentNumber())).thenReturn(Mono.just(true));

        StepVerifier.create(userUseCase.saveUser(user))
                .expectErrorMatches(throwable -> throwable instanceof BusinessException &&
                        throwable.getMessage().equals("El documento ya está registrado: " + user.getDocumentNumber()))
                .verify();

        verify(userRepository).findByEmail(user.getEmail());
        verify(userRepository).existsByDocumentNumber(user.getDocumentNumber());
        verify(userRepository, never()).save(user);
    }


}
