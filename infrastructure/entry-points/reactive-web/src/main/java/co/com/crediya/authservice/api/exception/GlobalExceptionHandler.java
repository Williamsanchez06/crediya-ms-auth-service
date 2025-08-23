package co.com.crediya.authservice.api.exception;

import co.com.crediya.authservice.api.dto.ErrorResponse;
import co.com.crediya.authservice.model.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class GlobalExceptionHandler {

    public Mono<ServerResponse> handle(Throwable ex) {
        if (ex instanceof NotFoundException) {
            return buildResponse(HttpStatus.NOT_FOUND, "NOT_FOUND", ex.getMessage());
        } else if (ex instanceof BusinessException) {
            return buildResponse(HttpStatus.BAD_REQUEST, "BUSINESS_ERROR", ex.getMessage());
        } else {
            return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_ERROR", "Ha ocurrido un error inesperado.");
        }
    }

    private Mono<ServerResponse> buildResponse(HttpStatus status, String code, String message) {
        ErrorResponse error = new ErrorResponse(code, message);
        return ServerResponse
                .status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(error);
    }


}
