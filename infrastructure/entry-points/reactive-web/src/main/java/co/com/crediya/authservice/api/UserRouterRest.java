package co.com.crediya.authservice.api;

import co.com.crediya.authservice.api.config.UserPath;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class UserRouterRest {

    private final UserPath userPath;
    private final UserHandler userHandler;

    public UserRouterRest(UserPath userPath, UserHandler userHandler) {
        this.userPath = userPath;
        this.userHandler = userHandler;
    }

    @Bean
    public RouterFunction<ServerResponse> routerFunction(UserHandler userHandler) {
        return route(POST(userPath.getUsers()), this.userHandler::saveUser);
    }

}
