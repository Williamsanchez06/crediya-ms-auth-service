package co.com.crediya.authservice.api;

import co.com.crediya.authservice.api.dto.UserRequestDTO;
import co.com.crediya.authservice.api.validation.RequestValidator;
import co.com.crediya.authservice.usecase.role.RoleUseCase;
import co.com.crediya.authservice.usecase.user.UserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Map;

import static co.com.crediya.authservice.api.mapper.UserMapper.toDomain;
import static co.com.crediya.authservice.api.mapper.UserMapper.toResponseDTO;

@Component
@RequiredArgsConstructor
public class UserHandler {

    private final UserUseCase userUseCase;
    private final RoleUseCase roleUseCase;
    private final RequestValidator requestValidator;

    public Mono<ServerResponse> saveUser(ServerRequest request) {
        return request.bodyToMono(UserRequestDTO.class)
                .flatMap(dto -> {
                    var errors = requestValidator.validate(dto);
                    if (!errors.isEmpty()) {
                        return ServerResponse.badRequest()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(errors);
                    }

                    return roleUseCase.getRoleById(dto.getRoleId())
                            .switchIfEmpty(Mono.error(new IllegalArgumentException(
                                    "El rol con id " + dto.getRoleId() + " no existe"
                            )))
                            .flatMap(role -> {
                                var user = toDomain(dto, role);
                                return userUseCase.saveUser(user);
                            })
                            .flatMap(saved ->
                                    ServerResponse.ok()
                                            .contentType(MediaType.APPLICATION_JSON)
                                            .bodyValue(toResponseDTO(saved))
                            )
                            .onErrorResume(IllegalArgumentException.class, e ->
                                    ServerResponse.badRequest()
                                            .contentType(MediaType.APPLICATION_JSON)
                                            .bodyValue(Map.of("error", e.getMessage()))
                            );

                });
    }

}