package co.com.crediya.authservice.usecase.user;


import co.com.crediya.authservice.model.user.User;
import co.com.crediya.authservice.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class UserUseCase {

    private final UserRepository userRepository;

    public Mono<User> saveUser(User user) {
        return userRepository.findByEmail(user.getEmail())
                .flatMap(existing -> Mono.<User>error(new IllegalArgumentException("El correo ya existe")))
                .switchIfEmpty(userRepository.save(user));
    }

}
