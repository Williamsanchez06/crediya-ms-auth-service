package co.com.crediya.authservice.model.role;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class Role {

    private static final int MAX_NAME_LENGTH = 50;
    private static final int MAX_DESCRIPTION_LENGTH = 200;

    private UUID id;
    private String name;
    private String description;

    private Role(UUID id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public static Role create(UUID id, String name, String description) {
        if (id == null)
            throw new IllegalArgumentException("Role ID is required");

        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Role name is required");
        if (name.length() > MAX_NAME_LENGTH)
            throw new IllegalArgumentException("Role name exceeds max length (" + MAX_NAME_LENGTH + ")");

        if (description == null || description.isBlank())
            throw new IllegalArgumentException("Role description is required");
        if (description.length() > MAX_DESCRIPTION_LENGTH)
            throw new IllegalArgumentException("Role description exceeds max length (" + MAX_DESCRIPTION_LENGTH + ")");

        return new Role(id, name, description);
    }
}
