package co.com.crediya.authservice.model.role;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Role {

    private UUID id;
    private String name;
    private String description;

}
