package co.com.crediya.authservice.api.exception;

import co.com.crediya.authservice.api.dto.ErrorResponse;
import co.com.crediya.authservice.model.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebInputException;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class GlobalExceptionHandler {

    public Mono<ServerResponse> handle(Throwable ex) {

        if (ex instanceof ServerWebInputException inputException) {
            log.warn("[GlobalHandler] Error en el cuerpo del request: {}", inputException.getReason());
            return buildResponse(
                    HttpStatus.BAD_REQUEST,
                    "INVALID_REQUEST_BODY",
                    "El cuerpo del request es inválido o está mal formado. Revisa el formato del JSON enviado."
            );
        }

        if (ex instanceof NotFoundException notFound) {
            log.warn("[GlobalHandler] Recurso no encontrado: {}", notFound.getMessage());
            return buildResponse(HttpStatus.NOT_FOUND, "NOT_FOUND", notFound.getMessage());
        }

        if (ex instanceof BusinessException businessEx) {
            log.warn("[GlobalHandler] Error de negocio: {}", businessEx.getMessage());
            return buildResponse(HttpStatus.BAD_REQUEST, "BUSINESS_ERROR", businessEx.getMessage());
        }

        log.error("[GlobalHandler] Error inesperado", ex); // loguea stacktrace completo
        return buildResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "INTERNAL_ERROR",
                "Ha ocurrido un error inesperado."
        );
    }

    private Mono<ServerResponse> buildResponse(HttpStatus status, String code, String message) {
        ErrorResponse error = new ErrorResponse(code, message);
        return ServerResponse
                .status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(error);
    }
}

