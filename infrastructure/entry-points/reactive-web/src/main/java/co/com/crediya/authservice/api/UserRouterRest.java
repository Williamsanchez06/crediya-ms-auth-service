package co.com.crediya.authservice.api;

import co.com.crediya.authservice.api.config.UserPath;
import co.com.crediya.authservice.api.dto.ErrorResponse;
import co.com.crediya.authservice.api.dto.UserRequestDTO;
import co.com.crediya.authservice.api.dto.UserResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.RouterOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@RequiredArgsConstructor
@Tag(name = "Usuarios", description = "Operaciones relacionadas con usuarios")
public class UserRouterRest {

    private final UserPath userPath;
    private final UserHandler userHandler;

    @Bean
    @RouterOperation(
            path = "/api/v1/users",
            method = RequestMethod.POST,
            beanClass = UserHandler.class,
            beanMethod = "saveUser",
            operation = @Operation(
                    operationId = "saveUser",
                    summary = "Crear un nuevo usuario",
                    requestBody = @RequestBody(
                            required = true,
                            content = @Content(schema = @Schema(implementation = UserRequestDTO.class))
                    ),
                    responses = {
                            @ApiResponse(
                                    responseCode = "200",
                                    description = "Usuario creado correctamente",
                                    content = @Content(schema = @Schema(implementation = UserResponseDTO.class))
                            ),
                            @ApiResponse(
                                    responseCode = "400",
                                    description = "Error de validación o cuerpo mal formado",
                                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                            ),
                            @ApiResponse(
                                    responseCode = "404",
                                    description = "Rol no encontrado",
                                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                            ),
                            @ApiResponse(
                                    responseCode = "500",
                                    description = "Error interno del servidor",
                                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                            )
                    }
            )
    )
    public RouterFunction<ServerResponse> routerFunction(UserHandler userHandler) {
        return route(POST(userPath.getUsers()), this.userHandler::saveUser);
    }

}
