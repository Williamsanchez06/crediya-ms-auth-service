package co.com.crediya.authservice.usecase.user;


import co.com.crediya.authservice.model.user.User;
import co.com.crediya.authservice.model.user.gateways.UserRepository;
import reactor.core.publisher.Mono;

public class UserUseCase {

    private final UserRepository userRepository;

    public UserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Mono<User> saveUser(User user) {
        return userRepository.save(user);
    }

}
