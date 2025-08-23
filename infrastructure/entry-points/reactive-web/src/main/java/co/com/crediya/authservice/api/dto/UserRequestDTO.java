package co.com.crediya.authservice.api.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UserRequestDTO {

    @NotBlank
    @Size(max = 100)
    private String name;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 8)
    private String password;

    @NotBlank
    @Size(max = 20)
    private String documentNumber;

    @NotBlank
    @Pattern(regexp = "^\\+?\\d{7,15}$")
    private String phone;

    @NotNull
    private UUID roleId;

}
