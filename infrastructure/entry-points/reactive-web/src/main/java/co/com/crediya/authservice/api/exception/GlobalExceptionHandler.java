package co.com.crediya.authservice.api.exception;

import co.com.crediya.authservice.api.dto.ErrorResponse;
import co.com.crediya.authservice.model.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.ServerWebInputException;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Slf4j
@Component
@Order(-2)
public class GlobalExceptionHandler implements WebExceptionHandler {

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        log.error("[GlobalWebExceptionHandler] Error capturado", ex);

        HttpStatus status;
        String code;
        String message;

        switch (ex) {
            case ServerWebInputException inputEx -> {
                status = HttpStatus.BAD_REQUEST;
                code = "INVALID_REQUEST_BODY";
                message = "El cuerpo del request es inválido o está mal formado.";
            }
            case NotFoundException notFound -> {
                status = HttpStatus.NOT_FOUND;
                code = "NOT_FOUND";
                message = notFound.getMessage();
            }
            case BusinessException business -> {
                status = HttpStatus.BAD_REQUEST;
                code = "BUSINESS_ERROR";
                message = business.getMessage();
            }
            default -> {
                status = HttpStatus.INTERNAL_SERVER_ERROR;
                code = "INTERNAL_ERROR";
                message = "Ha ocurrido un error inesperado.";
            }
        }

        return buildResponse(exchange, status, code, message);
    }

    private Mono<Void> buildResponse(ServerWebExchange exchange, HttpStatus status, String code, String message) {
        ErrorResponse error = new ErrorResponse(code, message);

        byte[] bytes = ("{\"code\":\"" + error.code() + "\",\"message\":\"" + error.message() + "\"}")
                .getBytes(StandardCharsets.UTF_8);

        exchange.getResponse().setStatusCode(status);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

        return exchange.getResponse()
                .writeWith(Mono.just(exchange.getResponse().bufferFactory().wrap(bytes)));
    }
}
