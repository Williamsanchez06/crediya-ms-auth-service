package co.com.crediya.authservice.api.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Getter
@ConfigurationProperties(prefix = "routes.paths")
public class UserPath {

    private String users;

}
