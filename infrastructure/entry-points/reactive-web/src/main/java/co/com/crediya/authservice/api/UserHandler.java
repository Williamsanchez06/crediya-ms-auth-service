package co.com.crediya.authservice.api;

import co.com.crediya.authservice.api.dto.UserRequestDTO;
import co.com.crediya.authservice.api.exception.GlobalExceptionHandler;
import co.com.crediya.authservice.api.exception.NotFoundException;
import co.com.crediya.authservice.api.validation.RequestValidator;
import co.com.crediya.authservice.usecase.role.RoleUseCase;
import co.com.crediya.authservice.usecase.user.UserUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static co.com.crediya.authservice.api.mapper.UserMapper.toDomain;
import static co.com.crediya.authservice.api.mapper.UserMapper.toResponseDTO;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserHandler {

    private final UserUseCase userUseCase;
    private final RoleUseCase roleUseCase;
    private final RequestValidator requestValidator;
    private final GlobalExceptionHandler globalExceptionHandler;

    public Mono<ServerResponse> saveUser(ServerRequest request) {
        log.info("[saveUser] Iniciando proceso de guardado de usuario");

        return request.bodyToMono(UserRequestDTO.class)
                .flatMap(dto -> {
                    var errors = requestValidator.validate(dto);
                    if (!errors.isEmpty()) {
                        log.warn("[saveUser] Validación fallida: {}", errors);
                        return ServerResponse.badRequest()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(errors);
                    }

                    return roleUseCase.getRoleById(dto.getRoleId())
                            .switchIfEmpty(Mono.error(new NotFoundException(
                                    "Rol no encontrado con ID: " + dto.getRoleId()
                            )))
                            .flatMap(role -> {
                                log.debug("[saveUser] Rol encontrado con nombre={}", role.getName());
                                var user = toDomain(dto, role);
                                log.debug("[saveUser] Construyendo entidad usuario para guardar...");
                                return userUseCase.saveUser(user);
                            })
                            .flatMap(saved -> {
                                log.info("[saveUser] Usuario guardado exitosamente con ID={}", saved.getId());
                                return ServerResponse.ok()
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(toResponseDTO(saved));
                            });
                })
                .onErrorResume(globalExceptionHandler::handle);
    }
}