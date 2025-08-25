package co.com.crediya.authservice.usecase.user;


import co.com.crediya.authservice.model.exception.BusinessException;
import co.com.crediya.authservice.model.user.User;
import co.com.crediya.authservice.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class UserUseCase {

    private final UserRepository userRepository;

    public Mono<User> saveUser(User user) {
        return userRepository.findByEmail(user.getEmail())
                .flatMap(existing -> Mono.<User>error(new BusinessException("El correo ya está registrado: " + user.getEmail())))
                .switchIfEmpty(userRepository.existsByDocumentNumber(user.getDocumentNumber())
                        .flatMap(exists -> exists
                                ? Mono.<User>error(new BusinessException("El documento ya está registrado: " + user.getDocumentNumber()))
                                : userRepository.save(user)));
    }

}
